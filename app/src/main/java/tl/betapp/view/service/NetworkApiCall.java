package tl.betapp.view.service;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import tl.betapp.R;
import tl.betapp.view.utils.ConfigData;
import tl.betapp.view.utils.LogPrint;

/**
 * Created by user on 18/04/2020.
 */

public class NetworkApiCall {

    private String TAG = "NetworkApiCall: ";
    private Activity mActivity = null;
    private Context context;
    private String responseString = "";
    private ProgressDialog pDialog = null;
    private JSONObject mJSONObject;
    private ServiceResponse mServiceResponse;
    private int MY_SOCKET_TIMEOUT_MS = 30000;
    private ConfigData mConfigData = ConfigData.getInstance();
    private LogPrint logConfig = LogPrint.getInstance();
    private String network_error;
    private RequestQueue mRequestQueue;
    public static Dialog customDialog;


    public NetworkApiCall(Activity activity, JSONObject jsonObject, ServiceResponse serviceResponse) {
        this.mActivity = activity;
        this.context = activity;
        this.mJSONObject = jsonObject;
        this.mServiceResponse = serviceResponse;
        this.network_error = context.getString(R.string.str_no_network);
        this.mRequestQueue = VolleyClass.getsInstance().getRequestQueue();
    }

    public NetworkApiCall(Context context, JSONObject jsonObject, ServiceResponse serviceResponce) {
        this.context = context;
        this.mJSONObject = jsonObject;
        this.mServiceResponse = serviceResponce;
        this.network_error = context.getResources().getString(R.string.str_no_network);
        this.mRequestQueue = VolleyClass.getsInstance().getRequestQueue();
    }

    public void call() {

        logConfig.printP(TAG, WebUrls.HOST + mJSONObject.toString());
        if (mActivity != null) {
         /*   SpannableString spandle = new SpannableString("Loading...");
            spandle.setSpan(new ForegroundColorSpan(Color.GRAY), 0, spandle.length(), 0);*/
          showProgressDialog(mActivity);
        }
        if (isConnectingToInternet()) {
            responseString = "";
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, WebUrls.HOST, mJSONObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            logConfig.printP(TAG, "JSONObject: " + response.toString());
                            //To dismiss progress dialog.
                            hideProgressDialog();
                            responseString = response.toString();
                            onPostExecute(responseString);

                        }//if
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideProgressDialog();
                            logConfig.printP(TAG, "VolleyError: " + error.getMessage());
                            responseString = "Error";
                            onPostExecute(responseString);
                        }
                    });
            jor.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jor);
        } else {
            onPostExecute(network_error);
        }
    }

    public void callGetUrl(String url) {

        logConfig.printP(TAG, url);
        if (mActivity != null) {
            SpannableString spandle = new SpannableString("Loading...");
            spandle.setSpan(new ForegroundColorSpan(Color.GRAY), 0, spandle.length(), 0);
           showProgressDialog(mActivity);
        }
        if (isConnectingToInternet()) {
            responseString = "";
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            logConfig.printP(TAG, "JSONObject: " + response.toString());
                            //To dismiss progress dialog.
                            hideProgressDialog();
                            responseString = response.toString();
                            onPostExecute(responseString);
                        }//if
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideProgressDialog();
                            logConfig.printP(TAG, "VolleyError: " + error.getMessage());
                            responseString = "Error";
                            onPostExecute(responseString);
                        }
                    });
            jor.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jor);
        } else {
            hideProgressDialog();
            onPostExecute(network_error);
        }
    }
    public void callLink(String url) {

        logConfig.printP(TAG, url+" "+mJSONObject.toString());
        if (mActivity != null) {
            SpannableString spandle = new SpannableString("Loading...");
            spandle.setSpan(new ForegroundColorSpan(Color.GRAY), 0, spandle.length(), 0);
           showProgressDialog(mActivity);
        }
        if (isConnectingToInternet()) {
            responseString = "";
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, mJSONObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            logConfig.printP(TAG, "JSONObject: " + response.toString());
                            //To dismiss progress dialog.
                            hideProgressDialog();
                            responseString = response.toString();
                            onPostExecute(responseString);
                        }//if
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            logConfig.printP(TAG, "VolleyError: " + error.getMessage());
                            responseString = "Error";
                            hideProgressDialog();
                            onPostExecute(responseString);
                        }
                    });
            jor.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jor);
        } else {
            hideProgressDialog();
            onPostExecute(network_error);
        }
    }

    public void callWithoutProgressDialogLink(String url) {

        logConfig.printP(TAG, url+" "+mJSONObject.toString());
        if (isConnectingToInternet()) {
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, mJSONObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            logConfig.printP(TAG, "response: " + response.toString());
                            //To dismiss progress dialog.
                            responseString = response.toString();
                            onPostExecute(responseString);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseString = "Error";
                            onPostExecute(responseString + " :  " + error);
                        }
                    });
            jor.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jor);
        } else {
            onPostExecute(network_error);
        }
    }

    public void callWithoutProgressDialog() {

        logConfig.printP(TAG, WebUrls.HOST + mJSONObject.toString());
        if (isConnectingToInternet()) {
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, WebUrls.HOST, mJSONObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            logConfig.printP(TAG, "response: " + response.toString());
                            //To dismiss progress dialog.
                            responseString = response.toString();
                            onPostExecute(responseString);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseString = "Error";
                            onPostExecute(responseString + " :  " + error);
                        }
                    });
            jor.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
           mRequestQueue.add(jor);
        } else {
            onPostExecute(network_error);
        }
    }

    public void onPostExecute(String result) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();

            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        if (!result.equals("") && !result.equals("Error") && !result.equals(network_error)) {
            mServiceResponse.requestResponse(result);
        } else {
            if (mActivity != null) {
                if (result.equals("") || result.equals("Error")) {
                    logConfig.printToast(mActivity,context.getResources().getString(R.string.some_thing_went_wrong_msg));
                  //  mConfigData.displayAlert(mActivity, context.getResources().getString(R.string.some_thing_went_wrong_msg), false);
                } else {
                  //  mConfigData.displayAlert(mActivity, network_error, false);
                }
            } else {
                logConfig.printToast(context, network_error);
            }
        }
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void showProgressDialog(Activity activity) {
        if (customDialog != null) {
            customDialog.dismiss();
            customDialog = null;
        }
        customDialog = new Dialog(activity, R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View mView = inflater.inflate(R.layout.progress_dialog, null);
        customDialog.setCancelable(false);
        customDialog.setContentView(mView);
        if (!customDialog.isShowing() && !activity.isFinishing()) {
            customDialog.show();
        }
    }

    public static void hideProgressDialog() {
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }
}
