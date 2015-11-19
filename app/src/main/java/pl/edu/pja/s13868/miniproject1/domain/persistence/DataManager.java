package pl.edu.pja.s13868.miniproject1.domain.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pja.s13868.miniproject1.SingletonRegistry;
import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;


public class DataManager {

    private SharedPreferences mSharedPreferences;
    private static final String KEY_MAIN_TUTORIAL = "KEY_MAIN_TUTORIAL";
    private static final boolean KEY_DEFAULT = false;

    private Handler mHandler;
    private ThreadTask mThreadTask;
    private List<Product> mProductList;

    public DataManager(Context pContext) {
        mHandler = new Handler();
        mSharedPreferences = pContext.getSharedPreferences(DataManager.class.getCanonicalName(), Context.MODE_PRIVATE);
        mThreadTask = new ThreadTask();
        mProductList = new ArrayList<>(SingletonRegistry.INSTANCE.productRepositorySingleton().listAllProducts());
    }

    public void saveMainTutorial() {
        mSharedPreferences.edit().putBoolean(KEY_MAIN_TUTORIAL, true).apply();
    }

    public boolean getMainTutorial() {
        return mSharedPreferences.getBoolean(KEY_MAIN_TUTORIAL, KEY_DEFAULT);
    }

    public void products(final DataHandler<Product> pDataHandler) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pDataHandler.onSuccess(mProductList);
                    }
                });
            }
        });
    }

    public void product(final String pProductId, final DataHandler pDataHandler) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                final Product product = SingletonRegistry.INSTANCE.productRepositorySingleton().findById(pProductId);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pDataHandler.onSuccess(product);
                    }
                });
            }
        });
    }

    public void deleteProduct(final String pProductId) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                SingletonRegistry.INSTANCE.productRepositorySingleton().delete(pProductId);
            }
        });
    }

    public void addProduct(final Product pProduct) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                mProductList.add(pProduct);
            }
        });
    }

    public void modifyProduct(final Product pProduct) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                Log.d("pp", "===> product: " + pProduct);
                SingletonRegistry.INSTANCE.productRepositorySingleton().store(pProduct);
            }
        });
    }
}
