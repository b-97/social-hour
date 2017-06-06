package socialhour.socialhour.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import socialhour.socialhour.tools.FirebaseData;

public class GroupData{
    private static DatabaseReference groupDatabase;
    private static ArrayList<GroupItem> group_list;

    public static void init(){
        group_list = new ArrayList<>();
        groupDatabase = FirebaseDatabase.getInstance().getReference("group_data");
    }
    public static void add_group_to_firebase(GroupItem group){
        String key = groupDatabase.push().getKey();
        group.set_key(key);
        groupDatabase.child(key).setValue(group);
    }

    public static void add_group_from_firebase(GroupItem group){
        group_list.add(group);
    }



    public static ArrayList<GroupItem> getListData(){
        return group_list;
    }
    public static GroupItem get_group(int position){
        return group_list.get(position);
    }

    public static void remove_group(int pos){
        group_list.remove(pos);
    }

    public static void addFriendtoGroup(PublicUserData friend, GroupItem group){
        if(group_list != null){
            for(int i = 0; i < group_list.size(); i++){
                if(group_list.get(i).get_key().compareTo(group.get_key()) == 0){
                    group_list.get(i).add_member(friend);
                }
            }
        }
    }
    public static void removeFriendFromGroup(PublicUserData friend, GroupItem group){
        if(group_list != null){
            for(int i = 0; i < group_list.size(); i++){
                if(group_list.get(i).get_key().compareTo(group.get_key()) == 0){
                    group_list.get(i).remove_member(friend);
                }
            }
        }
    }
    public static void update_group(GroupItem group){
        //we use this method because it allows us to skirt the ConcurrentModificationException
        ListIterator<GroupItem> iter = group_list.listIterator();
        while (iter.hasNext()) {
            String str = iter.next().get_key();
            if (str.compareTo(group.get_key()) == 0)
                iter.set(group);
        }
    }
    //like the implementation above, but allows us to search for the key and remove the list.
    public static void remove_group(String key) {

        //we use this method because it allows us to skirt the ConcurrentModificationException
        Iterator<GroupItem> iter = group_list.iterator();
        while (iter.hasNext()) {
            String str = iter.next().get_key();
            if (str.compareTo(key) == 0)
                iter.remove();
        }
        for (GroupItem f : group_list){
            if(f.get_key().compareTo(key) == 0)
                group_list.remove(f);
        }
    }
    /*
        Gets an arraylist all of the email addresses where there is a group request with the user,
        yet not accepted. Particularly good for transitioning data between activities.
     */
    public static int get_group_count() {return group_list.size();}
}