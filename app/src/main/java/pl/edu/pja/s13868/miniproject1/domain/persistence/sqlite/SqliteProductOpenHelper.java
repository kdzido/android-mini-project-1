package pl.edu.pja.s13868.miniproject1.domain.persistence.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The product repository Sqlite initializer.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class SqliteProductOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mini-project1-db";
    public static final int DATABASE_VERSION = 1;

    public static final String PRODUCT_TABLE_NAME = "product";

    // TODO fix sql
    public static final String PRODUCT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS" + PRODUCT_TABLE_NAME + " (" +
            "product_id TEXT PRIMARY KEY, " +
            "product_name TEXT, " +
            "is_bought INTEGER, " +
            "version INTEGER);";

    public SqliteProductOpenHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(PRODUCT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("db", "db upgrade " + oldVersion + " -> " + newVersion + ": doing nothing");
    }

}
