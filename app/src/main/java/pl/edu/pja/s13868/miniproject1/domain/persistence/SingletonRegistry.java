package pl.edu.pja.s13868.miniproject1.domain.persistence;

import pl.edu.pja.s13868.miniproject1.domain.model.product.ProductRepository;
import pl.edu.pja.s13868.miniproject1.domain.persistence.fake.FakeProductRepositoryImpl;

/**
 * A registry where application-wide singletons can be retrieved.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public enum SingletonRegistry {

    INSTANCE;

    private ProductRepository productRepository;

    /**
     * @return the product repository singleton
     */
    public ProductRepository productRepositorySingleton() {
        synchronized (ProductRepository.class) {
            if (productRepository == null) {
                // TODO use Sqllite or content-provider-based implementation eventually
                // TODO use Sqllite or content-provider-based implementation eventually
                // TODO use Sqllite or content-provider-based implementation eventually
                productRepository = new FakeProductRepositoryImpl();
            }
        }
        return productRepository;
    }


}
