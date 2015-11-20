package pl.edu.pja.s13868.miniproject1.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

import pl.edu.pja.s13868.miniproject1.EduApplication;
import pl.edu.pja.s13868.miniproject1.R;
import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;
import pl.edu.pja.s13868.miniproject1.domain.persistence.DataHandler;
import pl.edu.pja.s13868.miniproject1.ui.adapter.ProductArrayAdapter;

/**
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class MyListActivity extends AppCompatActivity implements View.OnClickListener, ProductArrayAdapter.OnOptionItemClick, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ProductArrayAdapter mProductArrayAdapter;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        initUI();
    }

    private void initUI() {
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        mProductArrayAdapter = new ProductArrayAdapter(getApplicationContext(), new ArrayList<Product>());
        mProductArrayAdapter.setOnOptionItemClick(this);

        ListView productListView = (ListView) findViewById(R.id.listView);
        productListView.setAdapter(mProductArrayAdapter);
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
    public void OnOptionClickListener(View pView, final Product pProduct) {
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
                        EduApplication.getDataManager().deleteProduct(pProduct.getId());
                        mProductArrayAdapter.deleteProductById(pProduct.getId());
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
    public void OnCheckedListner(final boolean pIsChecked, Product pProduct) {
        EduApplication.getDataManager().product(pProduct.getId(), new DataHandler<Product>() {
            @Override
            public void onSuccess(Product pObject) {
                // TODO validate nulls
                if (pIsChecked) {
                    pObject.markAsBought();
                } else {
                    pObject.markAsNotBought();
                }
                EduApplication.getDataManager().storeProduct(pObject);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EduApplication.getDataManager().products(new DataHandler<Product>() {
            @Override
            public void onSuccess(List<Product> pDataList) {
                if (pDataList != null && pDataList.size() > 0) {
                    mProductArrayAdapter.updateData(pDataList);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startActivity(new Intent(getApplicationContext(), EditActivity.class));
                break;
        }
    }
}
