package tl.betapp.view.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tl.betapp.R;

/**
 * Created by Jitendra on 15/12/2020.
 */
public class LogPrint {
    private static LogPrint instance;

    private LogPrint() {

    }

    public static LogPrint getInstance() {
        if (instance == null) {
            instance = new LogPrint();
        }
        return instance;
    }


    public void printP(String title, String msg){
        System.out.println(title+msg);
    }

    public void printD(String title, String value) {
        Log.d(title, value);
    }

    public void printE(String title, String value) {
        Log.e(title, value);
    }

    public void printV(String title, String value) {
        Log.v(title, value);
    }

    public void printToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void printToastCustom(Context context, String message) {

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View toastView = toast.getView();
        // This'll return the default View of the Toast.
        /* And now you can get the TextView of the default View of the Toast. */
        TextView toastMessage = toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(18);
        toastMessage.setPadding(20,0,20,0);
        toastMessage.setTextColor(Color.parseColor("#000000"));
        toastMessage.setGravity(Gravity.CENTER);
        //toastMessage.setCompoundDrawablePadding(16);
        // toastView.setBackgroundColor(Color.parseColor("#13CEC9"));
        toastView.setBackgroundResource(R.drawable.theme_input_bg);
        toast.show();

    }

    public void hideKeyBoard(Activity activity) {

        try {
            // Check if no view has focus:
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showKeyboard(EditText editText, Activity activity) {
        editText.requestFocus();
        editText.getShowSoftInputOnFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
