package org.yun.net.core.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.yun.net.core.Request;

import java.io.File;
import java.net.URLConnection;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by yune on 15-6-12.
 */
public class JsonRequest<T> extends Request {
    private TypeToken<T> typeToken;

    public JsonRequest(int priority, String url, int tag,
                       RequestListenter<T> requestListenter, TypeToken<T> typeToken) {
        super(priority, url, tag, requestListenter);
        this.typeToken = typeToken;
    }

    public JsonRequest(int priority, String url,
                       List<NameValuePair> nameValuePairList, List<File> files, int tag,
                       RequestListenter<T> requestListenter, TypeToken<T> typeToken) {
        super(priority, url, nameValuePairList, files, tag, requestListenter);
        this.typeToken = typeToken;
    }

    @Override
    protected void startRequest() {
        OkHttpClient okHttpClient = getYunHttp();
        try {
            String resultstr;
            MultipartBody.Builder builder = new MultipartBody.Builder();
            if (nameValuePairList != null && nameValuePairList.size() > 0) {
                for (NameValuePair nameValuePair : nameValuePairList) {
                    builder.addFormDataPart(nameValuePair.getName(), nameValuePair.getValue());
                }
            }
            if (files != null && files.size() > 0) {
                for (File file : files) {
                    String mediaType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());
                    if (mediaType == null) {
                        mediaType = "application/octet-stream";
                    }
                    builder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse(mediaType), file));
                }
            }
            RequestBody requestBody = builder.build();
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            resultstr = call.execute().body().string();
            forResult(resultstr);
        } catch (final Exception e) {
            if (requestListenter != null) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestListenter.onError(e, tag);
                    }
                });
            }
        }
    }

    @Override
    protected void forResult(final String resultStr) throws Exception {
        Gson gson = new Gson();
        if (typeToken == null) {
            if (requestListenter != null) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestListenter.onComlete(resultStr, tag);
                    }
                });
            }
        } else {
            final T result = gson.fromJson(resultStr, typeToken.getType());
            if (requestListenter != null) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestListenter.onComlete(result, tag);
                    }
                });
            }
        }

    }
}
