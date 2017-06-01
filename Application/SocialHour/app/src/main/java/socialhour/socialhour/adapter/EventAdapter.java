package socialhour.socialhour.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import socialhour.socialhour.R;
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
    public String createDateText(EventItem item){
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
    public String createTitleText(EventItem item){
        //TODO: Honor user preference for 24h format
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return item.get_creator() + "'s event" + item.get_name() + "at" + item.get_location() +
                "from" + sdf.format(item.get_start_date())  + sdf.format(item.get_end_date());
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        EventItem item = EventData.get_event(position);

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

        public EventHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.event_date_text);
            title = (TextView) itemView.findViewById(R.id.event_list_text);
            icon = (ImageView) itemView.findViewById(R.id.event_list_icon);
            container = itemView.findViewById(R.id.cont_event_root);
        }

    }
}
