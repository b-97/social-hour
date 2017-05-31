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

import socialhour.socialhour.adapter.FriendAdapter;
import socialhour.socialhour.model.FriendData;


public class friends_menu extends Fragment {

    public RecyclerView recView;
    public FriendAdapter adapter;
    public LinearLayout noFriendLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
            Code that sets up the recyclerview, layout manager, and makes an adapter and attaches
            it to the recyclerview.
            Also updates the adapter at the end to either display the empty image or load in people.
         */
        View view = inflater.inflate(R.layout.fragment_friends_menu, container, false);
        recView = (RecyclerView) view.findViewById(R.id.friend_list);
        noFriendLayout = (LinearLayout) view.findViewById(R.id.NoFriendLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getThisContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(layoutManager);
        adapter = new FriendAdapter(this.getThisContext());
        recView.setAdapter(adapter);
        updateAdapter();
        return view;
    }

    public Context getThisContext() {
        return getActivity();
    }

    /*
        Tell the adapter to update for how many people are in there.
        If there's no people to worry about, display an image to let the user know there's nobody
        there.
     */
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
        if(FriendData.get_friend_count() != 0)
            noFriendLayout.setVisibility(View.GONE);
        else
            noFriendLayout.setVisibility(View.VISIBLE);
    }
}