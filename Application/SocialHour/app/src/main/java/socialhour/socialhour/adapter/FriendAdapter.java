package socialhour.socialhour.adapter;

import android.app.LauncherActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import socialhour.socialhour.R;
import socialhour.socialhour.model.*;
import socialhour.socialhour.tools.FirebaseData;

/**
 * Created by michael on 3/15/17.
 */

import java.util.ArrayList;
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder> {

    private LayoutInflater inflater;
    private Context context;

    private static FirebaseUser current_user_firebase = FirebaseAuth.getInstance().getCurrentUser();

    public FriendAdapter(ArrayList<FriendItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.context = c;
    }


    @Override
    public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.friend_item, parent, false);
        return new FriendHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, int position) {
        FriendItem item = FriendData.get_friend(position);
        if(FirebaseData.FirebaseDecodeEmail(item.get_initiator().get_email())
                .compareTo(FirebaseData.FirebaseDecodeEmail(current_user_firebase.getEmail())) == 0){
            holder.title.setText(item.get_acceptor().get_email());
            Picasso.with(context).load(item.get_acceptor().get_profile_picture()).into(holder.icon);
        }
        if(item.get_isAccepted() == false){
            holder.deny_wait_button.setImageResource(R.drawable.ic_timer_black_24dp);
        }


    }

    public void add_friend(FriendItem friend) {
        FriendData.add_friend(friend);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return FriendData.getListData().size();
    }
    class FriendHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;
        private ImageButton deny_wait_button;
        private View container;
        public FriendHolder(View itemView) {
            super(itemView);
            deny_wait_button = (ImageButton) itemView.findViewById(R.id.deny_wait_button);
            title = (TextView) itemView.findViewById(R.id.friend_list_text);
            icon = (ImageView) itemView.findViewById(R.id.friend_list_icon);
            container = itemView.findViewById(R.id.cont_friend_root);
        }

    }

}
