package tl.betapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import tl.betapp.view.activity.DashBoardActivity;
import tl.betapp.view.activity.LoginActivity;

public class SplashActivity extends BaseFragmentActivity {
    private static final long LOAD_TIME = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // call Version api
                //versionService();
                changeActivity();
            }
        }, LOAD_TIME);


    }

    private void changeActivity() {
       /* if (mSharedStorage.getLoginStatus()) {
            Intent mIntent = new Intent(this, Sif.class);
            startActivity(mIntent);
            finish();
        } else {
            Intent mIntent = new Intent(this, LoginActivity.class);
            startActivity(mIntent);
            finish();
        }*/

        Intent mIntent = new Intent(this, DashBoardActivity.class);
        startActivity(mIntent);
        finish();

    }
}