package socialhour.socialhour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class add_event_activity extends frontend_activity {
    private TimePicker time_picker;

    int start_hour, end_hour;
    int start_minute, end_minute;
    int event_start_year, event_start_month, event_start_day;
    int event_end_year, event_end_month, event_end_day;
    int current_year, current_month, current_day;

    final int PRIVACY_DEFAULT = 0;
    final int PRIVACY_PUBLIC = 1;
    final int PRIVACY_PRIVATE = 2;
    final String privacy_array[] = { "Default", "Private", "Public"};

    int event_privacy;

    private Button start_time_diag_button;
    private Button end_time_diag_button;
    private Button start_date_diag_button;
    private Button end_date_diag_button;
    private TextView edit_event_name_textedit;
    private TextView edit_event_location;
    private CheckBox is_all_day_check_box;
    private Spinner privacy_spinner;


    private boolean isAllDay;

    private boolean EVENT_CREATION_CANCELLED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        current_year = cal.get(Calendar.YEAR);
        current_month = cal.get(Calendar.MONTH);
        current_day = cal.get(Calendar.DAY_OF_MONTH);
        event_privacy = 0;

        EVENT_CREATION_CANCELLED = true; //by default event creation wasn't cancelled


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Event");
        setSupportActionBar(toolbar);


        /*
            Setting up the spinner with an adapter and a listener so that we can get the user's
            privacy choice.
         */
        //adapter setup


        privacy_spinner = (Spinner) findViewById(R.id.privacy_spinner);
        final ArrayAdapter<String> privacy_adapter = new ArrayAdapter<String>(this,
                 android.R.layout.simple_spinner_item, privacy_array);
        privacy_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        privacy_spinner.setAdapter(privacy_adapter);
        privacy_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                switch (adapter.getItemAtPosition(position).toString()) {
                    case "Default":
                        event_privacy = PRIVACY_DEFAULT;
                        break;
                    case "Public":
                        event_privacy = PRIVACY_PUBLIC;
                        break;
                    case "Private":
                        event_privacy = PRIVACY_PRIVATE;
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        //listener setup



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
        start_date_diag_button = (Button) findViewById(R.id.start_date_button);
        start_date_diag_button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(3);
                    }
                }
        );
        end_date_diag_button = (Button) findViewById(R.id.end_date_button);
        end_date_diag_button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(4);
                    }
                }
        );



        /*
               Establishes behaviour for two floating buttons.
               fab1 handles event creation, fab2 handles event cancellation.
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                attemptFinish(view);
            }
        });
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
            Handles checkbox input for whether or not the event takes place all day.
            TODO: write behaviour that disables / "clears" buttons and variables when checked
         */
        is_all_day_check_box = (CheckBox) findViewById(R.id.is_all_day_checkbox);
        is_all_day_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                isAllDay = isChecked;
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 1)
            return new TimePickerDialog(add_event_activity.this, kTimePickerListener, start_hour, start_minute, false);
        else if(id == 2)
            return new TimePickerDialog(add_event_activity.this, lTimePickerListener, end_hour, end_minute, false);
        else if(id == 3)
            return new DatePickerDialog(this, startdatePickerListener, current_year, current_month, current_day);
        else if(id == 4)
            return new DatePickerDialog(this, enddatePickerListener, current_year, current_month, current_day);
        return null;
    }

    protected DatePickerDialog.OnDateSetListener startdatePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            event_start_year = year;
            event_start_month = monthOfYear + 1;
            event_start_day = dayOfMonth;
            if (event_end_year == 0) {
                event_end_year = event_start_year;
                event_end_month = event_start_month;
                event_end_day = event_start_day;
                end_date_diag_button.setText(String.format(Locale.getDefault(), "%02d", event_end_month) + "/"
                        + String.format(Locale.getDefault(), "%02d", event_end_day) + "/"
                        + String.format(Locale.getDefault(), "%04d", event_end_year));

            }
            start_date_diag_button.setText(String.format(Locale.getDefault(), "%02d", event_start_month) + "/"
                    + String.format(Locale.getDefault(), "%02d", event_start_day) + "/"
                    + String.format(Locale.getDefault(), "%04d", event_start_year));
        }
    };

    protected DatePickerDialog.OnDateSetListener enddatePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            event_end_year = year;
            event_end_month = monthOfYear + 1;
            event_end_day = dayOfMonth;
            start_date_diag_button.setText(String.format(Locale.getDefault(), "%02d", event_end_month) + "/"
                    + String.format(Locale.getDefault(), "%02d", event_end_day) + "/"
                    + String.format(Locale.getDefault(), "%04d", event_end_year));
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

            //set the end button to one hour later by default
            if(end_hour == 0 && end_minute == 0){
                end_minute = start_minute;
                end_hour = start_hour + 1;
                //if the automatic end time placement puts us to the next day
                //TODO: Write implementation if the end of the event takes us to the next month, year
                if (end_hour == 24) {
                    event_end_day++;
                    end_date_diag_button.setText(String.format(Locale.getDefault(), "%02d", event_end_month) + "/"
                            + String.format(Locale.getDefault(), "%02d", event_end_day) + "/"
                            + String.format(Locale.getDefault(), "%04d", event_end_year));
                }
                if (end_hour > 24)  end_hour -= 24;
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
        Function that checks and makes sure that all of the appropriate user data was filled out,
        and if so, sets EVENT_CREATION_CANCELLED to false and finishes,
        and if not, shows a snackbar that warns the user of invalid fields.
     */
    protected void attemptFinish(View view) {
        if(invalid_fields()) {
            Snackbar.make(view, "Make sure you fill out event data first!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        }
        else { //this is the only case where event creation can succeed
            EVENT_CREATION_CANCELLED = false;
            finish();
        }
    }
    /*
        Function that ensures all fields are valid.
        TODO: Make sure this function is updated with new fields to be validated.
     */
    protected boolean invalid_fields(){
        return ((((TextView) findViewById(R.id.edit_event_name_edittext)).getText().toString().length() < 1) ||
                (((TextView) findViewById(R.id.event_location_textbox)).getText().toString().length() < 1) ||
                event_start_year == 0);
    }

    /*
    Code that overrides the finish function. This allows the "Add New Event" dialog to return data to the parent function.
     */
    @Override
    public void finish() {
        Intent data = new Intent();

        if(EVENT_CREATION_CANCELLED){
            setResult(RESULT_CANCELED, data);
            super.finish();
        }
        else{
            edit_event_name_textedit = (TextView) findViewById(R.id.edit_event_name_edittext);
            edit_event_location = (TextView) findViewById(R.id.event_location_textbox);

            String event_name = edit_event_name_textedit.getText().toString();
            String event_location = edit_event_location.getText().toString();

            data.putExtra("event_start_year", this.event_start_year);
            data.putExtra("event_start_month", this.event_start_month);
            data.putExtra("event_start_day", this.event_start_day);

            data.putExtra("event_end_year", this.event_end_year);
            data.putExtra("event_end_month", this.event_end_month);
            data.putExtra("event_end_day", this.event_end_day);

            data.putExtra("event_start_hour", this.start_hour);
            data.putExtra("event_end_hour", this.end_hour);
            data.putExtra("event_start_minute", this.start_minute);
            data.putExtra("event_end_minute", this.end_minute);

            data.putExtra("event_name", event_name);
            data.putExtra("event_location", event_location);
            data.putExtra("is_all_day", isAllDay);

            data.putExtra("event_privacy", event_privacy);

            setResult(RESULT_OK, data);
            super.finish();
        }
    }


    /*
        This method overrides frontend_activity's onCreateOptionsMenu
        so that the ability to access the user settings does not appear.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
}
