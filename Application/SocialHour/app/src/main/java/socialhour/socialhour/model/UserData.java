package socialhour.socialhour.model;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by michael on 3/16/17.
 */

public class UserData {
    private String user_email;
    private String user_display_name;
    private Uri    user_photo;
    private String user_uid;
    private String user_providerId;

    /*
        Basic mutators for user data
     */
    public void set_user_email(String email)
    {
        user_email = email;
    }
    public void set_user_display_name(String name)
    {
        user_display_name = name;
    }
    public void set_user_photo(Uri photo)
    {
        user_photo = photo;
    }
    public void set_uid(String uid) {user_uid = uid;}
    public void set_providerId(String providerId) {user_providerId = providerId;}

    /*
        Basic Accessors
     */
    public String get_user_display_name()
    {
        return user_display_name;
    }
    public String get_user_email()
    {
        return user_email;
    }
    public Uri get_user_photo()
    {
        return user_photo;
    }
    public String get_uid() {return user_uid;}
    public String getUser_providerId() {return user_providerId;}
}
