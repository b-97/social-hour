package socialhour.socialhour.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EventData{
    private static ArrayList<EventItem> event_list = new ArrayList<EventItem>();

    private static DatabaseReference eventDatabase;
    private static FirebaseDatabase database;

    public static void init() {
        eventDatabase = FirebaseDatabase.getInstance().getReference("events");
    }

    public static void initFirebaseEventData(){

    }

    public static ArrayList<EventItem> getListData(){
        return event_list;
    }
    public static EventItem get_event(int position){
        return event_list.get(position);
    }
    public static void add_event(EventItem event){
        String key = eventDatabase.push().getKey();
        EventItem data_event = new EventItem(event, key);
        eventDatabase.child(FirebaseEncodeEmail(data_event.get_user_email())).child(key).setValue(event);
        event_list.add(event);
    }
    public static void remove_event(int pos){
        event_list.remove(pos);
    }
    public static int get_event_count() {
        return event_list.size();
    }

    public static String FirebaseEncodeEmail(String string) {
        return string.replace(".", ",");
    }

    public static String FirebaseDecodeEmail(String string) {
        return string.replace(",", ".");
    }
}