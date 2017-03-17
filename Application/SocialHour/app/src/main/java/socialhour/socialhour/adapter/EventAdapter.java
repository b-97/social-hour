package socialhour.socialhour.adapter;

import android.app.LauncherActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import socialhour.socialhour.R;
import socialhour.socialhour.model.*;

/**
 * Created by michael on 3/15/17.
 */

import java.util.ArrayList;
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private LayoutInflater inflater;


    public EventAdapter(ArrayList<EventItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        //this.listData = listData;
    }


    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_item, parent, false);

        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        EventItem item = EventData.get_event(position);
        holder.title.setText(item.get_event_title());

    }


    public void add_event(EventItem event) {
        EventData.add_event(event);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return EventData.getListData().size();
    }

    class EventHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;
        private View container;
        public EventHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.event_list_text);
            icon = (ImageView) itemView.findViewById(R.id.event_list_icon);
            container = itemView.findViewById(R.id.cont_event_root);
        }

    }

}