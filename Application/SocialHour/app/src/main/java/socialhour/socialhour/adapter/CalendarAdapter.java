package socialhour.socialhour.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import socialhour.socialhour.R;
import socialhour.socialhour.model.EventItem;

/**
 * Created by michael on 3/15/17
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.EventHolder> {

    private LayoutInflater inflater;

    private Context context;

    public ArrayList<EventItem> adapterList;

    public CalendarAdapter(ArrayList<EventItem> listData, Context c) {
        adapterList = new ArrayList<EventItem>(listData);
        this.inflater = LayoutInflater.from(c);
        context = c;
    }

    public void clear(){
        adapterList.clear();
    }
    public void add(EventItem e){
        adapterList.add(e);
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_item, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        EventItem item = adapterList.get(position);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(item.get_creation_date());

        String event_creation_date_text;

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)){
            SimpleDateFormat sdf = new SimpleDateFormat("K:m a");
            event_creation_date_text = sdf.format(item.get_creation_date());
        }
        else if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d");
            event_creation_date_text = sdf.format(item.get_creation_date());
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY");
            event_creation_date_text = sdf.format(item.get_creation_date());
        }

        holder.date.setText(event_creation_date_text);
        holder.title.setText(item.get_user_name() + " created event " + item.get_name() +
                                " at " + item.get_location());
        Picasso.with(context).load(item.get_picture()).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public void delete(int position)
    {
        adapterList.remove(position);
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
