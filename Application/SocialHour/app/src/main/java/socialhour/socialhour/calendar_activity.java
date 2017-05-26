package socialhour.socialhour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import socialhour.socialhour.adapter.CalendarAdapter;
import socialhour.socialhour.adapter.EventAdapter;
import socialhour.socialhour.model.EventData;
import socialhour.socialhour.model.EventItem;



/**
 * Created by dylan on 5/12/17.
 */



public class calendar_activity extends AppCompatActivity{

    private DatabaseReference public_event_database;
    private FirebaseDatabase fDatabase;
    private FirebaseUser current_user_firebase;

    private calendar_activity c;
    public RecyclerView calRecView;
    public CalendarAdapter adapter;
    public LinearLayout date_Pick;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Button pick_date_button;

    private DatePicker date_picker;

    int current_year, current_month, current_day;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        adapter = new CalendarAdapter(new ArrayList<EventItem>(),this);

        for(EventItem e : EventData.getListData()){
            adapter.add(e);
        }
        calRecView = (RecyclerView) this.findViewById(R.id.event_list_calendar);
        date_Pick = (LinearLayout) this.findViewById(R.id.NoEventLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calRecView.setLayoutManager(layoutManager);
        calRecView.setAdapter(adapter);

        final Calendar cal = Calendar.getInstance();

        current_year = cal.get(Calendar.YEAR);
        current_month = cal.get(Calendar.MONTH);
        current_day = cal.get(Calendar.DAY_OF_MONTH);

        Calendar today = Calendar.getInstance();

        date_picker = (DatePicker) findViewById(R.id.datePicker);
        date_picker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                Toast.makeText(calendar_activity.super.getApplicationContext(), "" + mYear + "/" + mMonth + "/" + mDay + "", Toast.LENGTH_SHORT).show();
                updateDisplay();
            }
        });
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

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
}