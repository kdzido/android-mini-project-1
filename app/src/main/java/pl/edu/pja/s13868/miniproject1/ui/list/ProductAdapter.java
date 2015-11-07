package pl.edu.pja.s13868.miniproject1.ui.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import pl.edu.pja.s13868.miniproject1.R;
import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;

/**
 * The product list view adapter.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private Product[] productItems;

    public ProductAdapter(final Context context, final Product[] products) {
        super(context, R.layout.product_list_row, products);

        this.context = context;
        this.productItems = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.product_list_row, parent, false);

        CheckBox cb = (CheckBox) convertView.findViewById(R.id.productBought);
        TextView name = (TextView) convertView.findViewById(R.id.productName);
        name.setText(productItems[position].getName());

        if (productItems[position].isBought()) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }
        return convertView;
    }

}
