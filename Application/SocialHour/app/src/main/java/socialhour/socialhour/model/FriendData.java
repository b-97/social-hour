package socialhour.socialhour.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
    public static int get_friend_count() {return friend_list.size();}
}