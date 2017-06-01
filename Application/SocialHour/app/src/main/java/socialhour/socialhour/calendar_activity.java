package socialhour.socialhour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;

import socialhour.socialhour.adapter.CalendarAdapter;
import socialhour.socialhour.model.EventData;
import socialhour.socialhour.model.EventItem;



/**
 * Created by dylan on 5/12/17.
 */



public class calendar_activity extends AppCompatActivity{

    public RecyclerView calRecView;
    public CalendarAdapter adapter;
    public LinearLayout date_Pick;
    private int mYear;
    private int mMonth;
    private int mDay;

    private static final int request_code_add_event = 5;
    private static final int request_code_add_friend = 6;
    private static final int request_code_add_group = 7;
    private static final int request_code_edit_settings = 8;
    private static final int request_code_edit_event = 9;

    private DatePicker date_picker;

    int current_year, current_month, current_day;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        /*
            Initiate the recyclerview data. We're going to filter everything by the current date, because
            the calendar is initially pointing to the current date.
         */
        Calendar today_cal = Calendar.getInstance();
        adapter = new CalendarAdapter(new ArrayList<EventItem>(),this);
        for(EventItem e: EventData.getListData()){
            if(today_cal.get(Calendar.DAY_OF_MONTH) ==mDay && today_cal.get(Calendar.MONTH)
                    == mMonth && today_cal.get(Calendar.YEAR)==mYear)
                adapter.add(e);
        }
        updateAdapter();

        //Initiate all of the views attach the necessary data.
        calRecView = (RecyclerView) this.findViewById(R.id.event_list_calendar);
        date_Pick = (LinearLayout) this.findViewById(R.id.NoEventLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calRecView.setLayoutManager(layoutManager);
        calRecView.setAdapter(adapter);


        //We need ints because the date_picker doesn't take a calendar
        current_year = today_cal.get(Calendar.YEAR);
        current_month = today_cal.get(Calendar.MONTH);
        current_day = today_cal.get(Calendar.DAY_OF_MONTH);


        //Initiate the date picker and attach a listener
        date_picker = (DatePicker) findViewById(R.id.datePicker);
        date_picker.init(today_cal.get(Calendar.YEAR), today_cal.get(Calendar.MONTH), today_cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
            }
        });
    }

    /*
        Notifies the recycleradapter that the dataset changed, so it can choose which events to display.
     */
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    /*
        Function to iterate through the EventItem and update the adapter and recyclerview
        with all of the events that match that date.
     */
    protected void updateDisplay(){
        adapter.clear();
        for(EventItem e : EventData.getListData())
        {
            Calendar eCal = Calendar.getInstance();
            eCal.setTime(e.get_start_date());
            Toast.makeText(calendar_activity.super.getApplicationContext(), e.get_start_date() + "", Toast.LENGTH_SHORT).show();
            if(eCal.get(Calendar.DAY_OF_MONTH) ==mDay && eCal.get(Calendar.MONTH)
                    == mMonth && eCal.get(Calendar.YEAR)==mYear)
                adapter.add(e);
        }
        updateAdapter();
    }

    public static void editEvent(Intent i)
    {
        //startActivityForResult(i,request_code_edit_event);
    }



}