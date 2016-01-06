package org.yun.net.core;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 请求队列
 * Created by yunye on 15-6-12.
 */
public class RequestQueue implements Runnable {
    private Looper mainLooper;
    private ExecutorService executorService;
    private PriorityBlockingQueue<Runnable> queue;

    private RequestQueue() {

    }

    protected RequestQueue(Context context) {
        mainLooper = context.getMainLooper();
        queue = new PriorityBlockingQueue<Runnable>();
        executorService = Executors.newCachedThreadPool();
        executorService.execute(this);
    }

    public void addRequest(Request request) {
        request.setMainLooper(mainLooper);
        queue.add(request);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                queue.take().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        executorService.shutdown();
    }
}
