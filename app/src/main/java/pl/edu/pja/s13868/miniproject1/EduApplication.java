package pl.edu.pja.s13868.miniproject1;

import android.app.Application;
import android.content.Context;

import pl.edu.pja.s13868.miniproject1.domain.persistence.DataManager;

/**
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class EduApplication extends Application {
    private static Context sContext;
    private static DataManager sDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static DataManager getDataManager() {
        if (sDataManager == null) {
            synchronized (EduApplication.class) {
                if (sDataManager == null) {
                    sDataManager = new DataManager(getAppContext());
                }
            }
        }
        return sDataManager;
    }

    public static Context getAppContext() {
        return sContext;
    }
}
