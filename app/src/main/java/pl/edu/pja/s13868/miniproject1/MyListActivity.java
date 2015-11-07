package pl.edu.pja.s13868.miniproject1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;
import pl.edu.pja.s13868.miniproject1.domain.model.product.ProductRepository;
import pl.edu.pja.s13868.miniproject1.ui.list.ProductAdapter;

/**
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class MyListActivity extends AppCompatActivity {

    private ProductRepository productRepository;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        this.productRepository = SingletonRegistry.INSTANCE.productRepositorySingleton();

        Product[] productItems = this.productRepository.listAllProductsAsArray();
        this.productAdapter = new ProductAdapter(this, productItems);

        ListView productListView = (ListView) findViewById(R.id.listView);
        productListView.setChoiceMode(productListView.CHOICE_MODE_MULTIPLE);
        productListView.setAdapter(this.productAdapter);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = MyListActivity.this.productAdapter.getItem(position);


                Log.i("product_list_before", product.toString());
                if (product.isBought()) {
                    product.markAsNotBought();
                } else {
                    product.markAsBought();
                }
                Log.i("product_list_after", product.toString());
            }
        });
    }

}
