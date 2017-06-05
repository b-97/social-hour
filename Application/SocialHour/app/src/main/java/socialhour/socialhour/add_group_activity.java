package socialhour.socialhour;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import socialhour.socialhour.model.PublicUserData;

public class add_group_activity extends frontend_activity {


    private ArrayList<PublicUserData> group_members;
    private String group_name;
    private String group_description;
    private PublicUserData group_owner;
    private boolean EVENT_CREATION_CANCELLED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Create Group");
        setSupportActionBar(toolbar);
        EVENT_CREATION_CANCELLED = true;

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
