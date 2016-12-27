package cm.fitnessbud;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button_tracker = (Button) findViewById(R.id.button_tracker);
        button_tracker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tracker.class);
                startActivity(intent);
            }

        });
        final Button button_nutrition = (Button) findViewById(R.id.button_nutrition);
        button_nutrition.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getApplicationContext(), Nutrition.class);
                startActivity(intent);
            }

        });

        final Button button_recipes = (Button) findViewById(R.id.button_recipes);
        button_recipes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getApplicationContext(), Recipes.class);
                startActivity(intent);
            }

        });
        final Button button_ingredients = (Button) findViewById(R.id.button_ingredients);
        button_ingredients.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getApplicationContext(), Ingredients.class);
                startActivity(intent);
            }

        });
        final Button button_conf = (Button) findViewById(R.id.button_conf);
        button_conf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getApplicationContext(), Configurations.class);
                startActivity(intent);
            }

        });
    }

}
