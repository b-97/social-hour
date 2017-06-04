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
        eventDatabase.child(key).setValue(event);
    }
    public static void modify_event_to_firebase(EventItem event){
        eventDatabase.child(event.get_id()).setValue(event);
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

    //allows the user to remove an event with only an object known.
    //returns true if event was removed, false otherwise.
    public static boolean remove_event(EventItem event){
        if(event_list != null){
            for(int i = 0; i < event_list.size(); i++){
                if(event_list.get(i).get_id().compareTo(event.get_id()) == 0){
                    event_list.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    //allows the user to remove an event with the position known.
    //returns true if event was removed, false otherwise.
    public static boolean remove_event(int i){
        //Structured in a flattened tree because event_list.size() will throw an exception if
        //it's not there, and try/catches are computationally expensive
        if(event_list == null)
            return false;
        else if (i >= event_list.size())
            return false;
        else{
            event_list.remove(i);
            return true;
        }
    }

    public static int get_event_count() {
        return event_list.size();
    }


}