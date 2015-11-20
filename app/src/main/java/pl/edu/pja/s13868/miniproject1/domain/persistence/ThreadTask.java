package pl.edu.pja.s13868.miniproject1.domain.persistence;

import android.os.Handler;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class ThreadTask {
    private ScheduledThreadPoolExecutor mExecutorService;
    private static ThreadTask sThreadTask;
    private Handler mHandler;

    private ThreadTask() {
        mExecutorService = new ScheduledThreadPoolExecutor(4);
        mHandler = new Handler();
    }

    public static synchronized ThreadTask getInstance() {
        if (sThreadTask == null) {
            sThreadTask = new ThreadTask();
        }
        return sThreadTask;
    }

    public void executeTask(final Runnable pTask) {
        mExecutorService.execute(pTask);
    }
}
