package pl.edu.pja.s13868.miniproject1;

import android.app.Application;
import android.content.Context;

import pl.edu.pja.s13868.miniproject1.domain.model.product.ProductRepository;
import pl.edu.pja.s13868.miniproject1.domain.persistence.DataManager;
import pl.edu.pja.s13868.miniproject1.domain.persistence.fake.FakeProductRepositoryImpl;
import pl.edu.pja.s13868.miniproject1.domain.persistence.sqlite.SqliteProductRepositoryImpl;

/**
 * Responsibilities:
 * <ul>
 *     <li>entry point class</li>
 *     <li>application-wide singleton registry</li>
 * </ul>
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class EduApplication extends Application {

    private static Context sContext;

    private static ProductRepository sProductRepository;
    private static DataManager sDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    /**
     * @return the data manager singleton
     */
    public static synchronized DataManager dataManagerSingleton() {
        if (sDataManager == null) {
            synchronized (EduApplication.class) {
                if (sDataManager == null) {
                    sDataManager = new DataManager(getAppContext());
                }
            }
        }
        return sDataManager;
    }

    /**
     * @return the product repository singleton
     */
    public static synchronized ProductRepository productRepositorySingleton() {
        synchronized (ProductRepository.class) {
            if (sProductRepository == null) {
                // TODO use Sqllite or content-provider-based implementation eventually
                // TODO use Sqllite or content-provider-based implementation eventually
                // TODO use Sqllite or content-provider-based implementation eventually
//                sProductRepository = new FakeProductRepositoryImpl();
                sProductRepository = SqliteProductRepositoryImpl.getInstance(getAppContext());
            }
        }
        return sProductRepository;
    }

    public static Context getAppContext() {
        return sContext;
    }
}
