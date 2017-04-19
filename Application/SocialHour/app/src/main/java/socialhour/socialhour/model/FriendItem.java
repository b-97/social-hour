package socialhour.socialhour.model;

/**
 * Created by michael on 3/15/17.
 * Handles all of the data for the events.
 * Right now, there is no reason to have
 */

public class FriendItem{
    private String name;
    public FriendItem(String name_)
    {
        name = name_;
    }

    /*
        ACCESSORS - EACH RESPECTIVE METHOD MERELY RETURNS THE VALUE
     */
    public String getName(){
        return name;
    }

    /*
        BASIC MUTATORS - EACH RESPECTIVE METHOD MERELY MODIFIES THE VALUE
        TODO: Change all of these to return false if value failed to update
     */
    public void setName(String name_){
        name = name_;
    }
}