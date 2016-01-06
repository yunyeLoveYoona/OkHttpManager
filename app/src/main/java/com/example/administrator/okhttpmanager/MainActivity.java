package com.example.administrator.okhttpmanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yun.imageloader.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.yun.net.core.Request;
import org.yun.net.core.RequestQueue;
import org.yun.net.core.YunNet;
import org.yun.net.core.requests.JsonRequest;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = YunNet.createRequestQueue(this);
        textView = (TextView) findViewById(R.id.content);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", "18606530387"));
        nameValuePairs.add(new BasicNameValuePair("password", "123456"));
        JsonRequest<String> request = new JsonRequest<String>(0, "http://115.236.69.110:8457/bornclown/app/user/login.do"
                , nameValuePairs, null, 0, new Request.RequestListenter<String>() {
            @Override
            public void onComlete(String response, int tag) {
                textView.setText(response);
            }

            @Override
            public void onError(Exception error, int tag) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();

            }
        }, null);
        queue.addRequest(request);

        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image1),
                        "http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
                        R.drawable.ic_launcher);
        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image2),
                        "http://h.hiphotos.baidu.com/image/w%3D230/sign=fcf88e34a41ea8d38a227307a70b30cf/38dbb6fd5266d016c626a7a7952bd40735fa3505.jpg",
                        R.drawable.ic_launcher);
        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image3),
                        "http://b.hiphotos.baidu.com/image/w%3D230/sign=790b1813700e0cf3a0f749f83a46f23d/64380cd7912397dd481d2d055b82b2b7d0a2874f.jpg",
                        R.drawable.ic_launcher, true);
        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image4),
                        "http://a.hiphotos.baidu.com/image/w%3D230/sign=ea9e16090ed79123e0e093779d345917/b58f8c5494eef01f1a375ec4e2fe9925bc317d8b.jpg",
                        R.drawable.ic_launcher, true);
        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image5),
                        "http://h.hiphotos.baidu.com/image/w%3D230/sign=fedd4185d2a20cf44690f9dc46084b0c/9825bc315c6034a8f9807e83c913495409237656.jpg",
                        R.drawable.ic_launcher, true);
        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image6),
                        "http://d.hiphotos.baidu.com/image/w%3D230/sign=7ff1fb19369b033b2c88fbd925cf3620/203fb80e7bec54e79d0afc03bb389b504ec26acd.jpg",
                        R.drawable.ic_launcher, true);
        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image7),
                        "http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
                        R.drawable.ic_launcher, true);
        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image8),
                        "http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
                        R.drawable.ic_launcher);
        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image9),
                        "http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
                        R.drawable.ic_launcher);
        ImageLoader
                .getInstance(this)
                .load((ImageView) findViewById(R.id.image10),
                        "http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
                        R.drawable.ic_launcher);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
