package cm.fitnessbud;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeInfoFragment extends Fragment {
    Cursor result=null;
    Cursor ingredients = null;
    DatabaseHelper db = null;
    String nomeReceita = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_recipes, container, false);

    }

    public static RecipeInfoFragment newInstance(int id) {
        RecipeInfoFragment cdf = new RecipeInfoFragment();
        Bundle args = new Bundle();
        args.putInt("_id", id);
        cdf.setArguments(args);
        return cdf;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        int id =getArguments().getInt("_id", 0);
        db = new DatabaseHelper(getActivity());
        result = db.getRecipe(id);
        ingredients = db.getRecipeIngredients(id);
        result.moveToFirst();

        TextView recipeName =(TextView) view.findViewById(R.id.recipeName);
        nomeReceita = result.getString(0);
        recipeName.setText( result.getString(0));
        TextView proteins = (TextView)  view.findViewById(R.id.RecipeProteins);
        proteins.setText( result.getString(2));
        TextView lipids = (TextView)  view.findViewById(R.id.RecipeFats);
        lipids.setText( result.getString(3));
        TextView carbs = (TextView)  view.findViewById(R.id.RecipeCarbs);
        carbs.setText( result.getString(4));
        TextView calories = (TextView)  view.findViewById(R.id.RecipeCalories);
        calories.setText( result.getString(1));

        ListView ingredientesLayout = (ListView) view.findViewById(R.id.listView2);
        SimpleCursorAdapter adapter=
                new SimpleCursorAdapter(getContext(), R.layout.row2,
                        ingredients, new String[] {
                        "nome_ingrediente",
                         "calorias_ingrediente",
                         "qnt"},
                        new int[] { R.id.title, R.id.value,R.id.qnt },
                        0);
        ingredientesLayout.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().setTitle(nomeReceita);
        inflater.inflate(R.menu.my_menu_das, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.add1) {
            Toast.makeText(getContext(),"THIS IS THE ADD",Toast.LENGTH_LONG).show();
            IngredientsListFragment details = new IngredientsListFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.recipes, details);
            ft.addToBackStack("INGREDIENT");
            ft.commit();

        }else if(item.getItemId()==R.id.delete1){

                db.deleteRecipe(getArguments().getInt("_id", 0));
                RecipeListFragment newFragment = new RecipeListFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.remove(this);
                ft.replace(R.id.recipes, newFragment);
                ft.commit();
                Toast.makeText(getContext(), "This recipe has been successfully deleted", Toast.LENGTH_LONG).show();


        }else if(item.getItemId()==R.id.save1){
            Toast.makeText(getContext(),"THIS IS THE SAVE",Toast.LENGTH_LONG).show();
        }

        return(super.onOptionsItemSelected(item));
    }

}