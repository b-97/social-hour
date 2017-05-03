package socialhour.socialhour.model;

import socialhour.socialhour.adapter.Friend_Search_Adapter;

/**
 * Created by michael on 5/3/17.
 * Serves as a data structure for Friend_Search_adapter.
 */

public class Friend_Search_Result {
    private String display_name;
    private boolean is_friends;
    private String profile_picture;
    private String email;

    public Friend_Search_Result(String display_name, boolean is_friends, String profile_picture,
                                String email){
        this.display_name = display_name;
        this.is_friends = is_friends;
        this.profile_picture = profile_picture;
        this.email = email;
    }
    public String get_display_name(){
        return display_name;
    }

    //TODO: FRIENDS LIST IMPLEMENTATION
    public boolean get_is_friends(){
        return false;
    }
    public String get_picture(){
        return profile_picture;
    }
    public String get_email(){
        return email;
    }
}
