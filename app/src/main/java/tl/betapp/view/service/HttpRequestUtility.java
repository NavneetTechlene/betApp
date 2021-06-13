package tl.betapp.view.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import tl.betapp.R;

/**
 * Created by user on 10/5/2019.
 */
public class HttpRequestUtility extends AsyncTask<String, Void, String> {

    public final static String TAG = HttpRequestUtility.class.getSimpleName().toString();
    private String mUrl;//, filePath;
    private ArrayList<String> filepathlist;
    private HashMap<String, String> parameters;
    private HashMap<String, String> bodyparameters = null;
    private String mSchemaParameters;
    private HttpRequestResponseListener mHttpRequestResponseListener;
    private int response_code, mRequestType;
    private ProgressDialog _ProgressDialog;
    private boolean mProgressBarVisible = true;
    private String mFileTag;
    private Context mContext;
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT = 3;
    public final static int DELETE = 4;
    public final static int MULTIPART = 5;


    public HttpRequestUtility(HashMap<String, String> parameters,
                              String schemaParameters, String url, int requestType,
                              int response_code, Context context,
                              HttpRequestResponseListener httpRequest) {
        this.mHttpRequestResponseListener = httpRequest;
        this.mRequestType = requestType;
        this.mUrl = url;
        this.response_code = response_code;
        this.parameters = parameters;
        this.mSchemaParameters = schemaParameters;
        this.mContext = context;
    }

    public HttpRequestUtility(HashMap<String, String> parameters,
                              String schemaParameters, String url, int requestType,
                              int response_code, Context context, boolean isProgressBarVisible,
                              HttpRequestResponseListener httpRequest) {
        this.mHttpRequestResponseListener = httpRequest;
        this.mRequestType = requestType;
        this.mUrl = url;
        this.response_code = response_code;
        this.parameters = parameters;
        this.mSchemaParameters = schemaParameters;
        this.mContext = context;
        this.mProgressBarVisible = isProgressBarVisible;
        Log.e("server url ", mUrl);
    }

    public HttpRequestUtility(HashMap<String, String> parameters,
                              HashMap<String, String> bodyparameters, ArrayList<String> filePath,
                              String fileTag, String url, int requestType,
                              int response_code, Context context, boolean isProgressBarVisible,
                              HttpRequestResponseListener httpRequest) {

        this.mHttpRequestResponseListener = httpRequest;
        this.mRequestType = requestType;
        this.mUrl = url;
        this.response_code = response_code;
        this.parameters = parameters;
        this.bodyparameters = bodyparameters;
        this.filepathlist = filePath;
        this.mContext = context;
        this.mProgressBarVisible = isProgressBarVisible;
        this.mFileTag = fileTag;
        Log.e("server url ", mUrl);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mProgressBarVisible) {
            _ProgressDialog = ProgressDialog.show(mContext, "",
                    mContext.getResources().getString(R.string.str_loading),
                    true);
        }

    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        try {
            if (mRequestType == POST) {
                // post method
                return executePost();
            } else if (mRequestType == GET) {
                // Get method
                return executeGet();
            } else if (mRequestType == PUT) {
                // Get method
                return executePut();
            } else if (mRequestType == DELETE) {
                // Get method
                return executeDelet();//executeMultipart
            } else if (mRequestType == MULTIPART) {
                // Get method
                return executeMultipart1();//executeMultipart
            }

        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        if ((_ProgressDialog != null) && _ProgressDialog.isShowing()) {
            _ProgressDialog.dismiss();
        }
        super.onPostExecute(result);
        Log.e("server response ", "" + result);
        try {
            mHttpRequestResponseListener.callBackHttpRequest(
                    result, response_code);
        }catch (Exception e){
            Log.e("server response --", "" + e.getMessage());
        }
    }

