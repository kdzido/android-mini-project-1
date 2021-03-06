package pl.edu.pja.s13868.miniproject1.ui.list;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pja.s13868.miniproject1.R;
import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;

/**
 * The product list view adapter.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class ProductArrayAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> products = new ArrayList<>();

    public ProductArrayAdapter(final Context context, final List<Product> productList) {
        super(context, R.layout.product_list_row, productList);

        this.context = context;
        this.products.addAll(productList);
    }

    private class ViewHolder {
        CheckBox productBought;
        TextView productName;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Log.v("CovertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.product_list_row, null);

            holder = new ViewHolder();
            holder.productBought = (CheckBox) convertView.findViewById(R.id.productBought);
            holder.productName = (TextView) convertView.findViewById(R.id.productName);
            convertView.setTag(holder);

            holder.productName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Product product = (Product) cb.getTag();
                    Toast.makeText(getContext(), "Clicked on CheckBox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                }
            });


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productName.setTag(product);
        holder.productBought.setChecked(product.isBought());

//        CheckBox cb = (CheckBox) convertView.findViewById(R.id.productBought);
//        if (products.get(position).isBought()) {
//            cb.setChecked(true);
//        } else {
//            cb.setChecked(false);
//        }
//        TextView name = (TextView) convertView.findViewById(R.id.productName);
//        name.setText(products.get(position).getName());

        return convertView;
    }

}
