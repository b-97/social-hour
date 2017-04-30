package socialhour.socialhour.model;

/**
 * Created by michael on 4/30/17.
 */

import java.util.ArrayList;

public class GroupData{
    private static ArrayList<GroupItem> group_list = new ArrayList<GroupItem>();
    public static ArrayList<GroupItem> getListData(){
        return group_list;
    }
    public static GroupItem get_group(int position){
        return group_list.get(position);
    }
    public static void add_group(GroupItem group){
        group_list.add(group);
    }
    public static void remove_event(int pos){
        group_list.remove(pos);
    }
}