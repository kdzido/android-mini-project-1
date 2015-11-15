package pl.edu.pja.s13868.miniproject1.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.edu.pja.s13868.miniproject1.R;
import pl.edu.pja.s13868.miniproject1.domain.helpers.SingletonRegistry;
import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;

public class EditActivity extends AppCompatActivity {
    public final static String TAG_PRODUCT = "TAG_PRODUCT";

    private Product mProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initToolbar();
        initUI();
    }

    private void initUI(){
        Button save = (Button) findViewById(R.id.save);
        final EditText editText = (EditText) findViewById(R.id.product_edit);


        mProduct = (Product) getIntent().getSerializableExtra(TAG_PRODUCT);
        Log.d("pp", "===> product:" + mProduct.toString());

        if(mProduct != null){
            editText.setText(mProduct.getName());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProduct != null){
                    Product p = SingletonRegistry.INSTANCE.productRepositorySingleton().findById(mProduct.getId());
                    p.changeName(editText.getText().toString());
                    SingletonRegistry.INSTANCE.productRepositorySingleton().store(p);
                    Toast.makeText(getApplicationContext(), "Product was modified", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
