package tl.betapp;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.lang.reflect.Field;

import tl.betapp.R;
import tl.betapp.view.utils.ConfigData;
import tl.betapp.view.utils.LogPrint;
import tl.betapp.view.utils.SharedSaved;
import tl.betapp.view.utils.Utility;

public class BaseFragmentActivity extends FragmentActivity {

    public TextView actionBarTitle;
    public ImageView rightButton;
    public ImageView leftButton;
    public ProgressBar loadingPb;
    public RelativeLayout headerLayoutTop;

    public Context context;
    public LogPrint logConfig = LogPrint.getInstance();
    public ConfigData configData = ConfigData.getInstance();
    public SharedSaved mSharedStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        this.mSharedStorage = SharedSaved.getInstance(this);
    }

    public void setHeader(int leftButtonId, int rightButtonId, String title, View.OnClickListener
            leftButtonListener, View.OnClickListener rightButtonListener) {

       /* headerLayoutTop = (RelativeLayout) ((Activity) context).findViewById(R.id.headerLayoutTop);
        actionBarTitle = (TextView) ((Activity) context).findViewById(R.id.header_tv);
        rightButton = (ImageView) ((Activity) context).findViewById(R.id.right_button);
        leftButton = (ImageView) ((Activity) context).findViewById(R.id.back_button);
        loadingPb = (ProgressBar) ((Activity) context).findViewById(R.id.loading_h_pb);*/

        actionBarTitle.setText(title);
        leftButton.setImageResource(leftButtonId);
        leftButton.setOnClickListener(leftButtonListener);
        rightButton.setImageResource(rightButtonId);
        rightButton.setOnClickListener(rightButtonListener);

        rightButton.setVisibility(rightButtonId > 0 ? View.VISIBLE : View.GONE);
    }

    public void setHeaderTitle(String title) {
        actionBarTitle.setText(title);
    }

    public void hideHeaderLayout(int value) {
        if (value == 0) {
            headerLayoutTop.setVisibility(View.GONE);
        } else {
            headerLayoutTop.setVisibility(View.VISIBLE);
        }

    }

    public void hideRightButton() {
        rightButton.setVisibility(View.INVISIBLE);
    }

    public void showLoader(boolean show) {
        loadingPb.setVisibility(show ? View.VISIBLE : View.GONE);
        rightButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public ImageView getRightButton() {
        return rightButton;
    }



  /*  public void getFireBaseToken() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("NotificationKey ", "Key: " + key + " Value: " + value);
                logConfig.printP("NotificationKey ", "Key: " + key + " Value: " + value);
            }
        }

        try {

            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(
                    new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("NotificationKey", "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            SharedSaved sharedStorage = SharedSaved.getInstance(context);
                            sharedStorage.setDeviceToken(token);
                            // Log and toast
                            String msg = getString(R.string.msg_token_fmt, token);
                            logConfig.printP("NotificationKey ", " msg:" + msg);
                        }
                    });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
*/
    public AdapterView.OnItemSelectedListener OnItemClickSpinner = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(13);
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void setSpinnerSize(Spinner spinner){
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            //float height = (float) mActivity.getResources().getDimension(R.dimen.item_detail_image_large);
            // Set popupWindow height to 500px
            popupWindow.setHeight((int) Utility.convertDpToPixel(150, this));
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
    }


}
