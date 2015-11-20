package pl.edu.pja.s13868.miniproject1.domain.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import java.util.ArrayList;

import pl.edu.pja.s13868.miniproject1.EduApplication;
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


    public DataManager(final Context pContext) {
        mHandler = new Handler();
        mSharedPreferences = pContext.getSharedPreferences(DataManager.class.getCanonicalName(), Context.MODE_PRIVATE);
        mThreadTask = ThreadTask.getInstance();
    }

    /**
     * Saves the font size shared preference.
     *
     * @param pFontSize the font size
     */
    public void saveFontSize(int pFontSize) {
        mSharedPreferences.edit().putInt(KEY_FONT_SIZE, pFontSize).apply();
    }

    /**
     * @return the font size preference
     */
    public int getFontSize() {
        return mSharedPreferences.getInt(KEY_FONT_SIZE, KEY_FONT_SIZE_DEFAULT);
    }

    /**
     * Saves the font color shared preference.
     *
     * @param pFontColor the font color (the #rrggbb notation)
     */
    public void saveFontColor(String pFontColor) {
        mSharedPreferences.edit().putString(KEY_FONT_COLOR, pFontColor).apply();
    }

    /**
     * @return the font color preference
     */
    public String getFontColor() {
        return mSharedPreferences.getString(KEY_FONT_COLOR, KEY_FONT_COLOR_DEFAULT);
    }

    /**
     * Performs the given operation on the all products in a separate thread.
     *
     * @param pDataHandler the operation to performs
     */
    public void products(final DataHandler<Product> pDataHandler) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pDataHandler.onSuccess(
                                new ArrayList<>(EduApplication.productRepositorySingleton().listAllProducts()));
                    }
                });
            }
        });
    }

    /**
     * Performs the given operation on the product in a separate thread.
     *
     * @param pProductId the product ID
     * @param pDataHandler the operation to perform
     */
    public void product(final Long pProductId, final DataHandler pDataHandler) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                final Product product = EduApplication.productRepositorySingleton().findById(pProductId);
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
     * Performs product deletion operation in a separate thread.
     *
     * @param pProductId the product ID
     */
    public void deleteProduct(final Long pProductId) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                EduApplication.productRepositorySingleton().delete(pProductId);
            }
        });
    }

    /**
     * Performs product insert or update operation in a separate thread.
     *
     * @param pProduct the product to persist
     */
    public void storeProduct(final Product pProduct) {
        mThreadTask.executeTask(new Runnable() {
            @Override
            public void run() {
                EduApplication.productRepositorySingleton().store(pProduct);
            }
        });
    }

}
