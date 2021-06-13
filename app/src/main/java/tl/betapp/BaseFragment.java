package tl.betapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import tl.betapp.view.model.SportModel;
import tl.betapp.view.service.HttpRequestUtility;
import tl.betapp.view.service.ServiceResponse;
import tl.betapp.view.service.WebUrls;
import tl.betapp.view.utils.ConfigData;
import tl.betapp.view.utils.DateUtility;
import tl.betapp.view.utils.LogPrint;
import tl.betapp.view.utils.SharedSaved;
import tl.betapp.view.utils.Utility;

import static android.text.TextUtils.isEmpty;

public class BaseFragment extends Fragment {

    private Activity mActivity;
    public LogPrint logConfig = LogPrint.getInstance();
    public ConfigData configData = ConfigData.getInstance();
    public SharedSaved sharedStorage;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
        this.sharedStorage = SharedSaved.getInstance(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public AdapterView.OnItemSelectedListener OnItemClickSpinner = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(13);
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void setSpinnerSize(Spinner spinner) {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            //float height = (float) mActivity.getResources().getDimension(R.dimen.item_detail_image_large);
            // Set popupWindow height to 500px
            popupWindow.setHeight((int) Utility.convertDpToPixel(150, mActivity));
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
            e.printStackTrace();
        }
    }

    /**
     * Create a chooser intent to select the source to get image from.<br/>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
     * All possible sources are added to the intent chooser.
     *//*
    public Intent getPickImageChooserIntent() {

        ImageFilePath mImageFilePath = new ImageFilePath(mActivity);
        // Determine Uri of camera image to save.
        Uri outputFileUri = mImageFilePath.getCaptureImageOutputUri(mActivity);
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = mActivity.getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        //you must setup two line below
        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);
        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }*/
    public void uploadSingleImage(String picture, String profileImage, final ServiceResponse serviceResponse) {

        LogPrint.getInstance().printP("uploadSingleImage ", "profileImage: " + profileImage);

        HashMap<String, String> parameter = new HashMap<>();
        HashMap<String, String> bodyParameter = new HashMap<>();
        if (profileImage != null && profileImage.length() != 0) {
            bodyParameter.put("action", "profile_pic");
        } else {
            bodyParameter.put("action", "profile_pic");
        }

        ArrayList<String> filePath = new ArrayList<>();
        if (profileImage != null && profileImage.length() != 0) {
            filePath.add(profileImage);
        } else {
            filePath.add(picture);
        }

        HttpRequestUtility request = new HttpRequestUtility(parameter, bodyParameter, filePath,
                "pic", WebUrls.UPLOAD_IMAGE_URL, HttpRequestUtility.MULTIPART,
                1001, mActivity, true,
                new HttpRequestUtility.HttpRequestResponseListener() {
                    @Override
                    public void callBackHttpRequest(String strResponse, int response_code) {
                        LogPrint.getInstance().printP("ImageUploading Response: ", strResponse);
                        try {
                            JSONObject resultData = new JSONObject(strResponse);
                            String status = resultData.getString("status");
                            if (status.equals("true")) {
                                JSONObject jObject = resultData.getJSONObject("data");
                                String picture = jObject.getString("picture");
                                serviceResponse.requestResponse(picture);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        request.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Drawable new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setImageDrawable(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    public static void current_date_callDatePickerDialogNew(Activity mActivity, final TextView txtDate) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                txtDate.setText(DateUtility.convertDateFormat(date, "dd-MM-yyyy", "yyyy-MM-dd"));///"dd-MM-yyyy", "dd-MMM-yyyy"
                //txtDate.setText(date);///"dd-MM-yyyy", "dd-MMM-yyyy"
            }
        }, year, month, day);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }

    public static String substringAfter(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return "";
        }
        final int pos = str.indexOf(separator);
        if (pos == 0) {
            return str;
        }
        return str.substring(pos + separator.length());
    }

    public ArrayList<SportModel> getList() {
        ArrayList<SportModel> list = new ArrayList<>();

        SportModel sportModel = new SportModel();
        sportModel.setSportName("NFL");
        sportModel.setId("0");
        sportModel.setImage(R.drawable.icon_sports_list_1);
        list.add(sportModel);

        sportModel = new SportModel();
        sportModel.setSportName("MLB");
        sportModel.setId("1");
        sportModel.setImage(R.drawable.icon_sports_list_3);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("NBA");
        sportModel.setId("2");
        sportModel.setImage(R.drawable.icon_sports_list_3);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("NHL");
        sportModel.setId("3");
        sportModel.setImage(R.drawable.icon_sports_list_5);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("NCAA Football");
        sportModel.setId("4");
        sportModel.setImage(R.drawable.icon_sports_list_6);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("NCAA Basketball");
        sportModel.setId("5");
        sportModel.setImage(R.drawable.icon_sports_list_7);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("Golf");
        sportModel.setId("6");
        sportModel.setImage(R.drawable.icon_sports_list_8);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("NASCAR");
        sportModel.setId("7");
        sportModel.setImage(R.drawable.icon_sports_list_9);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("Soccer");
        sportModel.setId("8");
        sportModel.setImage(R.drawable.icon_sports_list_10);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("CS:GO");
        sportModel.setId("9");
        sportModel.setImage(R.drawable.icon_sports_list_1);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("LoL");
        sportModel.setId("10");
        sportModel.setImage(R.drawable.icon_sports_list_3);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("WNBA");
        sportModel.setId("11");
        sportModel.setImage(R.drawable.icon_sports_list_5);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("MMA");
        sportModel.setId("12");
        sportModel.setImage(R.drawable.icon_sports_list_6);
        list.add(sportModel);
        sportModel = new SportModel();
        sportModel.setSportName("NCAA Women's Basketball");
        sportModel.setId("13");
        sportModel.setImage(R.drawable.icon_sports_list_7);
        list.add(sportModel);

        return list;
    }
}
