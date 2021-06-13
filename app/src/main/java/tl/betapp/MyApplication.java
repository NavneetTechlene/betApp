package tl.betapp;

import android.content.Context;


import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import tl.betapp.view.service.ConnectivityReceiver;


//import io.branch.referral.Branch;

public class MyApplication extends MultiDexApplication {

    private static MyApplication myApp;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        myApp =this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    public static synchronized MyApplication getInstance() {
        return myApp;
    }

    public static Context getAppContext() {
        return myApp.getApplicationContext();
    }
    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
