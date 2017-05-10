package socialhour.socialhour.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import socialhour.socialhour.R;
import socialhour.socialhour.model.PublicUserData;

import static java.util.Collections.sort;

/**
 * Serves as an adapter for searching for new friends
 * Created by michael on 5/3/17.
 */

public class Friend_Search_Adapter extends RecyclerView.Adapter<Friend_Search_Adapter.ViewHolder> implements Filterable{
    private ArrayList<PublicUserData> fArrayList;
    private ArrayList<PublicUserData> fFilteredList;
    private  Context context;

    public Friend_Search_Adapter(ArrayList<PublicUserData> arrayList, Context context) {
        fArrayList = arrayList;
        fFilteredList = arrayList;
        this.context = context;
    }

    @Override
    public Friend_Search_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friend_result_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Friend_Search_Adapter.ViewHolder viewHolder, int i){
        viewHolder.friends_text.setText(fFilteredList.get(i).get_display_name() + " (" +
                                        fFilteredList.get(i).get_email() +")");
        Picasso.with(context).load(fFilteredList.get(i).get_profile_picture()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return fFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    fFilteredList = fArrayList;
                } else {

                    ArrayList<PublicUserData> filteredList = new ArrayList<>();

                    for (PublicUserData f : fArrayList) {

                        if (f.get_display_name().toLowerCase().contains(charString) || f.get_email().toLowerCase().contains(charString)) {
                            filteredList.add(f);
                        }
                    }

                    fFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = fFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                fFilteredList = (ArrayList<PublicUserData>) filterResults.values;
                sort(fFilteredList);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView friends_text;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            friends_text = (TextView) view.findViewById(R.id.friends_text);
            imageView = (ImageView) view.findViewById(R.id.friendImageView);
        }
    }
}