    private String executeGet() {
        InputStream inputStream = null;

        try {

            if (parameters != null) {
                int i = 0;
                for (String key : parameters.keySet()) {
                    if (i == 0) {
                        mUrl = mUrl + "?";
                    } else {
                        mUrl = mUrl + "&";
                    }
                    i++;
                    mUrl = mUrl + key + "=" + parameters.get(key);
                }

            }

            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(50000);
            connection.setConnectTimeout(150000 /* milliseconds */);
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            // for (String key : parameters.keySet()) {
            // connection.setRequestProperty(key, parameters.get(key));
            // }
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            Log.e("server getResponseCode ", "" + response);

            inputStream = connection.getInputStream();
            // Convert the InputStream into a string
            String contentAsString = readIt(inputStream);

            return contentAsString;
        } catch (Exception e) {

        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String executePost() {
        URL url;
        HttpURLConnection connection = null;
        try {
            // Create connection
            url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            for (String key : parameters.keySet()) {
                String value = parameters.get(key);

                connection.setRequestProperty(key, value);
            }
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if (bodyparameters != null) {
                for (String key : bodyparameters.keySet()) {
                    String value = bodyparameters.get(key);
                    byte[] outputInBytes = value.getBytes("UTF-8");// "UTF-8"
                    OutputStream os = connection.getOutputStream();
                    os.write(outputInBytes);
                }
            } else {
                Log.e(" mSchemaParameters ", mSchemaParameters);
                byte[] outputInBytes = mSchemaParameters.getBytes("UTF-8");// "UTF-8"
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
            }
            int response = connection.getResponseCode();

            Log.e("server getResponseCode ", "" + response);

            InputStream is = connection.getInputStream();
            String contentAsString = readIt(is);

            return contentAsString;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    public String executeMultipart() {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int serverResponseCode = 0;
        Bitmap mIcon11 = null;

        try {
            ////UPLOADING PICTURE AND DATA
            URL url = new URL(mUrl);
            // Open a HTTP  connection to  the URL
            Log.e("The caught  ", "get connection");
            conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                Log.e("The caught  ", "get openConnection");
            }
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs

            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(conn.getOutputStream());
            for (String key : bodyparameters.keySet()) {
                String test = bodyparameters.get(key).toString();//.getBytes("UTF-8")
                	dos.writeBytes(twoHyphens + boundary + lineEnd);//"full_name""  "full_name""
                dos.writeBytes("Content-Disposition: form-data; name=" + key + lineEnd + lineEnd
                        + test + lineEnd);
               Log.e(key,"  "+test);

            }
            //add  file on from data
            if(filepathlist != null){
                FileInputStream fileInputStream_new = null;
                if(filepathlist.size()<1){
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"image[]\";filename=\"" + "" +"\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                }
                for (String fpath:filepathlist) {
                    Log.e("image file atta-- ",fpath);
                    fpath= fpath.replace("file:/","");
                    try {
                        File sourceFile = new File(fpath);
                        fileInputStream_new = new FileInputStream(sourceFile);
                    } catch (Exception e) { }

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"image[]\";filename=\"" + fpath +"\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream_new.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    // read file and write it into form...
                    bytesRead = fileInputStream_new.read(buffer, 0, bufferSize);
                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream_new.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream_new.read(buffer, 0, bufferSize);
                    }
                    if (fileInputStream_new != null)
                        fileInputStream_new.close();
                }
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            //end one file on from data
             dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();

          //  String serverResponseMessage = conn.getResponseMessage();
            InputStream is = conn.getInputStream();
            String contentAsString = readIt(is);
            Log.e("uploadFile", "HTTP Response is : "
                     + ": " + serverResponseCode+"   --- "+contentAsString);

            //close the streams //

            dos.flush();
            dos.close();
            conn.disconnect();

            return contentAsString;

        } catch (MalformedURLException ex) {
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            String err = (e.getMessage() == null) ? "SD Card failed" : e.getMessage();
            Log.e("The caught  ", err);
        }
        return null;
    }

    public String executeMultipart1() {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int serverResponseCode = 0;
        Bitmap mIcon11 = null;

        try {
            ////UPLOADING PICTURE AND DATA
            URL url = new URL(mUrl);
            // Open a HTTP  connection to  the URL
            Log.e("The caught  ", "get connection");
            conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                Log.e("The caught  ", "get openConnection");
            }
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(conn.getOutputStream());
            for (String key : bodyparameters.keySet()) {
                String test = bodyparameters.get(key).toString();//.getBytes("UTF-8")
                dos.writeBytes(twoHyphens + boundary + lineEnd);//"full_name""  "full_name""
                dos.writeBytes("Content-Disposition: form-data; name=" + key + lineEnd + lineEnd
                        + test + lineEnd);
                Log.e(key,"  "+test);
            }
            //add  file on from data
            if(filepathlist!=null){
                FileInputStream fileInputStream_new = null;
                if(filepathlist.size()<1){
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + "" +"\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                }
                for (String fpath:filepathlist) {
                    Log.e("image file atta-- ",fpath);
                    fpath= fpath.replace("file:/","");
                    try {
                        File sourceFile = new File(fpath);
                        fileInputStream_new = new FileInputStream(sourceFile);
                    } catch (Exception e) {
                    }

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\""+mFileTag+"\";filename=\"" + fpath +"\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream_new.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    // read file and write it into form...
                    bytesRead = fileInputStream_new.read(buffer, 0, bufferSize);
                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream_new.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream_new.read(buffer, 0, bufferSize);
                    }
                    if (fileInputStream_new != null)
                        fileInputStream_new.close();
                }
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            //end one file on from data
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();

            //  String serverResponseMessage = conn.getResponseMessage();
            InputStream is = conn.getInputStream();
            String contentAsString = readIt(is);
            Log.e("uploadFile", "HTTP Response is : "
                    + ": " + serverResponseCode+"   --- "+contentAsString);
            //close the streams //
            dos.flush();
            dos.close();
            conn.disconnect();

            return contentAsString;

        } catch (MalformedURLException ex) {
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            String err = (e.getMessage() == null) ? "SD Card failed" : e.getMessage();
            Log.e("The caught  ", err);
        }
        return null;
    }


    public String executePut() {
        URL url;
        HttpURLConnection connection = null;
        try {
            // Create connection
            url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            for (String key : parameters.keySet()) {
                String value = parameters.get(key);

                connection.setRequestProperty(key, value);
            }
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            for (String key : bodyparameters.keySet()) {
                String value = bodyparameters.get(key);
                byte[] outputInBytes = value.getBytes("UTF-8");// "UTF-8"
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
            }
            InputStream is = connection.getInputStream();
            String contentAsString = readIt(is);

            return contentAsString;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    public String executeDelet() {
        URL url;
        HttpURLConnection connection = null;
        try {
            // Create connection
            url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            for (String key : parameters.keySet()) {
                String value = parameters.get(key);

                connection.setRequestProperty(key, value);
            }
            connection.setUseCaches(false);
            connection.setDoInput(true);
            InputStream is = connection.getInputStream();
            String contentAsString = readIt(is);

            return contentAsString;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    public String readIt(InputStream stream) throws IOException,
            UnsupportedEncodingException {
        InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public interface HttpRequestResponseListener {
        public void callBackHttpRequest(String strResponse, int response_code);
    }

}
