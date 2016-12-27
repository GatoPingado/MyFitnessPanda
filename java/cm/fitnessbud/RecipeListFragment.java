package cm.fitnessbud;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class RecipeListFragment extends ListFragment {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper db;
        db = new DatabaseHelper(getActivity());
        Cursor recipesInfo = db.getRecipeList();
        //#################
        /*
        long index = db.insertRecipe("Torradas à alentejana",1);
        db.insertIngredientIntoRecipe((int)index,1,1);
        index =db.insertRecipe("Torradas à alentejana",2);
        db.insertIngredientIntoRecipe((int)index,1,2);
        index =db.insertRecipe("Torradas à alentejana",3);
        db.insertIngredientIntoRecipe((int)index,1,3);
        index =db.insertRecipe("Torradas à alentejana",4);
        db.insertIngredientIntoRecipe((int)index,1,4);
        index =db.insertRecipe("Torradas à alentejana",5);
        db.insertIngredientIntoRecipe((int)index,1,5);
*/

        //####################
        try {
            SimpleCursorAdapter adapter =
                    new SimpleCursorAdapter(getContext(), R.layout.row,
                            recipesInfo, new String[]{
                            "nome_receita",
                            "calorias"},
                            new int[]{R.id.title, R.id.value},
                            0);
            setListAdapter(adapter);
            setHasOptionsMenu(true);
        } catch (RuntimeException ie) {
            ie.printStackTrace();
        }

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
        super.onListItemClick(l, v, position, id);
        RecipeInfoFragment details = RecipeInfoFragment.newInstance((int) id);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        //ft.remove(this);
        ft.addToBackStack("main");
        ft.replace(R.id.recipes, details);
        ft.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().setTitle("Lista de Receitas");
        inflater.inflate(R.menu.my_menu_a, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Toast.makeText(getContext(), "Lol", Toast.LENGTH_LONG).show();
            Log.d("lol", "lol");

        }
        return (super.onOptionsItemSelected(item));
    }
}