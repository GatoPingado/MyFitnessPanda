package cm.fitnessbud;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class Recipes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
    }


    @Override
    protected void onStart() {
        super.onStart();

        Fragment linkFrame = getSupportFragmentManager().findFragmentById(R.id.recipes);

        if (linkFrame==null)
        {
            RecipeListFragment linksFragment = new RecipeListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.recipes, linksFragment).commit();
        } else if (!linkFrame.isVisible()) getSupportFragmentManager().popBackStack();

    }


}
