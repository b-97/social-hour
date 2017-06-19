package socialhour.socialhour.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

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


    public FriendAdapter(Context c) {
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
        final FriendItem item = FriendData.get_friend(position);
        String initiator_email = FirebaseData.decodeEmail(item.get_initiator().get_email());
        String acceptor_email = FirebaseData.decodeEmail(item.get_acceptor().get_email());
        String firebase_email = FirebaseData.decodeEmail(current_user_firebase.getEmail());


        //If we're the sender of the request
        if(initiator_email.compareTo(firebase_email) == 0){
            holder.title.setText(item.get_acceptor().get_display_name());
            //TODO: Maybe replace this with icons with rounded corners????
            Picasso.with(context).load(item.get_acceptor().get_profile_picture()).into(holder.icon);
            if(item.get_isAccepted()){
                holder.deny_wait_button.setVisibility(View.GONE);
                holder.accept_button.setVisibility(View.GONE);
            }
            else{
                holder.accept_button.setVisibility(View.GONE);
                holder.deny_wait_button.setVisibility(View.VISIBLE);
                holder.deny_wait_button.setImageResource(R.drawable.ic_timer_black_24dp);
                holder.deny_wait_button.setBackground(ContextCompat.getDrawable(context, R.drawable.roundcorner_yellow));
            }
        }
        //if we're the receiver of the request
        else if(acceptor_email.compareTo(firebase_email) == 0){
            holder.title.setText(item.get_initiator().get_display_name());
            Picasso.with(context).load(item.get_initiator().get_profile_picture()).into(holder.icon);
            if(item.get_isAccepted()){
                holder.deny_wait_button.setVisibility(View.GONE);
                holder.accept_button.setVisibility(View.GONE);
            }
            else{
                holder.accept_button.setVisibility(View.VISIBLE);
                holder.deny_wait_button.setVisibility(View.VISIBLE);
                holder.deny_wait_button.setBackground(ContextCompat.getDrawable(context, R.drawable.roundcorner_red));
                holder.accept_button.setBackground(ContextCompat.getDrawable(context, R.drawable.roundcorner_green));
                holder.deny_wait_button.setImageResource(R.drawable.ic_clear_black_24px);
                holder.accept_button.setImageResource(R.drawable.ic_done_black_24px);
                holder.accept_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        establish_friendship(item);
                    }
                });
                holder.deny_wait_button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        FriendData.remove_friend(item.get_key());
                    }
                });
            }
        }
    }

    public void establish_friendship(FriendItem item){
        for(FriendItem e: FriendData.getListData()){
            if(e.get_key().compareTo(item.get_key()) == 0){
                e.set_isAccepted(true);
                DatabaseReference ldatabase = FirebaseDatabase.getInstance().getReference("friend_data/" + e.get_key());
                ldatabase.setValue(e);
            }
            notifyDataSetChanged();
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
        private ImageButton accept_button;
        private ImageButton deny_wait_button;
        private View container;

        public FriendHolder(View itemView) {
            super(itemView);
            accept_button = (ImageButton) itemView.findViewById(R.id.accept_button);
            deny_wait_button = (ImageButton) itemView.findViewById(R.id.deny_wait_button);
            title = (TextView) itemView.findViewById(R.id.friend_list_text);
            icon = (ImageView) itemView.findViewById(R.id.friend_list_icon);
            container = itemView.findViewById(R.id.cont_friend_root);
        }

    }

}
