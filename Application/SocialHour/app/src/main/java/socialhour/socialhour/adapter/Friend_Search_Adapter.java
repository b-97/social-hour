package socialhour.socialhour.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
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
import com.squareup.picasso.Picasso;

import socialhour.socialhour.R;
import socialhour.socialhour.model.*;
import socialhour.socialhour.tools.FirebaseData;

/**
 * Created by michael on 3/15/17.
 */

import java.util.ArrayList;
public class Friend_Search_Adapter extends
        RecyclerView.Adapter<Friend_Search_Adapter.Friend_Search_Holder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<PublicUserData> friend_list;
    private ArrayList<PublicUserData> added_list;

    private static FirebaseUser current_user_firebase = FirebaseAuth.getInstance().getCurrentUser();

    public Friend_Search_Adapter(Context c, ArrayList<PublicUserData> friend_list) {
        this.inflater = LayoutInflater.from(c);
        this.context = c;
        this.friend_list = friend_list;
        //TODO: ADD CODE TO INITIALIZE THIS FOR ALREADY CREATED GROUPS
        added_list = new ArrayList<PublicUserData>();
    }


    @Override
    public Friend_Search_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.friend_item, parent, false);
        return new Friend_Search_Holder(view);
    }

    @Override
    public void onBindViewHolder(Friend_Search_Holder holder, int position) {
        final PublicUserData item = friend_list.get(position);
        holder.title.setText(item.get_display_name());
        Picasso.with(context).load(item.get_profile_picture()).into(holder.icon);
        holder.accept_button.setVisibility(View.GONE);
        boolean already_added = false;
        if(added_list != null){
            for(int i = 0; i < added_list.size(); i++){
                if(FirebaseData.decodeEmail(item.get_email())
                        .compareTo(FirebaseData.decodeEmail(added_list.get(i).get_email())) == 0){
                    already_added = true;
                }
            }
        }
        if(already_added){
            holder.deny_wait_button.setImageResource(R.drawable.ic_timer_black_24dp);
            holder.deny_wait_button.setBackground(ContextCompat.getDrawable(context, R.drawable.roundcorner_green));
            holder.deny_wait_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Already added user!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        else{
            holder.deny_wait_button.setImageResource(R.drawable.ic_add_black_24dp);
            holder.deny_wait_button.setBackground(ContextCompat.getDrawable(context, R.drawable.roundcorner_yellow));
            holder.deny_wait_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    added_list.add(item);
                    notifyDataSetChanged();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return friend_list.size();
    }

    class Friend_Search_Holder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;
        private ImageButton accept_button;
        private ImageButton deny_wait_button;
        private View container;

        public Friend_Search_Holder(View itemView) {
            super(itemView);
            accept_button = (ImageButton) itemView.findViewById(R.id.accept_button);
            deny_wait_button = (ImageButton) itemView.findViewById(R.id.deny_wait_button);
            title = (TextView) itemView.findViewById(R.id.friend_list_text);
            icon = (ImageView) itemView.findViewById(R.id.friend_list_icon);
            container = itemView.findViewById(R.id.cont_friend_root);
        }

    }

}