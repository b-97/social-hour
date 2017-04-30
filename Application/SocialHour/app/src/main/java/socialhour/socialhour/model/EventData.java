package socialhour.socialhour.model;

import java.util.ArrayList;

public class EventData{
    private static ArrayList<EventItem> event_list = new ArrayList<EventItem>();
    public static ArrayList<EventItem> getListData(){
        return event_list;
    }
    public static EventItem get_event(int position){
        return event_list.get(position);
    }
    public static void add_event(EventItem event){
        event_list.add(event);
    }
    public static void remove_event(int pos){
        event_list.remove(pos);
    }
    public static int get_event_count() {return event_list.size();}
}