package cm.fitnessbud;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cm.fitnessbud.R;

public class IngredientsFragment extends ListFragment {
    boolean dualFrame;
    int curPosition=0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        outState.putInt("curPosition", curPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
    }




}
