package pl.edu.pja.s13868.miniproject1.ui.list;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by edzikrz on 07.11.15.
 */
public class ProductItemHolder {

    private CheckBox checkBox;
    private TextView textView;

    public ProductItemHolder() {
    }

    public ProductItemHolder(CheckBox checkBox, TextView textView) {
        this.checkBox = checkBox;
        this.textView = textView;
    }

    
}
