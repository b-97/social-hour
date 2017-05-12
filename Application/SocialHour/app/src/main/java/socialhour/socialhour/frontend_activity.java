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

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import socialhour.socialhour.model.EventData;
import socialhour.socialhour.model.EventItem;
import socialhour.socialhour.model.GroupItem;
import socialhour.socialhour.model.PrivateUserData;
import socialhour.socialhour.model.PublicUserData;
import socialhour.socialhour.tools.CalendarRequestTask;


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
    private PrivateUserData current_user_local;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontend_activity);


        //Let's pull Firebase data down into the application.
        current_user_firebase = FirebaseAuth.getInstance().getCurrentUser();



        EventData.init();


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
                EventData.FirebaseEncodeEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        private_user_database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.exists()){
                    current_user_local = dataSnapshot.getValue(PrivateUserData.class);
                }
                else{
                    current_user_local = new PrivateUserData(current_user_firebase.getDisplayName(),
                            EventData.FirebaseEncodeEmail(current_user_firebase.getEmail()),
                            current_user_firebase.getPhotoUrl().toString(),
                            current_user_firebase.getProviderId(), new ArrayList<PublicUserData>(),
                            new ArrayList<GroupItem>(), new ArrayList<EventItem>());
                    private_user_database.setValue(current_user_local);

                }
                public_user_database = fDatabase.getReference("public_user_data/" +
                        EventData.FirebaseEncodeEmail(current_user_local.get_email()));
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
        public_event_database = fDatabase.getReference("public_event_data/" +
                EventData.FirebaseEncodeEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
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
                        d.updateAdapter(event);
                    } catch (NullPointerException e) {
                        Log.d("MainActivity", "WARNING: Can't update adapter because we're not on the main activity!");
                    }
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FAILED!!", null, null);
            }
        });




        //TODO: Implement Friend Connection Database
        friend_connection_database = fDatabase.getReference("private_user_data/");

        //TODO: Implement Group Database
        group_database = fDatabase.getReference("private_user_data/friends");


        /*     Sets up the floating action button that persists between tabs.
               Behaviour of the floating action button changes depending on page loaded.
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), add_event_activity.class);
                startActivityForResult(i, request_code_add_event);
            }
        });
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
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.pastel_orange)));
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
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.pastel_yellow)));
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
        Grabs all of the intent data from the subactivity, and either creates a new event, sends
        a friend request, or creates a new group.
    */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == request_code_add_event) &&
                resultCode == RESULT_OK) {
            int start_year = data.getExtras().getInt("event_start_year");
            int start_month = data.getExtras().getInt("event_start_month");
            int start_date = data.getExtras().getInt("event_start_date");
            int end_year = data.getExtras().getInt("event_end_year");
            int end_month = data.getExtras().getInt("event_end_month");
            int end_date = data.getExtras().getInt("event_end_date");
            int privacy = data.getExtras().getInt("event_privacy");
            int start_hour = data.getExtras().getInt("event_start_hour");
            int end_hour = data.getExtras().getInt("event_end_hour");
            int start_minute = data.getExtras().getInt("event_start_minute");
            int end_minute = data.getExtras().getInt("event_end_minute");
            String name = data.getExtras().getString("event_name");
            String location = data.getExtras().getString("event_location");
            Date creation_date = new Date();
            boolean is_all_day = data.getExtras().getBoolean("is_all_day");


            EventItem event = new EventItem(start_year, start_month, start_date, end_year,
                    end_month, end_date, start_hour, end_hour, start_minute, end_minute, is_all_day,
                    name, location, privacy, current_user_local.get_photo(),
                    current_user_local.get_display_name(), current_user_local.get_email(),
                    creation_date);

            EventData.add_event_to_firebase(event);

            //add the event to the private user database aswell
            current_user_local.add_event(event);
            private_user_database.setValue(current_user_local);

            Toast.makeText(this, event.get_name() + " at " + event.get_start_month() + "/" +
                            event.get_start_date() + "/" + event.get_end_month() + "Is all day: " +
                            event.get_isAllDay() + event.get_privacy() + creation_date.getTime() +
                            current_user_firebase.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
        else if ((requestCode == request_code_add_event) &&
                resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Event creation cancelled.", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frontend_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), edit_settings_activity.class);
            i.putExtra("display_name", current_user_local.get_display_name());
            i.putExtra("is_24_hr", current_user_local.get_pref_display_24hr());
            i.putExtra("privacy", current_user_local.get_pref_default_privacy());
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
