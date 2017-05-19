package socialhour.socialhour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    public EventAdapter adapter;
    public LinearLayout date_Pick;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Button pick_date_button;

    int current_year, current_month, current_day;

    ArrayList<EventItem> events_Today;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        current_user_firebase = FirebaseAuth.getInstance().getCurrentUser();
        fDatabase = FirebaseDatabase.getInstance();

        public_event_database = fDatabase.getReference("public_event_data/" +
                EventData.FirebaseEncodeEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));

        adapter = new EventAdapter(events_Today,this);
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

        pick_date_button = (Button) findViewById(R.id.pick_date);
        pick_date_button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        showDialog(1);

                    }
                }
        );


    }
    protected DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==1)
            return new DatePickerDialog(calendar_activity.this,datePickerListener,current_year, current_month, current_day);
        else
            return null;
    }




    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    protected void updateDisplay(){
        //if (events_Today!=null)
        //    events_Today.clear();
        //    for(int i = 0;i<adapter.getItemCount();i++)
        //        adapter.delete(i);
        for(EventItem e : EventData.getListData())
        {
            if(e.get_start_date()==mDay && e.get_start_month()==mMonth && e.get_start_year()==mYear)
                events_Today.add(e);
        }
        adapter = new EventAdapter(events_Today, this);
        updateAdapter();
        }
}