package pl.edu.pja.s13868.miniproject1.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import pl.edu.pja.s13868.miniproject1.EduApplication;
import pl.edu.pja.s13868.miniproject1.R;
import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;
import pl.edu.pja.s13868.miniproject1.domain.persistence.DataHandler;
import pl.edu.pja.s13868.miniproject1.domain.persistence.database.ProductsDataSource;

public class EditActivity extends AppCompatActivity {
    public static final int PRODUCT_REQUEST = 1;
    public final static String TAG_PRODUCT = "TAG_PRODUCT";

    private Product mProduct;
    private ProductsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initUI();
        setupLogic();
    }

    public void setupLogic() {
        datasource = new ProductsDataSource(this);
        datasource.open();
    }

    private void initUI() {
        Button save = (Button) findViewById(R.id.save);
        final EditText editText = (EditText) findViewById(R.id.product_edit);


        mProduct = (Product) getIntent().getSerializableExtra(TAG_PRODUCT);

        if (mProduct != null) {
            editText.setText(mProduct.getName());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProduct != null) {
                    EduApplication.getDataManager().product(mProduct.getId(), new DataHandler<Product>() {
                        @Override
                        public void onSuccess(Product pProduct) {
                            pProduct.changeName(editText.getText().toString());

                            EduApplication.getDataManager().modifyProduct(pProduct);
                            Toast.makeText(getApplicationContext(), "Product was persisted", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                } else {
                    if (!editText.getText().toString().isEmpty()) {
                        Random ran = new Random();
                        String x = Integer.toString(ran.nextInt(100));
                        Product product = new Product(x, editText.getText().toString(), false);

                        product = datasource.createProduct(product);

                        EduApplication.getDataManager().addProduct(product);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Valid product name is required", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
