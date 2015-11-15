package pl.edu.pja.s13868.miniproject1.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pja.s13868.miniproject1.R;
import pl.edu.pja.s13868.miniproject1.domain.helpers.SingletonRegistry;
import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;
import pl.edu.pja.s13868.miniproject1.domain.model.product.ProductRepository;
import pl.edu.pja.s13868.miniproject1.ui.adapter.ProductArrayAdapter;

/**
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class MyListActivity extends AppCompatActivity implements ProductArrayAdapter.OnOptionItemClick, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ProductRepository mProductRepository;
    private ProductArrayAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        initUI();
    }

    private void initUI() {
        this.mProductRepository = SingletonRegistry.INSTANCE.productRepositorySingleton();
        List<Product> productItems = new ArrayList<>(this.mProductRepository.listAllProducts());
        this.productAdapter = new ProductArrayAdapter(this, productItems);
        this.productAdapter.setOnOptionItemClick(this);

        ListView productListView = (ListView) findViewById(R.id.listView);
        productListView.setAdapter(this.productAdapter);
        productListView.setOnItemClickListener(this);
        productListView.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void OnOptionClick(View pView, final Product pProduct) {
        PopupMenu popupMenu = new PopupMenu(this, pView);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                        intent.putExtra(EditActivity.TAG_PRODUCT, pProduct);
                        startActivity(intent);
                        return true;
                    case R.id.action_delete:
                        SingletonRegistry.INSTANCE.productRepositorySingleton().delete(pProduct.getId());
                        productAdapter.deleteProductById(pProduct.getId());
                        productAdapter.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        productAdapter.notifyDataSetChanged();
    }
}
