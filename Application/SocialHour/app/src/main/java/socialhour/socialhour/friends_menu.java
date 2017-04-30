package socialhour.socialhour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import socialhour.socialhour.adapter.FriendAdapter;
import socialhour.socialhour.adapter.GroupAdapter;
import socialhour.socialhour.model.FriendData;


public class friends_menu extends Fragment {

    public RecyclerView recView;
    public FriendAdapter adapter;
    public LinearLayout noFriendLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_menu, container, false);

        noFriendLayout = (LinearLayout) view.findViewById(R.id.NoFriendLayout);
        if(FriendData.get_friend_count() != 0) {
            noFriendLayout.setVisibility(View.GONE);
        }

        return view;
    }
}