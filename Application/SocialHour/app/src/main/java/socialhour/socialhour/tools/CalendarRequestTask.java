package socialhour.socialhour.tools;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.client.util.DateTime;
import java.util.Calendar.*;

import com.google.api.services.calendar.model.*;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import socialhour.socialhour.model.EventData;
import socialhour.socialhour.model.EventItem;

/**
 * Created by michael on 5/12/17.
 */

public class CalendarRequestTask extends AsyncTask<EventItem, EventItem, EventItem>
{
        private com.google.api.services.calendar.Calendar mService = null;
        private com.google.api.services.calendar.Calendar calServ = null;

        private Exception mLastError = null;

        public CalendarRequestTask(GoogleAccountCredential mCredential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();


            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, mCredential)
                    .setApplicationName("Social Hour")
                    .build();

             calServ = new com.google.api.services.calendar.Calendar.Builder
                    (transport, jsonFactory, mCredential)
                    .setApplicationName("Social Hour")
                    .build();

        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected EventItem doInBackground(EventItem... params) {
            try {
                return pushEvent(params[1]);
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        public EventItem pushEvent(EventItem local_event){
            Calendar start_cal = Calendar.getInstance();
            start_cal.set(Calendar.YEAR, local_event.get_start_year());
            start_cal.set(Calendar.MONTH, local_event.get_start_month());
            start_cal.set(Calendar.DAY_OF_MONTH, local_event.get_start_date());
            start_cal.set(Calendar.HOUR, local_event.get_start_hour());
            start_cal.set(Calendar.MINUTE, local_event.get_start_minute());
            Calendar end_cal = Calendar.getInstance();
            end_cal.set(Calendar.YEAR, local_event.get_end_year());
            end_cal.set(Calendar.MONTH, local_event.get_end_month());
            end_cal.set(Calendar.DAY_OF_MONTH, local_event.get_end_date());
            end_cal.set(Calendar.HOUR, local_event.get_end_hour());
            end_cal.set(Calendar.MINUTE, local_event.get_end_minute());

            Date start_date = start_cal.getTime();
            Date end_date = start_cal.getTime();

            DateTime start_date_time = new DateTime(start_date);
            DateTime end_date_time = new DateTime(end_date);

            EventDateTime event_start_date_time = new EventDateTime()
                    .setDateTime(start_date_time).setTimeZone(TimeZone.getDefault().toString());
            EventDateTime event_end_date_time = new EventDateTime()
                    .setDateTime(end_date_time).setTimeZone(TimeZone.getDefault().toString());

            Event google_event = new Event()
                    .setSummary(local_event.get_name())
                    .setLocation(local_event.get_location())
                    .setDescription(local_event.get_description())
                    .setStart(event_start_date_time)
                    .setEnd(event_end_date_time);
            try {
                google_event = mService.events().insert("Social Hour", google_event).execute();
                Log.d("Event000 created: %s\n", google_event.getHtmlLink());
                return null;
            }
            catch(IOException e){
                ArrayList<String> test = new ArrayList<>();
                return null;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    start = event.getStart().getDate();
                }
                eventStrings.add(
                        String.format("%s (%s)", event.getSummary(), start));
            }
            return eventStrings;
        }


        @Override
        protected void onPreExecute() { /*TODO: IMPLEMENTATION???*/   }

        @Override
        protected void onPostExecute(EventItem output) {
            if (output == null) { } else {
                //TODO: IMPLEMENTATION
            }
        }

        @Override
        protected void onCancelled() {}
    }
