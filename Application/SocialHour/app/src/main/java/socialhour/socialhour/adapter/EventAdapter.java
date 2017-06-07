package socialhour.socialhour.adapter;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import socialhour.socialhour.R;
import socialhour.socialhour.add_event_activity;
import socialhour.socialhour.frontend_activity;
import socialhour.socialhour.model.*;

/**
 *
 * Created by michael on 3/15/17
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private LayoutInflater inflater;

    private Context context;

    public EventAdapter(ArrayList<EventItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        context = c;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_item, parent, false);
        return new EventHolder(view);
    }


    /*
        Function that creates an adapter-appropriate string based on the creation date of the
        event and the current event.
        Current behaviour:
            Display hour and minute if the event is created on the same day
            Display month and date if event is created on the same year
            Display month, date and year if otherwise
     */
    private String createDateText(EventItem item){
        //Get calendars out of the current time and creation time of event
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(item.get_creation_date());

        //if the year and day matches, display the hour, minute, and am/pm
        //TODO: UPDATE THIS FUNCTION TO RESPECT 24hour USER PREFERENCE
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)){
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            return sdf.format(item.get_creation_date());
        }
        //if the year matches, display month and date
        else if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d");
            return sdf.format(item.get_creation_date());
        }
        //display date
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY");
            return sdf.format(item.get_creation_date());
        }
    }
    private String createTitleText(EventItem item){
        SimpleDateFormat time_sdf = new SimpleDateFormat();
        if(frontend_activity.current_user_local.get_pref_display_24hr()){
            time_sdf = new SimpleDateFormat("HH:mm");
        }
        else{
            time_sdf = new SimpleDateFormat("hh:mm a");
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal2.setTime(item.get_start_date());
        cal3.setTime(item.get_end_date());

        SimpleDateFormat date_sdf;


        if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal2.get(Calendar.DAY_OF_YEAR) == cal3.get(Calendar.DAY_OF_YEAR)){
            date_sdf = new SimpleDateFormat("EEEE, MMMM d");
            return item.get_creator().get_display_name() + "'s event " + item.get_name() + " at " +
                    item.get_location() + " from " + time_sdf.format(cal2.getTime()) + " to " +
                    time_sdf.format(cal2.getTime()) + " on " + date_sdf.format(cal3.getTime());
        }
        else if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
            date_sdf = new SimpleDateFormat("EEEE, MMMM d");

        }
        else if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
            date_sdf = new SimpleDateFormat("MMMM d");
        }
        else{
            date_sdf = new SimpleDateFormat("MMMM d, YYYY");
        }
        return item.get_creator().get_display_name() + "'s event " + item.get_name() + " at " +
                item.get_location() + " from " + time_sdf.format(cal2.getTime()) + " on " +
                date_sdf.format(cal2.getTime()) + " to " + time_sdf.format(cal3.getTime()) +
                " on " + date_sdf.format(cal3.getTime());
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        EventItem item = EventData.get_event(position);
        holder.item = item;
        holder.date.setText(createDateText(item));
        holder.title.setText(createTitleText(item));
        Picasso.with(context).load(item.get_creator().get_profile_picture()).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return EventData.getListData().size();
    }


    //Not sure if we need this, but keep it in anyways
    //TODO: IF BELOW METHOD IS NOT GREYED OUT, TEST FUNCTION
    public void delete(int position)
    {
        EventData.remove_event(position);
        notifyItemRemoved(position);
    }

    class EventHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;
        private View container;
        private TextView date;
        private final Context c2;

        private static final int request_code_add_event = 5;
        private static final int request_code_add_friend = 6;
        private static final int request_code_add_group = 7;
        private static final int request_code_edit_settings = 8;
        private static final int request_code_edit_event = 9;
        private EventItem item;

        public EventHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.event_date_text);
            title = (TextView) itemView.findViewById(R.id.event_list_text);
            icon = (ImageView) itemView.findViewById(R.id.event_list_icon);
            container = itemView.findViewById(R.id.cont_event_root);
            c2 = itemView.getContext();

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventItem e = item;
                    Intent i = new Intent(context, add_event_activity.class);
                    i.putExtra("name", e.get_name());
                    i.putExtra("description", e.get_description());
                    i.putExtra("location", e.get_location());

                    long start_date_millis = e.get_start_date().getTime();
                    String start_date_timezone = Calendar.getInstance().getTimeZone().getID();
                    long end_date_millis = e.get_end_date().getTime();
                    String end_date_timezone = Calendar.getInstance().getTimeZone().getID();

                    i.putExtra("start_date_millis", start_date_millis);
                    i.putExtra("end_date_millis", end_date_millis);
                    i.putExtra("start_date_timezone", start_date_timezone);
                    i.putExtra("end_date_timezone", end_date_timezone);
                    i.putExtra("privacy", e.get_privacy());
                    i.putExtra("isAllDay", e.get_isAllDay());
                    i.putExtra("id", e.get_id());
                    i.putExtra("request_code", request_code_edit_event);
                    i.putExtra("key", e.get_id());
                    ((Activity) c2).startActivityForResult(i, request_code_edit_event);
                }
            }
            );
        }

    }
}
