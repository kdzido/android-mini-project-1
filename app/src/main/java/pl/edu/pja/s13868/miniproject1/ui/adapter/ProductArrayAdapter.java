package pl.edu.pja.s13868.miniproject1.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.edu.pja.s13868.miniproject1.R;
import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;

/**
 * The product list view adapter.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class ProductArrayAdapter extends ArrayAdapter<Product> {
    private LayoutInflater mInflater;
    private List<Product> mProducts;
    private OnOptionItemClick mOnOptionItemClick;

    public ProductArrayAdapter(final Context pContext, final List<Product> pProductList) {
        super(pContext, 0, pProductList);
        mProducts = pProductList;
        mInflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        CheckBox productBought;
        TextView productName;
        ImageView mImageView;
    }

    public void setOnOptionItemClick(OnOptionItemClick pOnOptionItemClick) {
        mOnOptionItemClick = pOnOptionItemClick;
    }

    public void updateData(List<Product> pProductList) {
        mProducts.clear();
        mProducts.addAll(pProductList);
        notifyDataSetChanged();
    }

    public void deleteProductById(final String pProductId) {
        Set<String> productsToDelete = new HashSet<>();

        for (Product product : mProducts) {
            if (product.getId().equals(pProductId)) {
                productsToDelete.add(product.getId());
            }
        }

        Product toDelete = null;
        for (Product p : mProducts) {
            if (p.getId().equals(pProductId)) {
                toDelete = p;
                break;
            }
        }

        // removes all occurrences of the product on the list (defensive)
        while (mProducts.remove(toDelete)) {
            remove(toDelete);
        }

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.product_list_row, null);

            holder = new ViewHolder();
            holder.productBought = (CheckBox) convertView.findViewById(R.id.productBought);
            holder.productName = (TextView) convertView.findViewById(R.id.productName);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.options);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Product product = mProducts.get(position);
        holder.productName.setText(product.getName());
        holder.productBought.setChecked(product.isBought());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnOptionItemClick != null) {
                    mOnOptionItemClick.OnOptionClick(v, product);
                }
            }
        });

        return convertView;
    }

    public interface OnOptionItemClick {
        void OnOptionClick(View pView, Product pProduct);
    }
}
