package socialhour.socialhour.model;

/**
 * Created by michael on 4/30/17.
 */

import java.util.ArrayList;
import java.util.Date;

import socialhour.socialhour.tools.FirebaseData;

/**
 * Created by michael on 3/15/17.
 * Handles all of the data for the events.
 * Right now, there is no reason to have
 */

public class GroupItem{

    private String name;
    private String description;
    private ArrayList<PublicUserData> members;
    private PublicUserData owner;
    private Date creation_date;
    private String key;

    public GroupItem(Date creation_date, PublicUserData group_owner,
                     ArrayList<PublicUserData> members,
                     String description, ArrayList<EventItem> events, String name, String key) {
        this.creation_date = creation_date;
        this.owner = group_owner;
        this.members = members;
        this.name = name;
        this.description = description;
        this.key = key; //KEY MUST BE SET BY OTHER CLASS
    }

    public GroupItem(){
        //we need a constructor with no arguments or Firebase will complain.
    }

    /*
        ACCESSORS - EACH RESPECTIVE METHOD MERELY RETURNS THE VALUE
     */
    public String get_name(){return this.name;}
    public String get_description(){return this.description;}
    public ArrayList<PublicUserData> get_members(){return this.members;}
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
    public void set_owner(PublicUserData owner){this.owner = owner;}
    public void set_creation_date(Date creation_date){this.creation_date = creation_date;}
    public void set_key(String key){this.key = key;}

    /*
        ARRAYLIST MODIFIERS FOR SPECIFIC GROUPS
     */
    /*
        Adds a member to the members arraylist.
        Checks and makes sure that the member already isn't in there first, however.
     */
    public void add_member(PublicUserData member){
        boolean should_add = true;
        if(members != null) {
            for (PublicUserData usr : members) {
                //check to make sure the member isn't already in the arraylist
                if (FirebaseData.decodeEmail(member.get_email())
                        .compareTo(FirebaseData.decodeEmail(usr.get_email())) == 0) {
                    should_add = false;
                    break;
                }
            }
        }
        if(should_add)
            members.add(member);
    }

    /*
        Removes a member from the members arraylist.
        Returns a boolean to check if it was actually removed.
     */
    public boolean remove_member(PublicUserData member){
        if(members != null) {
            for (int i = 0; i < members.size(); i++) {
                if (FirebaseData.decodeEmail(members.get(i).get_email())
                        .compareTo(FirebaseData.decodeEmail(member.get_email())) == 0) {
                    members.remove(i);
                    return true;
                }
            }
        }
        return false;
    }
}