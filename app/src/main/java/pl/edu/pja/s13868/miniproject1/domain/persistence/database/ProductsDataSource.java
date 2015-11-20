package pl.edu.pja.s13868.miniproject1.domain.persistence.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;

/**
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class ProductsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private DbSQLiteHelper dbHelper;
    private String[] allColumns = {
            DbSQLiteHelper.COLUMN_ID,
            DbSQLiteHelper.COLUMN_PRODUCT_NAME,
            DbSQLiteHelper.COLUMN_BOUGHT
    };

    public ProductsDataSource(Context context) {
        dbHelper = new DbSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Product createProduct(Product pProduct) {
        ContentValues values = new ContentValues();
        values.put(DbSQLiteHelper.COLUMN_PRODUCT_NAME, pProduct.getName());
        values.put(DbSQLiteHelper.COLUMN_BOUGHT, pProduct.isBought() ? 1 : 0);
        long insertId = database.insert(DbSQLiteHelper.TABLE_PRODUCTS, null, values);

        Cursor cursor = database.query(
                DbSQLiteHelper.TABLE_PRODUCTS,
                allColumns,
                DbSQLiteHelper.COLUMN_ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();

        Product product = cursorToProduct(cursor);
        cursor.close();
        return product;
    }

    public void deleteProduct(Product pProduct) {
        long id = Long.valueOf(pProduct.getId());
        System.out.println("Product deleted with id: " + id);

        database.delete(DbSQLiteHelper.TABLE_PRODUCTS, DbSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Product> getAllComments() {
        List<Product> products = new ArrayList<Product>();

        Cursor cursor = database.query(DbSQLiteHelper.TABLE_PRODUCTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            products.add(product);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return products;
    }


    // helpers

    private Product cursorToProduct(Cursor cursor) {
        Product product = new Product(
                String.valueOf(cursor.getInt(0)),
                cursor.getString(1),
                cursor.getInt(2) > 0 ? true : false);
        return product;
    }
}
