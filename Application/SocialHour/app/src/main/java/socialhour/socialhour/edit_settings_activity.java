package socialhour.socialhour;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import socialhour.socialhour.R;

public class edit_settings_activity extends frontend_activity {

    private String display_name;
    private boolean is_24_hr;
    private int privacy_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        display_name = extras.getString("display_name");
        is_24_hr = extras.getBoolean("is_24_hr");
        privacy_pref = extras.getInt("privacy");

        ((TextView) findViewById(R.id.display_name_edittext)).setText(display_name);


        if(is_24_hr){
            ((RadioButton) findViewById(R.id.radio_button_24hr)).setChecked(true);
            ((RadioButton) findViewById(R.id.radio_button_12hr)).setChecked(false);
        }
        else{
            ((RadioButton) findViewById(R.id.radio_button_24hr)).setChecked(false);
            ((RadioButton) findViewById(R.id.radio_button_12hr)).setChecked(true);
        }
        if(privacy_pref == 1){
            ((RadioButton) findViewById(R.id.radio_privacy_public)).setChecked(true);
            ((RadioButton) findViewById(R.id.radio_privacy_private)).setChecked(false);
        }
        else{
            ((RadioButton) findViewById(R.id.radio_privacy_public)).setChecked(false);
            ((RadioButton) findViewById(R.id.radio_privacy_private)).setChecked(true);
        }

    }

    public void attemptFinish(){
        TextView display_name_textedit = (TextView) findViewById(R.id.display_name_edittext);
        String display_name = display_name_textedit.getText().toString();
        if (display_name.length() <= 2){
            Snackbar.make(this.findViewById(android.R.id.content),"Hey, make sure your name is at " +
                    "least 3 letters long first!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else
            finish();
    }

    @Override
    public void finish(){
        Intent data = new Intent();
        String display_name = ((TextView) findViewById(R.id.display_name_edittext)).getText().toString();
        boolean is24hr = ((RadioButton) findViewById(R.id.radio_button_24hr)).isChecked();
        int privacy;
        if(((RadioButton) findViewById(R.id.radio_privacy_private)).isChecked())
            privacy = 2;
        else
            privacy = 1;

        data.putExtra("display_name", display_name);
        data.putExtra("is_24_hr", is24hr);
        data.putExtra("privacy", privacy);

        setResult(RESULT_OK, data);
        super.finish();
    }
    /*
        This method overrides frontend_activity's onCreateOptionsMenu
        so that the ability to access the user settings does not appear.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        attemptFinish();
        return true;
    }
    @Override
    public void onBackPressed() {
        attemptFinish();
    }
}
