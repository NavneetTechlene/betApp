package tl.betapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

import tl.betapp.view.utils.ConfigData;
import tl.betapp.view.utils.LogPrint;
import tl.betapp.view.utils.SharedSaved;
import tl.betapp.view.utils.Utility;

public class BaseCompatActivity extends AppCompatActivity {

    public Context context;
    public ConfigData configData = ConfigData.getInstance();
    public LogPrint logPrint = LogPrint.getInstance();
    public SharedSaved sharedStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = BaseCompatActivity.this;
        this.sharedStorage = SharedSaved.getInstance(context);
    }

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
/*
    public Drawable buildCounterDrawable(int count, int backgroundImageId) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.cart_menu_layout, null);
        // view.setBackgroundResource(backgroundImageId);
        TextView textView = (TextView) view.findViewById(R.id.text_count);
        ImageView mImageView = (ImageView) view.findViewById(R.id.notification_button);
        mImageView.setImageResource(backgroundImageId);

        if (count == 0) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText("" + count);
        }

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return new BitmapDrawable(getResources(), bitmap);
    }*/

}