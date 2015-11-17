package pl.edu.pja.s13868.miniproject1.domain.model.product;

import java.util.Collection;

/**
 * Abstraction for persisting products on the shopping list.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public interface ProductRepository {

    /**
     * Finds the products by ID.
     *
     * @param productId the product ID
     * @return product with the given ID or null
     */
    Product findById(String productId);

    /**
     * Lists all products in the persistent store.
     *
     * @return the all products collection
     */
    Collection<Product> listAllProducts();

    /**
     * Persistes the given product.
     * Inserts a new product or updates an existing one.
     *
     * @param product the product to persist
     */
    void store(Product product);

    /**
     * Deletes the product with given ID.
     * If product does not exist does nothing.
     *
     * @param productId the product ID to delete
     */
    void delete(String productId);

}
