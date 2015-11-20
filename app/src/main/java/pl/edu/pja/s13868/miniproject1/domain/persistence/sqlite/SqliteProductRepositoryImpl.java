package pl.edu.pja.s13868.miniproject1.domain.persistence.sqlite;

import android.content.ContentValues;
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


    public static final String PRODUCT_TABLE_DROP = "DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME + ";";

    // TODO fix sql
    public static final String PRODUCT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE_NAME + " (" +
                    "product_id TEXT PRIMARY KEY, " +
                    "product_name TEXT NOT NULL, " +
                    "is_bought INTEGER NOT NULL);";

    public static final String PRODUCT_TABLE_INITIAL_DATA =
            "INSERT INTO " + PRODUCT_TABLE_NAME + " (product_id, product_name, is_bought)" +
                    "VALUES " +
                    "(1, 'product1', 0), " +
                    "(2, 'product2', 1), " +
                    "(3, 'product3', 0);";

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

    // SQLiteOpenHelper lifecycle methods

    @Override
    public void onCreate(final SQLiteDatabase db) {
//        Log.i("sqlite_repo", "DROPPING DB TABLE: " + PRODUCT_TABLE_NAME);
//        db.execSQL(PRODUCT_TABLE_DROP);

        Log.i("sqlite_repo", "Creating DB: " + DATABASE_NAME + ", ver. " + DATABASE_VERSION);
        db.execSQL(PRODUCT_TABLE_CREATE);

        // TODO disable init data
        Log.i("sqlite_repo", "Inserting initial data to: " + DATABASE_NAME + "." + PRODUCT_TABLE_NAME);
        db.execSQL(PRODUCT_TABLE_INITIAL_DATA);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        Log.i("sqlite_repo", "db upgrade " + oldVersion + " -> " + newVersion + ": doing nothing");
    }


    // Queries

    @Override
    public Product findById(final String productId) {
        Log.i("sqlite_repo", "Finding product, ID: " + productId);
        Product result = null;

        // Create/open the database for writing.
        // Nonetheless, the connection is cached so it's not expensive to call multiple times.
        SQLiteDatabase db = getWritableDatabase();

        final Cursor cursor = db.query(
                PRODUCT_TABLE_NAME,
                new String[] {"product_id", "product_name", "is_bought"},
                "product_id = ?",
                new String[] {productId},
                null, null, null);

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
        final List<Product> result = new ArrayList<>();

        // Create/open the database for writing.
        // Nonetheless, the connection is cached so it's not expensive to call multiple times.
        SQLiteDatabase db = getWritableDatabase();
        final Cursor cursor = db.query(
                PRODUCT_TABLE_NAME,
                new String[]{"product_id", "product_name", "is_bought"},
                null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    final Product p = toProduct(cursor);
                    result.add(p);

                } while (cursor.moveToNext());
            }

        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        Log.i("sqlite_repo", "Listing all products: " + result);
        return result;
    }


    // Modifiers

    @Override
    public void store(final Product product) {
        Log.i("sqlite_repo", "Storing product: " + product);
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

//        // Create/open the database for writing.
//        // Nonetheless, the connection is cached so it's not expensive to call multiple times.
//        SQLiteDatabase db = getWritableDatabase();
//
//        db.beginTransaction();
//        try {
//            Integer productId = addOrUpdate()
//
//            db.setTransactionSuccessful();
//
//        } finally {
//            db.endTransaction();
//        }

        throw new UnsupportedOperationException("Not implemented yet.");
    }


//    public Integer addOrUpdateProduct(final Product product) {
//        // Create/open the database for writing.
//        // Nonetheless, the connection is cached so it's not expensive to call multiple times.
//        SQLiteDatabase db = getWritableDatabase();
//
//        Integer productId = -1;
//
//        db.beginTransaction();
//        try {
//            final ContentValues values = new ContentValues();
//            values.put()
//
//
//            db.setTransactionSuccessful();
//
//        } finally {
//            db.endTransaction();
//        }
//    }

    @Override
    public void delete(final String productId) {
        Log.i("sqlite_repo", "Deleting product, ID: " + productId);

        // Create/open the database for writing.
        // Nonetheless, the connection is cached so it's not expensive to call multiple times.
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(PRODUCT_TABLE_NAME, "product_id = ?", new String[] {productId});
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }


    // Helpers

    private Product toProduct(final Cursor cursor) {
        final Product product = new Product(
                String.valueOf(cursor.getInt(0)),
                cursor.getString(1),
                cursor.getInt(2) > 0 ? true : false);

        return product;
    }

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
