package socialhour.socialhour.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by michael on 3/16/17.
 */

public class UserData {
    private static String user_first_name;
    private static String user_last_name;
    private static String user_email;
    private static String user_given_name;
    private static Uri    user_photo;
    private static String user_id;
    private static Bitmap user_bitmap;

    /*
        Basic mutators for user data
     */
    public static void set_user_first_name(String firstname)
    {
        user_first_name = firstname;
    }
    public static void set_user_last_name(String lastname)
    {
        user_last_name = lastname;
    }
    public static void set_user_email(String email)
    {
        user_email = email;
    }
    public static void set_user_given_name(String name)
    {
        user_given_name = name;
    }
    public static void set_user_photo(Uri photo)
    {
        user_photo = photo;
    }
    public static void set_user_id(String id)
    {
        user_id = id;
    }
    public static void set_user_bitmap(Bitmap bp)
    {
        user_bitmap = bp;
    }

    /*
        Basic Accessors
     */
    public static String get_user_first_name()
    {
        return user_first_name;
    }
    public static String get_user_last_name()
    {
        return user_last_name;
    }
    public static String get_user_given_name()
    {
        return user_first_name;
    }
    public static String get_user_email()
    {
        return user_email;
    }
    public static String get_user_id()
    {
        return user_id;
    }
    public static Uri get_user_photo()
    {
        return user_photo;
    }
    public static Bitmap get_user_bitmap()
    {
        return user_bitmap;
    }
}
