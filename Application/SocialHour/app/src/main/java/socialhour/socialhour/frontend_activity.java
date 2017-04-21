package socialhour.socialhour;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import socialhour.socialhour.model.EventData;
import socialhour.socialhour.model.EventItem;

/*
    No longer used libraries that at one point were integral to the application.
    Left here for reference.
    import android.widget.TextView;
    import socialhour.socialhour.adapter.EventAdapter;
    import android.view.LayoutInflater;
    import android.support.v7.widget.RecyclerView;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.design.widget.Snackbar;
    import android.app.usage.UsageEvents;
 */

public class frontend_activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final int request_code = 5;

    private dashboard d;
    private friends_menu f;
    private groups_menu g;

    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontend_activity);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), add_menu_activity.class);
                startActivityForResult(i, request_code);
            }
        });

        current_user = FirebaseAuth.getInstance().getCurrentUser();

    }
    /*
        This is called when the "Add Event activity finishes.
        This method then calls parseNewEventData with the event provided.
        Current Behaviour: Shows toast containing the event data passed from the Add Event.
        TODO: Update this function to grab more data when more event data is added to the activity
        TODO: Update this function to throw more data to parseNewEventData()
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == request_code) &&
                resultCode == RESULT_OK) {
            int event_start_year = data.getExtras().getInt("event_start_year");
            int event_start_month = data.getExtras().getInt("event_start_month");
            int event_start_date = data.getExtras().getInt("event_start_date");

            int event_end_year = data.getExtras().getInt("event_end_year");
            int event_end_month = data.getExtras().getInt("event_end_month");
            int event_end_date = data.getExtras().getInt("event_end_date");

            int event_privacy = data.getExtras().getInt("event_privacy");

            int event_start_hour = data.getExtras().getInt("event_start_hour");
            int event_end_hour = data.getExtras().getInt("event_end_hour");
            int event_start_minute = data.getExtras().getInt("event_start_minute");
            int event_end_minute = data.getExtras().getInt("event_end_minute");
            String event_name = data.getExtras().getString("event_name");
            String event_description = data.getExtras().getString("event_description");
            boolean is_all_day = data.getExtras().getBoolean("is_all_day");
            parseNewEventData(event_start_year, event_start_month, event_start_date,
                    event_end_year, event_end_month, event_end_date, event_start_hour,
                    event_end_hour, event_start_minute, event_end_minute,
                    is_all_day, event_name, event_description, event_privacy);

        }
        else if ((requestCode == request_code) &&
                resultCode == RESULT_CANCELED) {
            Toast.makeText(this,
                    //toast text
                    "Event creation cancelled."
                    , Toast.LENGTH_SHORT).show();
        }
    }

    /*
           This function is where all of the data processing happens.
           TODO: Update this function to have database integration???
           TODO: Update this function to grab more variables from onActivityResult() when more data is added to the activity
           TODO: Update this function to output data to the dashboard???
     */
    protected void parseNewEventData(int event_start_year, int event_start_month, int event_start_date,
                                     int event_end_year, int event_end_month, int event_end_date,
                                     int event_start_hour, int event_end_hour, int event_start_minute,
                                     int event_end_minute, boolean is_all_day, String event_name,
                                     String event_description, int event_privacy) {
        EventItem event = new EventItem(event_start_year, event_start_month, event_start_date,
        event_end_year, event_end_month, event_end_date, event_start_hour, event_end_hour, event_start_minute,
        event_end_minute, is_all_day, event_name, event_description, event_privacy);
        EventData.add_event(event);
        make_toast(event);
        d.updateAdapter(event);

    }
    public void make_toast(EventItem e) {

        Toast.makeText(this,
                //toast text
                e.get_event_title() + " at " + e.get_start_month() + "/" +
                        e.get_start_date() + "/" + e.get_end_month() + "Is all day: " +
                        e.get_isAllDay() + e.get_privacy()
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frontend_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
