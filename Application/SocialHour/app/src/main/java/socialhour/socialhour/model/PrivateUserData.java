package socialhour.socialhour.model;

/**
 * Created by michael on 5/9/17.
 */

import java.util.ArrayList;
import java.util.Date;

public class PrivateUserData {
    private ArrayList<PublicUserData> friends_list;
    private ArrayList<GroupItem> group_list;
    private ArrayList<EventItem> event_list;
    private String name;
    private String email;
    private String photo;
    private String provider_id;
    private Date date_created;

    public PrivateUserData(String name, String email, String photo, String provider_id,
                           ArrayList<PublicUserData> p_friends_list,
                           ArrayList<GroupItem> p_group_list,
                           ArrayList<EventItem> p_event_list){
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.provider_id = provider_id;
        friends_list = new ArrayList<PublicUserData>(p_friends_list);
        group_list = new ArrayList<GroupItem>(p_group_list);
        event_list = new ArrayList<EventItem>(p_event_list);
        date_created = new Date();
    }

    public PrivateUserData(){
        //empty constructor for god knows what reason why
    }


    public String get_name(){return name;}
    public String get_email(){return email;}
    public String get_photo(){return photo;}
    public String get_provider_id() {return provider_id;}
    public ArrayList<PublicUserData> get_friends_list() {return friends_list;}
    public ArrayList<GroupItem> get_group_list() {return group_list;}
    public ArrayList<EventItem> get_event_list() {return event_list;}
    public Date get_date_created() {return date_created;}

    public void set_name(String name){this.name = name;}
    public void set_email(String email){this.name = name;}
    public void set_photo(String photo){this.name = name;}
    public void set_provider_id(String provider_id){this.name = name;}
    public void set_date_created(Date date_created){this.name = name;}
    public void set_event_list(ArrayList<EventItem> event_list){this.event_list = event_list;}
    public void set_friend_list(ArrayList<PublicUserData> friends_list){this.friends_list = friends_list;}
    public void set_group_list(ArrayList<GroupItem> group_list){this.group_list = group_list;}

}
