package socialhour.socialhour.model;

import java.util.ArrayList;

public class FriendData{
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
}