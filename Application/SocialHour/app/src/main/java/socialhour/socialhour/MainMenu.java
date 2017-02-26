package socialhour.socialhour;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.widget.NestedScrollView;

public class MainMenu extends AppCompatActivity {


    private ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        flipper = (ViewFlipper) findViewById(R.id.mainViewFlipper);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(item.getItemId() == R.id.action_settings) {
                            flipper.setDisplayedChild(flipper.indexOfChild(findViewById(R.id.settings_menu_view)));
                            return true;
                        }
                        else if(item.getItemId() == R.id.action_groups){
                            flipper.setDisplayedChild(flipper.indexOfChild(findViewById(R.id.groups_menu_view)));
                            return true;
                        }
                        else if(item.getItemId() == R.id.action_add){
                            flipper.setDisplayedChild(flipper.indexOfChild(findViewById(R.id.add_menu_view)));
                            return true;
                        }
                        else if(item.getItemId() == R.id.action_activity){
                            flipper.setDisplayedChild(flipper.indexOfChild(findViewById(R.id.main_menu_view)));
                            return true;
                        }
                        else if(item.getItemId() == R.id.action_friends){
                            flipper.setDisplayedChild(flipper.indexOfChild(findViewById(R.id.friends_menu_view)));
                            return true;
                        }
                        else {
                            throw new IllegalArgumentException(new Integer(item.getItemId()).toString());
                        }

                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*noinspection SimplifiableIfStatement
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }
}
