package socialhour.socialhour.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by michael on 5/3/17.
 */

public class UserData implements Comparable<UserData>{
    private String profile_picture;
    private String display_name;
    private String email;
    private ArrayList<UserData> userFriends;


    public UserData(String profile_picture, String display_name, String email){
        this.profile_picture = profile_picture;
        this.display_name = display_name;
        this.email = email;
    }
    public UserData(){
        //Default Constructor required for Firebase
    }

    //getters for firebase
    public String get_profile_picture(){
        return profile_picture;
    }
    public String get_email(){
        return email;
    }
    public String get_display_name(){
        return display_name;
    }

    //setters for firebase
    public void set_profile_picture(String profile_picture){
        this.profile_picture = profile_picture;
    }
    public void set_display_name(String display_name){
        this.display_name = display_name;
    }
    public void set_email(String email){
        this.email = email;
    }

    public int compareTo(@NonNull UserData user2){
        return this.get_display_name().compareTo(user2.get_display_name());
    }

}
