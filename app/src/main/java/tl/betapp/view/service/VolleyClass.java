package tl.betapp.view.service;

import android.graphics.Bitmap;
import android.util.LruCache;


import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

import tl.betapp.MyApplication;


/**
 * Created by user on 18/04/2020.
 */
public class VolleyClass {

    private RequestQueue mRequestQueue;
    private static VolleyClass sInstance = null;
    // private static Context mCtx;
    private ImageLoader imageLoader;

    private VolleyClass() {

      //   mRequestQueue= Volley.newRequestQueue(Application.getAppContext());
        mRequestQueue = getRequestQueue();

        // for image
        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache()
        {
            private LruCache<String,Bitmap> cache =new LruCache<>((int) (Runtime.getRuntime().maxMemory()/1024/8));

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url,bitmap);
            }
        });
    }



    public static VolleyClass getsInstance() {
        if (sInstance == null) {
            sInstance = new VolleyClass();
        }
        return sInstance;
    }


   /* public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
*/

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(MyApplication.getAppContext().getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            mRequestQueue.start();
        }
        return mRequestQueue;
    }


    public  ImageLoader getImageLoader(){
        return imageLoader;
    }

}


