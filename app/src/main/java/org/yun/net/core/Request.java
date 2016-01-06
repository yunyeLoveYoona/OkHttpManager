package org.yun.net.core;

import android.os.Handler;
import android.os.Looper;

import org.apache.http.NameValuePair;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * 用来执行网络请求，并传递请求结果
 * 可以设置请求执行的优先级,并且可以给请求设置tag
 * 当你在一个activity或者其他情况下，需要一次发出多个请求并对所有的请求进行监听的话
 * 有了tag就可以在一个监听回调中完成对所有请求的监听，提供代码的简洁度
 * Created by yunye on 15-6-12.
 */
public abstract class Request implements Runnable, Comparable<Request> {
    private final int priority;
    protected String url;
    protected List<NameValuePair> nameValuePairList;
    protected int tag;
    protected Handler mainLooperHandler;
    protected RequestListenter requestListenter;
    protected List<File> files;

    /**
     * 无参数时默认为Get方式请求
     *
     * @param priority
     * @param url
     * @param tag
     * @param requestListenter
     */
    public Request(int priority, String url, int tag, RequestListenter requestListenter) {
        this.priority = priority;
        this.url = url;
        this.tag = tag;
        this.requestListenter = requestListenter;
    }

    /**
     * 有参数时默认为Post方式请求
     *
     * @param priority
     * @param url
     * @param nameValuePairList 可为null
     * @param tag
     * @param requestListenter
     * @param files             文件可传null
     */
    public Request(int priority, String url, List<NameValuePair> nameValuePairList, List<File> files
            , int tag, RequestListenter requestListenter) {
        this.priority = priority;
        this.url = url;
        this.nameValuePairList = nameValuePairList;
        this.tag = tag;
        this.requestListenter = requestListenter;
        this.files = files;
    }

    @Override
    public void run() {
        startRequest();
    }

    protected abstract void startRequest();

    protected abstract void forResult(String resultStr) throws Exception;


    protected OkHttpClient getYunHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
        return okHttpClient;
    }

    @Override
    public int compareTo(Request request) {
        if (priority > request.priority) {
            return 1;
        } else if (priority < request.priority) {
            return -1;
        }
        return 0;
    }

    public interface RequestListenter<T> {
        public void onComlete(T response, int tag);

        public void onError(Exception error, int tag);
    }

    protected void setMainLooper(Looper mainLooper) {
        mainLooperHandler = new Handler(mainLooper);
    }
}
