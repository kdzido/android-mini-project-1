package pl.edu.pja.s13868.miniproject1.domain.persistence.contentprovider;

import java.util.Collection;

import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;
import pl.edu.pja.s13868.miniproject1.domain.model.product.ProductRepository;

/**
 * The content-provider-based implementation of the {@code ProductRepository}.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class ContentProviderProductRepositoryImpl implements ProductRepository {

    @Override
    public Product findById(final Long productId) {
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
    public void delete(Long productId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
