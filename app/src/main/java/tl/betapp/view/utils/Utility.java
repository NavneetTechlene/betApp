package tl.betapp.view.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import tl.betapp.R;

/**
 * Created by user on 08/12/2020.
 */
public class Utility {

    public void setPicassoImageRatio(final Context context, final String imagePath,
                          final ImageView view, int ratioImg, int defaultImg) {
        Picasso.get()
                .load(imagePath)
                .error(defaultImg)
                .placeholder(defaultImg)
                .resize(ratioImg, ratioImg).centerCrop()
                .noFade()
                .into(view);

    }

    public void setPicassoImage(final Context context, final String imagePath, final ImageView view, int defaultImg) {
        Picasso.get()
                .load(imagePath)
                .error(defaultImg)
                .placeholder(defaultImg)
                .noFade()
                .into(view);

    }

    public void setPicassoImageRounded(Activity mActivity, ImageView imgView, String imagePath, int defaultImage) {

        Picasso.get()
                .load(imagePath)
                .placeholder(defaultImage)
                .transform(new RoundedTransformation(mActivity, R.color.common_google_signin_btn_text_light))//rounded, rounded))
                .error(defaultImage)
                .resize(500, 500)
                .into(imgView);
    }

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int pxWidth = outMetrics.widthPixels;
        return pxWidth;
    }

    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int pxHeight = outMetrics.heightPixels;
        return pxHeight;
    }


    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static double twoDigitDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.0");
        String angleFormated = df.format(value);
        double changeValue = Double.parseDouble(angleFormated);
        LogPrint.getInstance().printP("twoDigitDecimal ", "changeValue: "
                + changeValue + " value:" + value);
        return changeValue;
    }

    public static int getSeekBarThumbPosX(SeekBar seekBar) {
        int posX;
        if (Build.VERSION.SDK_INT >= 16) {
            posX = seekBar.getThumb().getBounds().centerX();
        } else {
            int left = seekBar.getLeft() + seekBar.getPaddingLeft();
            int right = seekBar.getRight() - seekBar.getPaddingRight();
            float width = (float) (seekBar.getProgress() * (right - left)) / seekBar.getMax();
            posX = Math.round(width) + seekBar.getThumbOffset();
        }
        LogPrint.getInstance().printP("getSeekBarThumbPosX ", "posX: " + posX);
        return posX;
    }




}
