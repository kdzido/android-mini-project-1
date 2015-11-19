package pl.edu.pja.s13868.miniproject1.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import pl.edu.pja.s13868.miniproject1.R;

/**
 * The entry point of the application.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class MyMainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);
        initUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listButton:
                startActivity(new Intent(MyMainActivity.this, MyListActivity.class));
                break;
            case R.id.optionsButton:
                startActivity(new Intent(MyMainActivity.this, MyOptionsActivity.class));
                break;
        }
    }

    private void initUI(){
        // List button
        Button listButton = (Button) findViewById(R.id.listButton);
        listButton.setOnClickListener(this);

        // Options button
        Button optionsButton = (Button) findViewById(R.id.optionsButton);
        optionsButton.setOnClickListener(this);
    }
}
