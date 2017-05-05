package socialhour.socialhour;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import socialhour.socialhour.adapter.Friend_Search_Adapter;
import socialhour.socialhour.model.UserData;

public class add_friends_activity extends frontend_activity {

    private Friend_Search_Adapter friendAdapter;
    private RecyclerView result_recycler_view;
    private DatabaseReference lDatabase;
    private ArrayList<UserData> fArrayList;
    private boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_activity);

        fArrayList = new ArrayList<UserData>();
        friendAdapter = new Friend_Search_Adapter(fArrayList, this.getApplicationContext());


        result_recycler_view = (RecyclerView) findViewById(R.id.result_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        result_recycler_view.setLayoutManager(layoutManager);
        result_recycler_view.setAdapter(friendAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lDatabase = FirebaseDatabase.getInstance().getReference("users");

        lDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserData user = dataSnapshot.getValue(UserData.class);
                fArrayList.add(user);
                friendAdapter.getFilter().filter("");
                friendAdapter.notifyDataSetChanged();
                Log.d("ADDED USER", null, null);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FAILED!!", null, null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        final Menu menu2 = menu;
        getMenuInflater().inflate(R.menu.menu_add_friends, menu2);

        final MenuItem friend_item = menu2.findItem(R.id.action_search);


        SearchView search_view = (SearchView) MenuItemCompat.getActionView(friend_item);
        friend_item.expandActionView();

        search_view.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextChange (String newText) {
                        //TODO: UPDATE INTERFACE
                        if(!finished){
                            friendAdapter.getFilter().filter(newText);
                        }
                        return false;
                    }

                    public boolean onQueryTextSubmit(String query) {
                        return false; //we want live results
                    }
                }
        );
        search_view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                menu2.close();
                finished = true;
                finish();
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
