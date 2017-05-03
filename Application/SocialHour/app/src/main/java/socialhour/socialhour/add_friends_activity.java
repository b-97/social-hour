package socialhour.socialhour;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

public class add_friends_activity extends frontend_activity {

    private SearchView search_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_activity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friends, menu);
        MenuItem friend_item = menu.findItem(R.id.action_search);
        SearchView search_view = (SearchView) MenuItemCompat.getActionView(friend_item);

        search_view.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextChange (String newText) {
                        //TODO: UPDATE INTERFACE
                        return false;
                    }

                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                }
        );

        return true;
    }
}
