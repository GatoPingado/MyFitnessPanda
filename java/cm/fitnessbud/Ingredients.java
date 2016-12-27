package cm.fitnessbud;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class Ingredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
    }


    @Override
    protected void onStart() {
        super.onStart();

        Fragment linkFrame = getSupportFragmentManager().findFragmentById(R.id.ingredients);

        if (linkFrame==null)
        {
            IngredientsListFragment linksFragment = new IngredientsListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.ingredients, linksFragment).commit();
        } else if (!linkFrame.isVisible()) getSupportFragmentManager().popBackStack();

    }


}
