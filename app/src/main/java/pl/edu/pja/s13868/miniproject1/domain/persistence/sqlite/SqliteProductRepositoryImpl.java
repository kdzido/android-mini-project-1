package pl.edu.pja.s13868.miniproject1.domain.persistence.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;
import pl.edu.pja.s13868.miniproject1.domain.model.product.ProductRepository;

/**
 * The Sqlite-based implementation of the {@code ProductRepository}.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class SqliteProductRepositoryImpl extends SQLiteOpenHelper implements ProductRepository {

    private static SqliteProductRepositoryImpl instance;

    public static final String DATABASE_NAME = "mini-project1-db";
    public static final int DATABASE_VERSION = 1;
    public static final String PRODUCT_TABLE_NAME = "product";

    // TODO fix sql
    public static final String PRODUCT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS" + PRODUCT_TABLE_NAME + " (" +
                    "product_id TEXT PRIMARY KEY, " +
                    "product_name TEXT NOT NULL, " +
                    "is_bought INTEGER NOT NULL, " +
                    "version INTEGER );";


    /**
     * The singleton factory method.
     *
     * @param context the context
     * @return the product repository singleton
     */
    public static synchronized SqliteProductRepositoryImpl getInstance(final Context context) {
        if (SqliteProductRepositoryImpl.instance == null) {
            SqliteProductRepositoryImpl.instance = new SqliteProductRepositoryImpl(context.getApplicationContext());
        }
        return SqliteProductRepositoryImpl.instance;
    }


    private SqliteProductRepositoryImpl(final Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PRODUCT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("db", "db upgrade " + oldVersion + " -> " + newVersion + ": doing nothing");
    }


    @Override
    public Product findById(final String productId) {
        Product result = null;
        SQLiteDatabase db = getWritableDatabase();

        final Cursor cursor = db.query(
                PRODUCT_TABLE_NAME,
                new String[] {"product_id", "product_name", "is_bought"},
                "product_id = ?",
                new String[] {productId},
                null,
                null,
                null);

        try {
            if (cursor.moveToFirst()) {
                result = new Product(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2) > 0 ? true : false);
            }

        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return result;
    }

    @Override
    public Collection<Product> listAllProducts() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void store(final Product product) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void delete(String productId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Helpers

    private List<Product> cursorToProducts(final Cursor cursor) {
        List<Product> result = new ArrayList<>();
        do {
            if (cursor.getCount() > 0) {
                Product product = new Product(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2) > 0 ? true : false);
                result.add(product);

            }
        } while(cursor.moveToNext());
        cursor.close();

        return result;
    }

}
