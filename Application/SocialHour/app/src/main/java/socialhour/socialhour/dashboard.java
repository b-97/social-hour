package socialhour.socialhour;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import socialhour.socialhour.adapter.EventAdapter;
import socialhour.socialhour.model.EventData;
import socialhour.socialhour.model.EventItem;

public class dashboard extends Fragment {

    public RecyclerView recView;
    public EventAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recView = (RecyclerView) view.findViewById(R.id.event_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getThisContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recView.setLayoutManager(layoutManager);

        adapter = new EventAdapter(EventData.getListData(), this.getThisContext());
        recView.setAdapter(adapter);

        return view;

    }
    public Context getThisContext() {
        return getActivity();
    }

    public void make_toast(EventItem e) {
        Toast.makeText(this.getThisContext(), e.get_event_title() , Toast.LENGTH_SHORT).show();
    }
    public void updateAdapter(EventItem e) {
        this.make_toast(e);
        adapter.notifyDataSetChanged();
    }
}