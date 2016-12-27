package cm.fitnessbud;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cm.fitnessbud.R;

public class IngredientsListFragment extends ListFragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Creates the list from the array defined in the XML file, using an ArrayAdapter
        setListAdapter(ArrayAdapter.createFromResource(getActivity(),
                R.array.goals, android.R.layout.simple_list_item_activated_1));
        setHasOptionsMenu(true);
    }

    //We are only concerned with reloading previous status on resumption
    //There is stuff that it should only be done on creation
    @Override
    public void onResume() {
        super.onResume();

    }

    //Called before an activity is killed, to be able to later restore the per-instance state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.my_menu_a, menu);
    }

}