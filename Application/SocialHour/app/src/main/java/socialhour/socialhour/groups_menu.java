package socialhour.socialhour;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
    Currently unused import statements; put these back in the code if necessary
    import android.support.v7.widget.Toolbar;
 */

public class groups_menu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups_menu, container, false);
        return view;

    }
}