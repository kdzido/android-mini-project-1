package pl.edu.pja.s13868.miniproject1.domain.persistence.fake;

import android.util.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;
import pl.edu.pja.s13868.miniproject1.domain.model.product.ProductRepository;

/**
 * The fake implementation of the {@code ProductRepository}.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class FakeProductRepositoryImpl implements ProductRepository {

    private static final Product PRODUCT_01 = new Product(1L, "Coca-Cola", false);
    private static final Product PRODUCT_02 = new Product(2L, "Pizza", false);
    private static final Product PRODUCT_03 = new Product(3L, "Toilet paper", false);
    private static final Product PRODUCT_04 = new Product(4L, "iPhone", true);
    private static final Product PRODUCT_05 = new Product(5L, "product 05", false);
    private static final Product PRODUCT_06 = new Product(6L, "product 06", false);
    private static final Product PRODUCT_07 = new Product(7L, "product 07", false);
    private static final Product PRODUCT_08 = new Product(8L, "product 08", true);
    private static final Product PRODUCT_09 = new Product(9L, "product 09", true);
    private static final Product PRODUCT_10 = new Product(10L, "product 10", true);

    private static final Product PRODUCT_11 = new Product(11L, "Coca-Cola", false);
    private static final Product PRODUCT_12 = new Product(12L, "Pizza", false);
    private static final Product PRODUCT_13 = new Product(13L, "Toilet paper", false);
    private static final Product PRODUCT_14 = new Product(14L, "iPhone", true);
    private static final Product PRODUCT_15 = new Product(15L, "product 15", false);
    private static final Product PRODUCT_16 = new Product(16L, "product 16", false);
    private static final Product PRODUCT_17 = new Product(17L, "product 17", false);
    private static final Product PRODUCT_18 = new Product(18L, "product 18", true);
    private static final Product PRODUCT_19 = new Product(19L, "product 19", true);
    private static final Product PRODUCT_20 = new Product(20L, "product 20", true);

    private static final Map<Long, Product> STORE = new HashMap<>();
    static {
        STORE.put(PRODUCT_01.getId(), PRODUCT_01);
        STORE.put(PRODUCT_02.getId(), PRODUCT_02);
        STORE.put(PRODUCT_03.getId(), PRODUCT_03);
        STORE.put(PRODUCT_04.getId(), PRODUCT_04);
        STORE.put(PRODUCT_05.getId(), PRODUCT_05);
        STORE.put(PRODUCT_06.getId(), PRODUCT_06);
        STORE.put(PRODUCT_07.getId(), PRODUCT_07);
        STORE.put(PRODUCT_08.getId(), PRODUCT_08);
        STORE.put(PRODUCT_09.getId(), PRODUCT_09);
        STORE.put(PRODUCT_10.getId(), PRODUCT_10);
        STORE.put(PRODUCT_11.getId(), PRODUCT_11);
        STORE.put(PRODUCT_12.getId(), PRODUCT_12);
        STORE.put(PRODUCT_13.getId(), PRODUCT_13);
        STORE.put(PRODUCT_14.getId(), PRODUCT_14);
        STORE.put(PRODUCT_15.getId(), PRODUCT_15);
        STORE.put(PRODUCT_16.getId(), PRODUCT_16);
        STORE.put(PRODUCT_17.getId(), PRODUCT_17);
        STORE.put(PRODUCT_18.getId(), PRODUCT_18);
        STORE.put(PRODUCT_19.getId(), PRODUCT_19);
        STORE.put(PRODUCT_20.getId(), PRODUCT_20);
    }

    @Override
    public Product findById(final Long productId) {
        Log.i("repo", "Finding product, ID: " + productId);
        return STORE.get(productId);
    }

    @Override
    public Collection<Product> listAllProducts() {
        Log.i("repo", "Listing all products");
        return Collections.unmodifiableCollection(STORE.values());
    }

    @Override
    public void store(final Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        Log.i("repo", "Storing product: " + product);
        STORE.put(product.getId(), product);
    }

    @Override
    public void delete(final Long productId) {
        Log.i("repo", "Deleting product, ID: " + productId);

        STORE.remove(productId);
    }

}
