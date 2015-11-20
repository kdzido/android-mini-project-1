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
    public static final String PRODUCT_TABLE = "product";


    public static final String PRODUCT_TABLE_DROP = "DROP TABLE IF EXISTS " + PRODUCT_TABLE + ";";

    public static final String PRODUCT_ID_COLUMN = "product_id";
    public static final String PRODUCT_NAME_COLUMN = "product_name";
    public static final String PRODUCT_IS_BOUGHT_COLUMN = "is_bought";

    public static final String PRODUCT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE + " (" +
                    PRODUCT_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCT_NAME_COLUMN + " TEXT NOT NULL, " +
                    PRODUCT_IS_BOUGHT_COLUMN + " INTEGER NOT NULL);";

    public static final String PRODUCT_TABLE_INITIAL_DATA =
            "INSERT INTO " + PRODUCT_TABLE + " (" +
                    PRODUCT_ID_COLUMN + ", " +
                    PRODUCT_NAME_COLUMN + ", " +
                    PRODUCT_IS_BOUGHT_COLUMN + ") " +
                    "VALUES " +
                    "(null, 'initial_product1', 0), " +
                    "(null, 'initial_product2', 1), " +
                    "(null, 'initial_product3', 0);";

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
//        Log.i("sqlite_repo", "DROPPING DB TABLE: " + PRODUCT_TABLE);
//        db.execSQL(PRODUCT_TABLE_DROP);

        Log.i("sqlite_repo", "Creating DB: " + DATABASE_NAME + ", ver. " + DATABASE_VERSION);
        db.execSQL(PRODUCT_TABLE_CREATE);

        // TODO disable init data
        Log.i("sqlite_repo", "Inserting initial data to: " + DATABASE_NAME + "." + PRODUCT_TABLE);
        db.execSQL(PRODUCT_TABLE_INITIAL_DATA);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        Log.i("sqlite_repo", "DB upgrade triggered: " + oldVersion + " -> " + newVersion);

        if (oldVersion != newVersion) {
            Log.i("sqlite_repo",  "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

            // dropping
            db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
            // recreating
            onCreate(db);
        }
    }


    // Queries

    @Override
    public Product findById(final Long productId) {
        Log.i("sqlite_repo", "Finding product, ID: " + productId);
        Product result = null;

        // Create/open the database for writing.
        // Nonetheless, the connection is cached so it's not expensive to call multiple times.
        SQLiteDatabase db = getWritableDatabase();

        final Cursor cursor = db.query(
                PRODUCT_TABLE,
                new String[] {PRODUCT_ID_COLUMN, PRODUCT_NAME_COLUMN, PRODUCT_IS_BOUGHT_COLUMN},
                PRODUCT_ID_COLUMN + " = ?",
                new String[] {String.valueOf(productId)},
                null, null, null);

        try {
            if (cursor.moveToFirst()) {
                result = new Product(
                        Long.valueOf(cursor.getInt(0)),
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
                PRODUCT_TABLE,
                new String[]{PRODUCT_ID_COLUMN, PRODUCT_NAME_COLUMN, PRODUCT_IS_BOUGHT_COLUMN},
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

        // Create/open the database for writing.
        // Nonetheless, the connection is cached so it's not expensive to call multiple times.
        SQLiteDatabase db = getWritableDatabase();

        Long productId = -1L;

        db.beginTransaction();
        try {
            final ContentValues values = new ContentValues();
            values.put(PRODUCT_NAME_COLUMN,         product.getName());
            values.put(PRODUCT_IS_BOUGHT_COLUMN,    product.isBought() ? 1 : 0);

            int rows = db.update(PRODUCT_TABLE, values, PRODUCT_ID_COLUMN + "= ?",
                    new String[] {String.valueOf(product.getId())});

            // check if update succeed
            if (rows == 1) { // updated
                final Cursor cursor = db.query(
                        PRODUCT_TABLE,
                        new String[]{PRODUCT_ID_COLUMN, PRODUCT_NAME_COLUMN, PRODUCT_IS_BOUGHT_COLUMN},
                        null, null, null, null, null);

                try {
                    if (cursor.moveToFirst()) {
                        final Product p = toProduct(cursor);
                        productId = p.getId();
                    }

                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }

            } else { // needs insert
                productId = db.insertOrThrow(PRODUCT_TABLE, null, values);
                db.setTransactionSuccessful();

            }

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }


    @Override
    public void delete(final Long productId) {
        Log.i("sqlite_repo", "Deleting product, ID: " + productId);

        // Create/open the database for writing.
        // Nonetheless, the connection is cached so it's not expensive to call multiple times.
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(PRODUCT_TABLE, PRODUCT_ID_COLUMN + " = ?", new String[] {String.valueOf(productId)});
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }


    // Helpers

    private Product toProduct(final Cursor cursor) {
        final Product product = new Product(
                Long.valueOf(cursor.getInt(0)),
                cursor.getString(1),
                cursor.getInt(2) > 0 ? true : false);

        return product;
    }

}
