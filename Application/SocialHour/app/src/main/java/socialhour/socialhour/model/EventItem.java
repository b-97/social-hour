package socialhour.socialhour.model;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michael on 3/15/17.
 * Handles all of the data for the events.
 * Right now, there is no reason to have
 */

public class EventItem implements Comparable<EventItem>{
    private int start_hour;
    private int start_minute;
    private int end_hour;
    private int end_minute;
    private String name;
    private String description;
    private String location;
    private String user_name;
    private Date creation_date;
    private int start_date;
    private int start_month;
    private int start_year;
    private int end_date;
    private int end_month;
    private int end_year;
    private int privacy;
    private String picture;
    private String user_email;
    private String id;


    private boolean isAllDay;
    public EventItem(int start_year, int start_month, int start_date,
                     int end_year, int end_month, int end_date,
                     int start_hour, int end_hour, int start_minute,
                     int end_minute, boolean is_all_day, String name,
                     String location, int privacy, String picture,
                     String user_display_name, String user_email, Date creation_date)
    {
        this.start_hour = start_hour;
        this.start_minute = start_minute;
        this.end_hour = end_hour;
        this.end_minute = end_minute;
        this.name = name;
        this.description = "UNIMPLEMENTED DESCRIPTION";
        this.location = location;
        this.start_date = start_date;
        this.start_month = start_month;
        this.start_year = start_year;
        this.end_date = end_date;
        this.end_month = end_month;
        this.end_year = end_year;
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
    public int get_start_hour() { return start_hour;}
    public int get_end_hour() { return end_hour;}
    public int get_start_minute() { return start_minute;}
    public int get_end_minute() { return end_minute;}
    public String get_name() { return name;}
    public String get_description() { return description;}
    public int get_start_date() { return start_date;}
    public int get_start_month() { return start_month;}
    public int get_start_year() { return start_year;}
    public int get_end_date() { return end_date;}
    public int get_end_month() { return end_month;}
    public int get_end_year() { return end_year;}
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
    public void set_start_hour(int start_hour_)
    {
        start_hour = start_hour_;
    }
    public void set_end_hour(int end_hour_)
    {
        end_hour = end_hour_;
    }
    public void set_start_minute(int start_minute_)
    {
        start_minute = start_minute_;
    }
    public void set_end_minute(int end_minute_)
    {
        end_minute = end_minute_;
    }
    public void set_name(String name_)
    {
        name = name_;
    }
    public void set_start_date(int dayOfMonth_)
    {
        start_date = dayOfMonth_;
    }
    public void set_start_month(int monthOfYear_)
    {
        start_month= monthOfYear_;
    }
    public void set_start_year(int year_)
    {
        start_year = year_;
    }
    public void set_end_date(int dayOfMonth_)
    {
        end_date = dayOfMonth_;
    }
    public void set_end_month(int monthOfYear_)
    {
        end_month= monthOfYear_;
    }
    public void set_end_year(int year_)
    {
        end_year = year_;
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