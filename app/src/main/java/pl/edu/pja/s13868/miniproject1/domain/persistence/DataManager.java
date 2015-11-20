package pl.edu.pja.s13868.miniproject1.domain.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pja.s13868.miniproject1.domain.model.product.Product;

/**
 * Manages persistent data
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class DataManager {

    private static final String KEY_FONT_SIZE = "KEY_FONT_SIZE";
    private static final int KEY_FONT_SIZE_DEFAULT = 14;
    private static final String KEY_FONT_COLOR = "KEY_FONT_COLOR";
    private static final String KEY_FONT_COLOR_DEFAULT = "#000000";
    private static final boolean KEY_DEFAULT = false;

    private SharedPreferences mSharedPreferences;

    private Handler mHandler;
    private ThreadTask mThreadTask;

    // TODO why duplicated with repo??
    // TODO why duplicated with repo??
    // TODO why duplicated with repo??
    private List<Product> mProductList;


    public DataManager(final Context pContext) {
        mHandler = new Handler();
        mSharedPreferences = pContext.getSharedPreferences(DataManager.class.getCanonicalName(), Context.MODE_PRIVATE);
        mThreadTask = ThreadTask.getInstance();
        mProductList = new ArrayList<>(SingletonRegistry.INSTANCE.productRepositorySingleton().listAllProducts());
    }

    public void saveFontSize(int pFontSize) {
        mSharedPreferences.edit().putInt(KEY_FONT_SIZE, pFontSize).apply();
    }

    public int getFontSize() {
        return mSharedPreferences.getInt(KEY_FONT_SIZE, KEY_FONT_SIZE_DEFAULT);
    }

    public void saveFontColor(String pFontColor) {
        mSharedPreferences.edit().putString(KEY_FONT_COLOR, pFontColor).apply();
    }

    public String getFontColor() {
        return mSharedPreferences.getString(KEY_FONT_COLOR, KEY_FONT_COLOR_DEFAULT);
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

    /**
     * Deletes product from persistent store.
     *
     * @param pProductId the product ID
     */
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

                SingletonRegistry.INSTANCE.productRepositorySingleton().store(pProduct);
            }
        });
    }

    public void modifyProduct(final Product pProduct) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                SingletonRegistry.INSTANCE.productRepositorySingleton().store(pProduct);
            }
        });
    }
}
