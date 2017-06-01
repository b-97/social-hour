package socialhour.socialhour.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

import socialhour.socialhour.tools.FirebaseData;

import static java.util.Collections.checkedCollection;
import static java.util.Collections.sort;

public class EventData {
    private static ArrayList<EventItem> event_list = new ArrayList<EventItem>();

    private static DatabaseReference eventDatabase;

    public static void init() {
        eventDatabase = FirebaseDatabase.getInstance().getReference("public_event_data/");
    }
    //sorts the array based on creation date
    public static void sort_list(){
        sort(event_list);
    }

    public static ArrayList<EventItem> getListData(){
        return event_list;
    }
    public static EventItem get_event(int position){
        return event_list.get(position);
    }


    //side effect: creates unique key for event, replacing NULL string
    public static void add_event_to_firebase(EventItem event){
        String key = eventDatabase.push().getKey();
        event.set_id(key);
        eventDatabase.child(FirebaseData.encodeEmail(event.get_creator().get_email()))
                .child(key).setValue(event);
    }
    public static void modify_event_to_firebase(EventItem event){
        eventDatabase.child(FirebaseData.encodeEmail(event.get_creator().get_email()))
                .child(event.get_id()).setValue(event);
    }
    public static void modify_event_from_firebase(EventItem event){
        for(int i = 0; i < event_list.size(); i++){
            if(event_list.get(i).get_id().compareTo(event.get_id()) == 0){
                event_list.set(i, event);
                sort(event_list, Collections.<EventItem>reverseOrder());
            }
        }
    }
    public static void add_event_from_firebase(EventItem event){
        event_list.add(event);
        sort(event_list, Collections.reverseOrder());
    }

    //NOTE: EXTREMELY DANGEROUS TO USE AS FIREBASE SYNC ISN'T ADDED YET!!
    public static void remove_event(int pos){
        event_list.remove(pos);
    }

    public static int get_event_count() {
        return event_list.size();
    }


}