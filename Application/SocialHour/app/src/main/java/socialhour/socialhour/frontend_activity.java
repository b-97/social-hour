package socialhour.socialhour;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import socialhour.socialhour.model.EventData;
import socialhour.socialhour.model.EventItem;
import socialhour.socialhour.model.FriendData;
import socialhour.socialhour.model.FriendItem;
import socialhour.socialhour.model.GroupItem;
import socialhour.socialhour.model.PrivateUserData;
import socialhour.socialhour.model.PublicUserData;
import socialhour.socialhour.tools.FirebaseData;

import static socialhour.socialhour.tools.FirebaseData.encodeEmail;


public class frontend_activity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    /*
            These values tell onActivityResult how to process the data from
            the activity. The actual value of the int doesn't matter.
     */
    private static final int request_code_add_event = 5;
    private static final int request_code_add_friend = 6;
    private static final int request_code_add_group = 7;
    private static final int request_code_edit_settings = 8;
    private static final int request_code_edit_event = 9;

    private dashboard d;
    private friends_menu f;
    private groups_menu g;

    private FirebaseUser current_user_firebase;

    private DatabaseReference public_event_database;
    private DatabaseReference private_user_database;
    private DatabaseReference public_user_database;

    private DatabaseReference friend_connection_database;
    private DatabaseReference group_database;

    private FirebaseDatabase fDatabase;
    public static PrivateUserData current_user_local;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontend_activity);


        //allows us to get data of the user currently logged into firebase.
        current_user_firebase = FirebaseAuth.getInstance().getCurrentUser();


        //Fire up the databases that depend on recyclers (events, friends, groups)
        EventData.init();
        FriendData.init();



        //Set up the title bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Social Hour");
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //instance of FirebaseDatabase that all of the other databases will draw from.
        fDatabase = FirebaseDatabase.getInstance();

        //Grab the user's data from Google Firebase. This will allow us to change user settings
        //later on, and have them persist throughout the application. If the data doesn't already
        //exist, we'll make a new PrivateUserData and throw that shit in Google Firebase.
        private_user_database = fDatabase.getReference("private_user_data/" +
                encodeEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        private_user_database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.exists()){
                    try {
                        current_user_local = dataSnapshot.getValue(PrivateUserData.class);
                    }
                    catch(Exception e)
                    {
                        //do nothing
                    }
                }
                else{
                    current_user_local = new PrivateUserData(current_user_firebase.getDisplayName(),
                            encodeEmail(current_user_firebase.getEmail()),
                            current_user_firebase.getPhotoUrl().toString(),
                            current_user_firebase.getProviderId(), new ArrayList<PublicUserData>(),
                            new ArrayList<GroupItem>(), new ArrayList<EventItem>());
                    private_user_database.setValue(current_user_local);

                }
                public_user_database = fDatabase.getReference("public_user_data/" +
                        encodeEmail(current_user_local.get_email()));
                PublicUserData temp_user_data = new PublicUserData(current_user_local.get_photo(),
                        current_user_local.get_display_name(), current_user_local.get_email());
                public_user_database.setValue(temp_user_data);
            }
            @Override
            public void onCancelled(DatabaseError databaseError){
                Log.w("ERROR", "loadPost:onCancelled", databaseError.toException());
            }
        });


        //Grab all of the public events from Google Firebase. If the user is friends with an
        //individual, the event will be placed on the user's feed.
        //TODO: Provide implementation for the user to
        public_event_database = fDatabase.getReference("public_event_data/" +
                encodeEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        public_event_database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventItem event = dataSnapshot.getValue(EventItem.class);
                boolean should_add_event = true;
                for(EventItem e : EventData.getListData()){
                    try {
                        if(e.get_id().compareTo(event.get_id()) == 0) {
                            should_add_event = false;
                        }
                    } catch (NullPointerException q) {
                        Log.d("FrontendActivity", "WARNING - ATTEMPT TO SEARCH NULL ARRAY");
                    }

                }
                if(should_add_event){
                    EventData.add_event_from_firebase(event);
                    try {
                        d.updateAdapter();
                    } catch (NullPointerException e) {
                        Log.d("MainActivity", "WARNING: Can't update adapter because we're not on the main activity!");
                    }
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            //TODO: ADD THIS IMPLEMENTATION
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FAILED!!", null, null);
            }
        });

        friend_connection_database = fDatabase.getReference("friend_data/");
        friend_connection_database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FriendItem friend = dataSnapshot.getValue(FriendItem.class);

                //Here, we'll look at if the connection should be added.
                boolean should_add_connection = true;

                //If the connection is already in the database, we shouldn't add it.
                for(FriendItem e : FriendData.getListData()){
                    try {
                        if(e.get_key().compareTo(friend.get_key()) == 0) {
                            should_add_connection = false;
                        }
                    } catch (NullPointerException q) {
                        Log.d("FrontendActivity", "WARNING - ATTEMPT TO SEARCH NULL ARRAY");
                    }
                }
                //If the local user has nothing to do with the connection, we shouldn't add it.
                String initiator_email = FirebaseData.decodeEmail(friend.get_initiator().get_email());
                String acceptor_email = FirebaseData.decodeEmail(friend.get_acceptor().get_email());
                String local_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                if(initiator_email.compareTo(local_email) != 0 &&
                        acceptor_email.compareTo(local_email) != 0){
                    should_add_connection = false;
                }
                //However, if we should add it,
                if(should_add_connection){
                    FriendData.add_connection_from_firebase(friend);
                    try {
                        f.updateAdapter();;
                    } catch (NullPointerException e) {
                        Log.d("MainActivity", "WARNING: Can't update adapter because we're not on the main activity!");
                    }
                }

            }

            //TODO: Finish implementation of onChildChanged() and onChildRemoved.
            //If onChildChanged, likely someone accepted the request.
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FriendItem friend = dataSnapshot.getValue(FriendItem.class);
                FriendData.update_friend(friend);
                try {
                    f.updateAdapter();
                } catch (NullPointerException e) {
                    Log.d("MainActivity", "WARNING: Can't update adapter because we're not on the main activity!");
                }
            }

            //if onChildremoved, either the user deleted the friend or denied the request.
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                FriendItem friend = dataSnapshot.getValue(FriendItem.class);
                FriendData.remove_friend(friend.get_key());
                try {
                    f.updateAdapter();
                } catch (NullPointerException e) {
                    Log.d("MainActivity", "WARNING: Can't update adapter because we're not on the main activity!");
                }
            }

            //these methods never need to be properly overrided due to the nature of our database.
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FAILED!!", null, null);
            }
        });


        //TODO: Implement Group Database
        group_database = fDatabase.getReference("private_user_data/friends");


        //     Sets up the initial behaviour of the persistent floating action buttons.

        //  Fab: Responsible for starting and finishing the activities adding events, friends,
        //      and groups.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), add_event_activity.class);
                startActivityForResult(i, request_code_add_event);
            }
        });

        //  Fabcal: Responsible for starting the calendar activity.
        FloatingActionButton fabcal = (FloatingActionButton) findViewById(R.id.fabcal);
        fabcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), calendar_activity.class);
                startActivityForResult(i, request_code_add_event);
            }
        });
        /*
            Page Change listener that detects when the user flips a page, and changes the
            appearance and function accordingly. Currently, only the second button changes
            behaviour based on the page.
         */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                if(position == 0) {
                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.pastel_red)));
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_testedit));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), add_event_activity.class);
                            startActivityForResult(i, request_code_add_event);
                        }
                    });
                }
                else if(position == 1) {
                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.pastel_yellow)));
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_person_add_black_24dp));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), add_friends_activity.class);
                            startActivityForResult(i, request_code_add_friend);
                        }
                    });
                }
                else if(position == 2) {
                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.pastel_orange)));
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_group_add_black_24dp));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), add_group_activity.class);
                            startActivityForResult(i, request_code_add_group);
                        }
                    });
                }

            }
        });

    }
    /*
        This is called when any of the subactivities finishes.
        Grabs all of the intent data from the subactivity, and either creates a new event, creates
        a new group, or modifies the user's settings based on the exit
        code of the activity and what activity actually exited.
    */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //First IF block: handle all of the incoming data from the add event activiy, provided
        //the user successfully creates an event
        if ((requestCode == request_code_add_event || requestCode == request_code_edit_event) &&
                resultCode == RESULT_OK) {
            long start_date_millis = data.getExtras().getLong("start_date_millis");
            long end_date_millis = data.getExtras().getLong("end_date_millis");
            String start_date_timezone = data.getExtras().getString("start_date_timezone");
            String end_date_timezone = data.getExtras().getString("end_date_timezone");

            Calendar start_date = Calendar.getInstance();
            start_date.setTimeZone(TimeZone.getTimeZone(start_date_timezone));
            Calendar end_date = Calendar.getInstance();
            end_date.setTimeZone(TimeZone.getTimeZone(end_date_timezone));
            start_date.setTimeInMillis(start_date_millis);
            end_date.setTimeInMillis(end_date_millis);

            Date start_time = start_date.getTime();
            Date end_time = end_date.getTime();

            int privacy = data.getExtras().getInt("event_privacy");

            String name = data.getExtras().getString("event_name");
            String location = data.getExtras().getString("event_location");
            Date creation_date = new Date();
            boolean is_all_day = data.getExtras().getBoolean("is_all_day");


            EventItem event = new EventItem(start_time, end_time, is_all_day,
                    name, location, privacy, current_user_local.getPublicData(),
                    creation_date);
            if(requestCode == request_code_add_event)   //if the event is new add it
                EventData.add_event_to_firebase(event);
            else if(requestCode == request_code_edit_event) //if the event is old modify it
                EventData.modify_event_to_firebase(event);
            else
                //BROKE
            //add the event to the private user database aswell
            current_user_local.add_event(event);
            private_user_database.setValue(current_user_local);

            Toast.makeText(this, event.get_name() + " at " + event.get_start_date() + "/" +
                            event.get_start_date() + "/" + event.get_end_date() + "Is all day: " +
                            event.get_isAllDay() + event.get_privacy() + creation_date.getTime() +
                            current_user_firebase.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
        //TODO: Add implementation for end of editing an event
        //Second if block: user enters edit creation but cancels
        else if ((requestCode == request_code_add_event) &&
                resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Event creation cancelled.", Toast.LENGTH_SHORT).show();
        }
        //Third if block: user exits friend adding
        else if (requestCode == request_code_add_friend){
            f.updateAdapter();
        }
        //Fourth if block: user exits settings modification
        //TODO: Create seperate implementation for cancelling the settings activity
        else if (requestCode == request_code_edit_settings){ //currently we don't allow the user to cancel
            current_user_local.set_display_name(data.getExtras().getString("display_name"));
            current_user_local.set_pref_default_privacy(data.getExtras().getInt("privacy"));
            current_user_local.set_pref_display_24hr(data.getExtras().getBoolean("is_24_hr"));
            private_user_database.setValue(current_user_local);
            PublicUserData temp_user_data = new PublicUserData(current_user_local.get_photo(),
                    current_user_local.get_display_name(), current_user_local.get_email());
            public_user_database.setValue(temp_user_data);
        }
    }

    /*
        Overrides OnCreateOptionsMenu so that we can add an entry for entering the Settings
        activity.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frontend_activity, menu);
        return true;
    }


    /*'
        Provides implementation for clicking a icon in the menu at the top right:
        For the foreseeable future, the only use for the menu is entering the Settings activity.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), edit_settings_activity.class);
            //We'll throw in the current user's settings so that we can automatically set them
            //in the settings activity.
            i.putExtra("display_name", current_user_local.get_display_name());
            i.putExtra("is_24_hr", current_user_local.get_pref_display_24hr());
            i.putExtra("privacy", current_user_local.get_pref_default_privacy());
            //start the settings activity
            startActivityForResult(i, request_code_edit_settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch(position){
                case 0:
                    return new dashboard();
                case 1:
                    return new friends_menu();
                case 2:
                    return new groups_menu();
                default:
                    throw new RuntimeException("Error: Page not Found! If you're seeing this " +
                            "message please contact the developers.");
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Dashboard";
                case 1:
                    return "Friends";
                case 2:
                    return "Groups";
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    d = (dashboard) createdFragment;
                    break;
                case 1:
                    f = (friends_menu) createdFragment;
                    break;
                case 2:
                    g = (groups_menu) createdFragment;
                    break;
            }
            return createdFragment;
        }
    }
}
