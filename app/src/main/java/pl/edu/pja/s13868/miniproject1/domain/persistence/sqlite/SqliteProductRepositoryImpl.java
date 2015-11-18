package pl.edu.pja.s13868.miniproject1.domain.persistence.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;

import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;
import pl.edu.pja.s13868.miniproject1.domain.model.product.ProductRepository;

/**
 * The Sqlite-based implementation of the {@code ProductRepository}.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class SqliteProductRepositoryImpl implements ProductRepository {

    private SqliteProductOpenHelper productOpenHelper;
    private SQLiteDatabase db;

    public SqliteProductRepositoryImpl(final Context context) {
        super();
        this.productOpenHelper = new SqliteProductOpenHelper(context);
        this.db = this.productOpenHelper.getWritableDatabase();
    }

    @Override
    public Product findById(final String productId) {
        this.db.beginTransaction();
        final Cursor cursor = this.db.query(
                SqliteProductOpenHelper.PRODUCT_TABLE_NAME,
                new String[] {"product_id", "product_name", "is_bought"},
                "product_id = ?",
                new String[] {productId},
                null,
                null,
                null
        );
//        cursor.is
//
//        return cursor.get
        throw new UnsupportedOperationException("Not implemented yet.");
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

}
