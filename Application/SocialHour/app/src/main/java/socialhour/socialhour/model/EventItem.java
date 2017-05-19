package socialhour.socialhour.model;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by michael on 3/15/17.
 * Handles all of the data for the events.
 * Right now, there is no reason to have
 */

public class EventItem implements Comparable<EventItem>{

    private String name;
    private String description;
    private String location;
    private String user_name;
    private Date creation_date;
    private Date start_date;
    private Date end_date;
    private int privacy;
    private String picture;
    private String user_email;
    private String id;


    private boolean isAllDay;
    public EventItem(Date start_date, Date end_date, boolean is_all_day, String name,
                     String location, int privacy, String picture,
                     String user_display_name, String user_email, Date creation_date)
    {

        this.name = name;
        this.description = "UNIMPLEMENTED DESCRIPTION";
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.privacy = privacy;
        this.isAllDay = is_all_day;
        this.picture = picture;
        this.user_name = user_display_name;
        this.user_email = user_email;
        this.creation_date = creation_date;
        this.id = "ID_NULL";
    }

    public EventItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    /*
        ACCESSORS - EACH RESPECTIVE METHOD MERELY RETURNS THE VALUE
     */
    public Date get_start_date() {return start_date; }
    public Date get_end_date() {return end_date; }
    public String get_name() { return name;}
    public String get_description() { return description;}
    public int get_privacy() { return privacy;}
    public String get_picture() {return picture;}
    public boolean get_isAllDay() { return isAllDay;}
    public String get_user_name() {return user_name;}
    public String get_location() {return location;}
    public String get_id() {return id;}
    public String get_user_email() {return user_email;}
    public Date get_creation_date() {return creation_date;}

    /*
        BASIC MUTATORS - EACH RESPECTIVE METHOD MERELY MODIFIES THE VALUE
        TODO: Change all of these to return false if value failed to update
     */
    public void set_start_date(Date start_date){this.start_date = start_date;}
    public void set_end_date(Date end_date){this.end_date = end_date;}

    public void set_name(String name_)
    {
        name = name_;
    }

    public void set_privacy(int privacy_) {privacy = privacy_;}
    public void set_location(String event_location) {location = event_location;}
    public void set_user_name(String name) {user_name = name;}
    public void set_isAllDay(boolean isAllDay_)
    {
        isAllDay = isAllDay_;
    }
    public void set_creation_date(Date date) {creation_date = date;}
    public void set_id(String id){this.id = id;}
    public void set_picture(String pict){this.picture = pict;}
    public void set_user_email(String email){this.user_email = email;}

    public int compareTo(@NonNull EventItem event2){
        return this.get_creation_date().compareTo(event2.get_creation_date());
    }
}