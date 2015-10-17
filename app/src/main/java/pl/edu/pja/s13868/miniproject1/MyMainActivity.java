package pl.edu.pja.s13868.miniproject1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MyMainActivity extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "mini-project-1-prefs" ;
    public static final String PRODUCT_1_KEY = "product_1";
    public static final String PROLDUCT_1_VALUE = "value_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // List button
        Button listButton = (Button) findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyMainActivity.this, MyListActivity.class));
            }
        });

        // Options button
        Button optionsButton = (Button) findViewById(R.id.optionsButton);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyMainActivity.this, MyOptionsActivity.class));
            }
        });

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(PRODUCT_1_KEY, PROLDUCT_1_VALUE);
        editor.commit();
        Log.i("tag", "Stored shared pref '" + PRODUCT_1_KEY + "':" + PROLDUCT_1_VALUE);

        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String value = settings.getString(PRODUCT_1_KEY, null);
        Log.i("tag", "Loaded shared pref '" + PRODUCT_1_KEY + "':" +  value);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
