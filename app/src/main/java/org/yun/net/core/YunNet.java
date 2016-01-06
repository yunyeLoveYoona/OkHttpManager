package org.yun.net.core;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yunye on 15-6-15.
 */
public class YunNet {
    public static String cookie = "";

    public static RequestQueue createRequestQueue(Context context) {
        RequestQueue requestQueue = new RequestQueue(context);
        return requestQueue;
    }
}
