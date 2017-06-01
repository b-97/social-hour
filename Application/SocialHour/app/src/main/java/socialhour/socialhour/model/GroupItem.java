package socialhour.socialhour.model;

/**
 * Created by michael on 4/30/17.
 */

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by michael on 3/15/17.
 * Handles all of the data for the events.
 * Right now, there is no reason to have
 */

public class GroupItem{

    private String name;
    private String description;
    private ArrayList<PublicUserData> members;
    private ArrayList<PublicUserData> admins;
    private PublicUserData owner;
    private Date creation_date;
    private String key;

    public GroupItem(Date creation_date, PublicUserData group_owner, boolean is_all_day,
                     ArrayList<PublicUserData> members, ArrayList<PublicUserData> admins,
                     String description, ArrayList<EventItem> events, String name) {
        this.creation_date = creation_date;
        this.owner = group_owner;
        this.admins = admins;
        this.members = members;
        this.name = name;
        this.description = description;
        this.key = "NULL"; //KEY MUST BE SET BY OTHER CLASS
    }

    /*
        ACCESSORS - EACH RESPECTIVE METHOD MERELY RETURNS THE VALUE
     */
    public String get_name(){return this.name;}
    public String get_description(){return this.description;}
    public ArrayList<PublicUserData> get_members(){return this.members;}
    public ArrayList<PublicUserData> get_admins(){return this.admins;}
    public PublicUserData get_owner(){return this.owner;}
    public Date get_creation_date(){return this.creation_date;}
    public String get_key(){return this.key;}
    /*
        BASIC MUTATORS - EACH RESPECTIVE METHOD MERELY MODIFIES THE VALUE
        TODO: Change all of these to return false if value failed to update
     */
    public void set_name(String name){this.name = name;}
    public void set_description(String description){this.description = description;}
    public void set_members(ArrayList<PublicUserData> members){this.members = members;}
    public void set_admins(ArrayList<PublicUserData> admins){this.admins = admins;}
    public void set_owner(PublicUserData owner){this.owner = owner;}
    public void set_creation_date(Date creation_date){this.creation_date = creation_date;}
    public void set_key(String key){this.key = key;}
}