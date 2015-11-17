package pl.edu.pja.s13868.miniproject1.domain.persistence.sqlite;

import java.util.Collection;

import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;
import pl.edu.pja.s13868.miniproject1.domain.model.product.ProductRepository;

/**
 * The Sqlite-based implementation of the {@code ProductRepository}.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class SqliteProductRepositoryImpl implements ProductRepository {

    @Override
    public Product findById(final String productId) {
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
