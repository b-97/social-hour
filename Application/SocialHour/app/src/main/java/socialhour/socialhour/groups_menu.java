package socialhour.socialhour;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import socialhour.socialhour.adapter.FriendAdapter;
import socialhour.socialhour.adapter.Friend_Search_Adapter;
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

        recView = (RecyclerView) view.findViewById(R.id.group_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(layoutManager);
        adapter = new GroupAdapter(this.getActivity());
        recView.setAdapter(adapter);

        noGroupsLayout = (LinearLayout) view.findViewById(R.id.NoGroupsLayout);
        updateAdapter();
        return view;
    }
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
        if(GroupData.get_group_count() != 0)
            noGroupsLayout.setVisibility(View.GONE);
        else
            noGroupsLayout.setVisibility(View.VISIBLE);
    }
}