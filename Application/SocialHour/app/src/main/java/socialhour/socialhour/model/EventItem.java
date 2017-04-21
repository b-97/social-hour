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
    private String event_location;

    private int start_date;
    private int start_month;
    private int start_year;
    private int end_date;
    private int end_month;
    private int end_year;

    private int privacy;

    private boolean isAllDay;
    public EventItem(int event_start_year, int event_start_month, int event_start_date,
                     int event_end_year, int event_end_month, int event_end_date,
                     int event_start_hour, int event_end_hour, int event_start_minute,
                     int event_end_minute, boolean is_all_day, String event_name,
                     String event_description_, int event_privacy)
    {
        start_hour = event_start_hour;
        start_minute = event_start_minute;
        end_hour = event_end_hour;
        end_minute = event_end_minute;
        event_title = event_name;
        event_description = event_description_;

        start_date = event_start_date;
        start_month = event_start_month;
        start_year = event_start_year;

        end_date = event_end_date;
        end_month = event_end_month;
        end_year = event_end_year;

        privacy = event_privacy;

        isAllDay = is_all_day;
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

    public int get_start_date() { return start_date;}
    public int get_start_month() { return start_month;}
    public int get_start_year() { return start_year;}

    public int get_end_date() { return end_date;}
    public int get_end_month() { return end_month;}
    public int get_end_year() { return end_year;}

    public int get_privacy() { return privacy;}

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
    public void set_event_description(String event_description_) {
        event_description = event_description_;
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

    public void set_isAllDay(boolean isAllDay_)
    {
        isAllDay = isAllDay_;
    }
}