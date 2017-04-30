package socialhour.socialhour;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import socialhour.socialhour.adapter.GroupAdapter;
import socialhour.socialhour.model.GroupData;

/*
    Currently unused import statements; put these back in the code if necessary
    import android.support.v7.widget.Toolbar;
 */

public class groups_menu extends Fragment {

    public RecyclerView recView;
    public GroupAdapter adapter;
    public LinearLayout noGroupsLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups_menu, container, false);

        noGroupsLayout = (LinearLayout) view.findViewById(R.id.NoGroupsLayout);
        if(GroupData.get_groups_count() != 0) {
            noGroupsLayout.setVisibility(View.GONE);
        }
        return view;
    }
}