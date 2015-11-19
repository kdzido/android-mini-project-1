package pl.edu.pja.s13868.miniproject1.domain.persistence;

import android.os.Handler;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadTask {
    private ScheduledThreadPoolExecutor mExecutorService;
    private static ThreadTask sThreadTask;
    private Handler mHandler;

    public ThreadTask() {
        mExecutorService = new ScheduledThreadPoolExecutor(4);
        mHandler = new Handler();
    }

    public static ThreadTask instance() {
        if (sThreadTask == null) {
            sThreadTask = new ThreadTask();
        }
        return sThreadTask;
    }

    public void executeTask(final Runnable pTask) {
        mExecutorService.execute(pTask);
    }
}
