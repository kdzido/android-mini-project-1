package pl.edu.pja.s13868.miniproject1.domain.persistence.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class DbSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_BOUGHT = "bought";

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PRODUCTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_PRODUCT_NAME + " text not null, "
            + COLUMN_BOUGHT + " integer);";

    public DbSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

}
