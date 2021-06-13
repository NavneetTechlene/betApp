package tl.betapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import tl.betapp.BaseFragmentActivity;
import tl.betapp.R;
import tl.betapp.databinding.ActivityOtpBinding;

import tl.betapp.view.service.NetworkApiCall;
import tl.betapp.view.service.ServiceResponse;
import tl.betapp.view.service.WebUrls;

public class OtpActivity extends BaseFragmentActivity {

    private String verificationId;

    ActivityOtpBinding binding;
    private FirebaseAuth mAuth;
    private String mobile;
    private String cust_id;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);

        mobile = getIntent().getStringExtra("mobile");
        cust_id = getIntent().getStringExtra("cust_id");
        otp = getIntent().getStringExtra("otp");
        Random r = new Random();
        int lowRange = 0;
        int highRange = 99;
        int result = r.nextInt(highRange -lowRange ) + lowRange ;
        String random=String.valueOf(result);
        char o1=otp.charAt(0);
        char o2=otp.charAt(1);
        char o3=otp.charAt(2);
        char o4=otp.charAt(3);
        char o5=random.charAt(0);
        char o6=random.charAt(1);


        binding.otp1.setText(""+o1);
        binding.otp2.setText(""+o2);
        binding.otp3.setText(""+o3);
        binding.otp4.setText(""+o4);
        binding.otp5.setText(""+o5);
        binding.otp6.setText(""+o6);

    }

    private void initViews() {

        String otp1 = binding.otp1.getText().toString().trim();
        String otp2 = binding.otp2.getText().toString().trim();
        String otp3 = binding.otp3.getText().toString().trim();
        String otp4 = binding.otp4.getText().toString().trim();
        String otp5 = binding.otp5.getText().toString().trim();
        String otp6 = binding.otp6.getText().toString().trim();
        String otp = otp1 + otp2 + otp3 + otp4;
        callotpApi(cust_id, otp);
    }

    private void callotpApi(String cust_id, String otp) {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("customer_id", "" + cust_id);
            jsonObject.put("otp", "" + otp);

            logConfig.printP("UserCheck jsonObject: ", jsonObject.toString());

            NetworkApiCall mNetworkApiCall = new NetworkApiCall(this, jsonObject,
                    new ServiceResponse() {
                        @Override
                        public void requestResponse(String result) {
                            logConfig.printP("CheckUser response: ", result);
                            pareseotpResponse(result);
                        }
                    });
            mNetworkApiCall.callLink(WebUrls.HOST + WebUrls.otp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void pareseotpResponse(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            if (status.equals("true")) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                mSharedStorage.setLoginStatus(true);
                startActivity(new Intent(OtpActivity.this,LoginActivity.class));

            } else {
                logConfig.printP("User ", "" + message);
                logConfig.printToast(this, "" + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendVerificationCode(String number) {
//        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

//        progressBar.setVisibility(View.GONE);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                //   editText.setText(code);
                // verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            //progressBar.setVisibility(View.GONE);
        }
    };

    public void otpCheck(View view) {
        initViews();

    }
}