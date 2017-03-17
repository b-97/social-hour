package socialhour.socialhour.model;

/**
 * Created by michael on 3/15/17.
 * Handles all of the data for the events.
 * Right now, there is no reason to have
 */

public class EventItem{
    private int start_hour;
    private int start_minute;
    private int end_hour;
    private int end_minute;
    private String event_title;
    private String event_description;
    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private boolean isAllDay;
    public EventItem(int start_hour_, int start_minute_, int end_hour_,
                     int end_minute_, String event_title_, String event_description_,
                     int dayOfMonth_, int monthOfYear_, int year_,
                     boolean isAllDay_)
    {
        start_hour = start_hour_;
        start_minute = start_minute_;
        end_hour = end_hour_;
        end_minute = end_minute_;
        event_title = event_title_;
        event_description = event_description_;
        dayOfMonth = dayOfMonth_;
        monthOfYear = monthOfYear_;
        year = year_;
        isAllDay = isAllDay_;
    }

    /*
        ACCESSORS - EACH RESPECTIVE METHOD MERELY RETURNS THE VALUE
     */
    public int get_start_hour() { return start_hour;}
    public int get_end_hour() { return end_hour;}
    public int get_start_minute() { return start_minute;}
    public int get_end_minute() { return end_minute;}
    public String get_event_title() { return event_title;}
    public String get_event_description() { return event_description;}
    public int get_dayOfMonth() { return dayOfMonth;}
    public int get_monthOfYear() { return monthOfYear;}
    public int get_year() { return year;}
    public boolean get_isAllDay() { return isAllDay;}

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
    public void set_event_title(String event_title_)
    {
        event_title = event_title_;
    }
    public void set_event_description(String event_description_)
    {
        event_description = event_description_;
    }
    public void set_dayOfMonth(int dayOfMonth_)
    {
        dayOfMonth = dayOfMonth_;
    }
    public void set_monthOfYear(int monthOfYear_)
    {
        monthOfYear= monthOfYear_;
    }
    public void set_year(int year_)
    {
        year = year_;
    }
    public void set_isAllDay(boolean isAllDay_)
    {
        isAllDay = isAllDay_;
    }
}