package socialhour.socialhour;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

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
            int event_year = data.getExtras().getInt("event_year");
            int event_month = data.getExtras().getInt("event_month");
            int event_date = data.getExtras().getInt("event_date");
            int event_start_hour = data.getExtras().getInt("event_start_hour");
            int event_end_hour = data.getExtras().getInt("event_end_hour");
            int event_start_minute = data.getExtras().getInt("event_start_minute");
            int event_end_minute = data.getExtras().getInt("event_end_minute");
            String event_name = data.getExtras().getString("event_name");

            parseNewEventData(event_year, event_month, event_date, event_start_hour,
                    event_end_hour, event_start_minute, event_end_minute, event_name);
        }
    }

    /*
           This function is where all of the data processing happens.
           TODO: Update this function to have database integration???
           TODO: Update this function to grab more variables from onActivityResult() when more data is added to the activity
           TODO: Update this function to output data to the dashboard???
     */
    protected void parseNewEventData(int year, int month, int date, int start_hour,
                                     int end_hour, int start_minute, int end_minute, String event_name_){
        int event_year = year;
        int event_month = month;
        int event_date = date;
        int event_start_hour = start_hour;
        int event_end_hour = end_hour;
        int event_start_minute = start_minute;
        int event_end_minute = end_minute;
        String event_name = event_name_;
        Toast.makeText(this.getBaseContext(), event_name + " " + event_year + "/" + event_month +
                        "/" + event_date + "; " + event_start_hour + ":" + event_end_minute +
                        " to " + event_end_hour + ":" + event_end_minute,
                Toast.LENGTH_LONG).show();
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
     * TODO: Update public Fragment getItem() to have all three pages when pages are added
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
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
    }
}
