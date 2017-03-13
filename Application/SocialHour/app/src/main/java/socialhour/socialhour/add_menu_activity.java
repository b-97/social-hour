package socialhour.socialhour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class add_menu_activity extends frontend_activity {
    private TimePicker time_picker;

    int start_hour, end_hour;
    int start_minute, end_minute;
    int event_year, event_month, event_day;
    private Button start_time_diag_button;
    private Button end_time_diag_button;
    private Button date_diag_button;

    private TextView edit_event_name_textedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        event_year = cal.get(Calendar.YEAR);
        event_month = cal.get(Calendar.MONTH);
        event_day = cal.get(Calendar.DAY_OF_MONTH);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Event");
        setSupportActionBar(toolbar);

        /*
            TODO: Convert dialogues to fragments
         */
        start_time_diag_button = (Button) findViewById(R.id.start_time_button);
        start_time_diag_button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(1);
                    }
                }
        );
        end_time_diag_button = (Button) findViewById(R.id.end_time_button);
        end_time_diag_button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(2);
                    }
                }
        );
        date_diag_button = (Button) findViewById(R.id.date_button);
        date_diag_button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(3);
                    }
                }
        );
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 1)
            return new TimePickerDialog(add_menu_activity.this, kTimePickerListener, start_hour, start_minute, false);
        else if(id == 2)
            return new TimePickerDialog(add_menu_activity.this, lTimePickerListener, end_hour, end_minute, false);
        else if(id == 3)
            return new DatePickerDialog(this, datePickerListener, event_year, event_month, event_day);
        return null;
    }

    protected DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            event_year = year;
            event_month = monthOfYear + 1;
            event_day = dayOfMonth;
            date_diag_button.setText(String.format(Locale.getDefault(), "%02d", event_month) + "/"
                    + String.format(Locale.getDefault(), "%02d", event_day) + "/"
                    + String.format(Locale.getDefault(), "%04d", event_year));
        }
    };

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String am_pm;
            start_hour = hourOfDay;
            start_minute = minute;
            int start_hour_12 = start_hour;
            if(start_hour_12 > 12)
            {
                start_hour_12 -= 12;
                if (start_hour_12 == 12) am_pm = "am";
                else am_pm = "PM";
            }
            else if(start_hour_12 == 12) am_pm = "PM";
            else am_pm = "AM";
            start_time_diag_button.setText(String.format(Locale.getDefault(), "%02d", start_hour_12) + ":"
                    + String.format(Locale.getDefault(), "%02d", start_minute) + " " + am_pm);
        }
    };
    protected TimePickerDialog.OnTimeSetListener lTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String am_pm;
            end_hour = hourOfDay;
            end_minute = minute;
            int end_hour_12 = end_hour;
            if(end_hour_12 > 12)
            {
                end_hour_12 -= 12;
                if (end_hour_12 == 12) am_pm = "am";
                else am_pm = "PM";
            }
            else if(end_hour_12 == 12) am_pm = "PM";
            else am_pm = "AM";
            end_time_diag_button.setText(String.format(Locale.getDefault(), "%02d", end_hour_12) + ":"
                    + String.format(Locale.getDefault(), "%02d", end_minute) + " " + am_pm);
        }
    };
    /*
    Code that overrides the finish function. This allows the "Add New Event" dialog to return data to the parent function.
     */

    @Override
    public void finish() {
        Intent data = new Intent();

        edit_event_name_textedit = (TextView) findViewById(R.id.edit_event_name_edittext);

        final CheckBox is_all_day_checkbox = (CheckBox) findViewById(R.id.is_all_day_checkbox);
        boolean isAllDay = is_all_day_checkbox.isChecked();

        String event_name = edit_event_name_textedit.getText().toString();

        data.putExtra("event_year", this.event_year);
        data.putExtra("event_month", this.event_month);
        data.putExtra("event_day", this.event_day);
        data.putExtra("event_start_hour", this.start_hour);
        data.putExtra("event_end_hour", this.end_hour);
        data.putExtra("event_start_minute", this.start_minute);
        data.putExtra("event_end_minute", this.end_minute);
        data.putExtra("event_name", event_name);
        data.putExtra("is_all_day", isAllDay);

        setResult(RESULT_OK, data);
        super.finish();
    }
}
