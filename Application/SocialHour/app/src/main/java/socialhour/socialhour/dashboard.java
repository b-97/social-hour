package socialhour.socialhour;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import socialhour.socialhour.adapter.EventAdapter;
import socialhour.socialhour.model.EventData;
import socialhour.socialhour.model.EventItem;


public class dashboard extends Fragment {

    public RecyclerView recView;
    public EventAdapter adapter;
    public LinearLayout noEventLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recView = (RecyclerView) view.findViewById(R.id.event_list);
        noEventLayout = (LinearLayout) view.findViewById(R.id.NoEventLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getThisContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(layoutManager);
        adapter = new EventAdapter(EventData.getListData(), this.getThisContext());
        recView.setAdapter(adapter);
        if(EventData.get_event_count() != 0) {
            noEventLayout.setVisibility(View.GONE);
        }
        return view;

    }
    public Context getThisContext() {
        return getActivity();
    }


    public void updateAdapter() {
        noEventLayout.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }
}