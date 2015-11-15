package pl.edu.pja.s13868.miniproject1.ui.list.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import pl.edu.pja.s13868.miniproject1.R;

/**
 * The activity for handling the options view.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class MyOptionsActivity extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "mini-project-1-prefs" ;

    public static final String FONT_SIZE_PREF = "font-size";
    public static final String FONT_COLOUR_PREF = "font-colour";

    public static final String SAMPLE_VALUE = "value_1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_options);


        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(FONT_COLOUR_PREF, SAMPLE_VALUE);
        editor.commit();
        Log.i("tag", "Stored shared pref '" + FONT_COLOUR_PREF + "':" + SAMPLE_VALUE);

        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String value = settings.getString(FONT_COLOUR_PREF, null);
        Log.i("tag", "Loaded shared pref '" + FONT_COLOUR_PREF + "':" + value);

    }

}
