package socialhour.socialhour;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import socialhour.socialhour.adapter.EventAdapter;
import socialhour.socialhour.model.EventData;
import socialhour.socialhour.model.EventItem;
import socialhour.socialhour.model.UserData;


public class dashboard extends Fragment {

    public RecyclerView recView;
    public EventAdapter adapter;
    public LinearLayout noEventLayout;

    private static DatabaseReference lDatabase;
    private static FirebaseDatabase fDatabase;

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


    public void updateAdapter(EventItem e) {
        noEventLayout.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }
}