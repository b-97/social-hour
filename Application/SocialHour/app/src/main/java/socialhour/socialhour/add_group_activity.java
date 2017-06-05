package socialhour.socialhour;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import socialhour.socialhour.adapter.Friend_Search_Adapter;
import socialhour.socialhour.model.FriendData;
import socialhour.socialhour.model.PublicUserData;

import static socialhour.socialhour.tools.FirebaseData.decodeEmail;

public class add_group_activity extends frontend_activity {


    private ArrayList<PublicUserData> group_members;
    private String group_name;
    private String group_description;
    private PublicUserData group_owner;
    private boolean EVENT_CREATION_CANCELLED;
    private Friend_Search_Adapter adapter;
    private DatabaseReference lDatabase;
    private RecyclerView result_recycler_view;
    private FirebaseUser local_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Create Group");
        setSupportActionBar(toolbar);
        EVENT_CREATION_CANCELLED = true;

        local_user = FirebaseAuth.getInstance().getCurrentUser();
        final String local_user_email = local_user.getEmail();

        group_members = new ArrayList<PublicUserData>();

        Bundle bundle = getIntent().getExtras();
        final ArrayList<String> email_list = bundle.getStringArrayList("email_list");
        adapter = new Friend_Search_Adapter(this.getApplicationContext(), group_members);

        result_recycler_view = (RecyclerView) findViewById(R.id.group_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        result_recycler_view.setLayoutManager(layoutManager);
        result_recycler_view.setAdapter(adapter);

        lDatabase = FirebaseDatabase.getInstance().getReference("public_user_data");
        lDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PublicUserData user = dataSnapshot.getValue(PublicUserData.class);
                boolean is_user_local = false;
                if(decodeEmail(user.get_email()).compareTo(local_user_email) == 0)
                    is_user_local = true;
                if(!is_user_local && email_list != null) {
                    for(int i = 0; i < email_list.size(); i++){
                        if(email_list.get(i).compareTo(user.get_email()) == 0){
                            group_members.add(user);
                            adapter.notifyDataSetChanged();
                            Log.d("Added user", null, null);
                            Toast.makeText(getBaseContext(), user.get_email(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                attemptFinish(view);
            }
        });
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*
        This method overrides frontend_activity's onCreateOptionsMenu
        so that the ability to access the user settings does not appear.
     */
    private void attemptFinish(View view){
        if(invalid_fields()){
            Snackbar.make(view, "Make sure you fill out event data first!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else {
            EVENT_CREATION_CANCELLED = false;
            finish();
        }
    }
    protected boolean invalid_fields(){
        return ((((TextView) findViewById(R.id.display_name_edittext)).getText().toString().length() < 1) ||
                (((TextView) findViewById(R.id.group_description_edittext)).getText().toString().length() < 1 ));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
}
