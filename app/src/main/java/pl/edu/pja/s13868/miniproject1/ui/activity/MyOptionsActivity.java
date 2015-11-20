package pl.edu.pja.s13868.miniproject1.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.edu.pja.s13868.miniproject1.EduApplication;
import pl.edu.pja.s13868.miniproject1.R;

/**
 * The activity for handling the options view.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class MyOptionsActivity extends AppCompatActivity {

    private EditText mFontSize;
    private EditText mFontColor;
    private Button mSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_options);
        initUI();
        setupLogic();
    }

    private void initUI(){
        mFontSize = (EditText) findViewById(R.id.font_size);
        mFontColor = (EditText) findViewById(R.id.font_color);
        mSave = (Button) findViewById(R.id.save);
    }

    private void setupLogic(){
        mFontSize.setText(String.valueOf(EduApplication.dataManagerSingleton().getFontSize()));
        mFontColor.setText(EduApplication.dataManagerSingleton().getFontColor());

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EduApplication.dataManagerSingleton().saveFontSize(Integer.valueOf(mFontSize.getText().toString()));
                EduApplication.dataManagerSingleton().saveFontColor(mFontColor.getText().toString());
                Toast.makeText(getApplicationContext(), "New settintgs saved", Toast.LENGTH_LONG).show();
            }
        });
    }
}
