package socialhour.socialhour.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import socialhour.socialhour.tools.FirebaseData;

public class FriendData{
    private static DatabaseReference friendDatabase;

    public static void init(){
        friendDatabase = FirebaseDatabase.getInstance().getReference("friend_data");
    }
    public static void add_connection_to_firebase(FriendItem connection){
        String key = friendDatabase.push().getKey();
        connection.set_key(key);
        friendDatabase.child(key).setValue(connection);
    }
    public static void add_connection_from_firebase(FriendItem connection){
        friend_list.add(connection);
    }
    private static ArrayList<FriendItem> friend_list = new ArrayList<FriendItem>();
    public static ArrayList<FriendItem> getListData(){
        return friend_list;
    }
    public static FriendItem get_friend(int position){
        return friend_list.get(position);
    }
    public static void add_friend(FriendItem event){
        friend_list.add(event);
    }
    public static void remove_friend(int pos){
        friend_list.remove(pos);
    }


    public static void update_friend(FriendItem friend){
        //we use this method because it allows us to skirt the ConcurrentModificationException
        ListIterator<FriendItem> iter = friend_list.listIterator();
        while (iter.hasNext()) {
            String str = iter.next().get_key();
            if (str.compareTo(friend.get_key()) == 0)
                iter.set(friend);
        }
    }
    //like the implementation above, but allows us to search for the key and remove the list.
    public static void remove_friend(String key) {

        //we use this method because it allows us to skirt the ConcurrentModificationException
        Iterator<FriendItem> iter = friend_list.iterator();
        while (iter.hasNext()) {
            String str = iter.next().get_key();
            if (str.compareTo(key) == 0)
                iter.remove();
        }
        for (FriendItem f : friend_list){
            if(f.get_key().compareTo(key) == 0)
                friend_list.remove(f);
        }
    }
    public static ArrayList<String> get_requests(String email){
        ArrayList<String> out = new ArrayList<>();
        if(friend_list != null){
            for(int i = 0; i < friend_list.size(); i++){
                if(!friend_list.get(i).get_isAccepted()){
                    if(FirebaseData.decodeEmail(friend_list.get(i).get_initiator().get_email())
                            .compareTo(FirebaseData.decodeEmail(email)) == 0){
                        out.add(friend_list.get(i).get_initiator().get_email());
                    }
                    else{
                        out.add(friend_list.get(i).get_acceptor().get_email());
                    }
                }
            }
        }
        return out;
    }
    public static int get_friend_count() {return friend_list.size();}
}