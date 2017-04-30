package socialhour.socialhour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import socialhour.socialhour.model.FriendData;

/*
    Currently unused import statements; put these back in the code if necessary
    //import android.support.v7.widget.LinearLayoutManager;
    //import android.support.v7.widget.Toolbar;
 */

public class friends_menu extends Fragment {
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