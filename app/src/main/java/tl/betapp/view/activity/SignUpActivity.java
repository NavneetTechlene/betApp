package tl.betapp.view.activity;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import tl.betapp.R;
import tl.betapp.databinding.ActivitySignUpBinding;
import tl.betapp.BaseFragmentActivity;
import tl.betapp.view.service.NetworkApiCall;
import tl.betapp.view.service.ServiceResponse;
import tl.betapp.view.service.WebUrls;
import tl.betapp.view.utils.DateUtility;


public class SignUpActivity extends BaseFragmentActivity {
    ActivitySignUpBinding binding;
    Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        initViews();
        binding.checkuserEV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    checkUser(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void checkUser(String name) {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("username", "" + name);

            logConfig.printP("UserCheck jsonObject: ", jsonObject.toString());

            NetworkApiCall mNetworkApiCall = new NetworkApiCall(SignUpActivity.this, jsonObject,
                    new ServiceResponse() {
                        @Override
                        public void requestResponse(String result) {
                            logConfig.printP("CheckUser response: ", result);
                            parseCheckuserResponse(result);
                        }
                    });
            mNetworkApiCall.callWithoutProgressDialogLink(WebUrls.HOST + WebUrls.check_username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseCheckuserResponse(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            if (status.equals("true")) {
                binding.checkuserEV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cancel, 0);
            } else {
                binding.checkuserEV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_right, 0);
                logConfig.printP("User ", "" + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dateofBirth(View view) {

        DateUtility.callAgeDatePicker(SignUpActivity.this,binding.dateofBirthEV);


    }

    private void initViews() {

    }



    public void submit(View view) {
        String name = binding.userNameEV.getText().toString().trim();
        String number = binding.contactNumberEV.getText().toString().trim();
        String username = binding.checkuserEV.getText().toString().trim();
        String email = binding.emailEV.getText().toString().trim();
        String password = binding.passwordEV.getText().toString().trim();
        String dateofbirth = binding.dateofBirthEV.getText().toString().trim();

        if(binding.userNameEV.getText().toString().trim().equalsIgnoreCase(""))
        {
          logConfig.printToast(this,"Please Add Name");
        }else if(binding.contactNumberEV.getText().toString().trim().equalsIgnoreCase(""))
        {           logConfig.printToast(this, "Please Add Contact Number");
        }else if(binding.checkuserEV.getText().toString().trim().equalsIgnoreCase("")){
            logConfig.printToast(this,"Please Add UserName");
        }else if(binding.emailEV.getText().toString().trim().equalsIgnoreCase("")){
            logConfig.printToast(this,"Please Add Email");
        }else if(binding.passwordEV.getText().toString().trim().equalsIgnoreCase(""))
        {            logConfig.printToast(this,"Please Add Password");
        }else if(binding.dateofBirthEV.getText().toString().trim().equalsIgnoreCase(""))
        {            logConfig.printToast(this,"Please Add DOB");
        } else {


            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("full_name", "" + name);
                jsonObject.put("mobile", "" + number);
                jsonObject.put("username", "" + username);
                jsonObject.put("email", "" + email);
                jsonObject.put("password", "" + password);
                jsonObject.put("dob", "" + dateofbirth);

                logConfig.printP("Registration  jsonObject: ", jsonObject.toString());

                NetworkApiCall mNetworkApiCall = new NetworkApiCall(this, jsonObject,
                        new ServiceResponse() {
                            @Override
                            public void requestResponse(String result) {
                                logConfig.printP("Register response: ", result);
                                pareRegisterResponse(result);
                            }
                        });
                mNetworkApiCall.callLink(WebUrls.HOST + WebUrls.register);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void pareRegisterResponse(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");

            if (status.equals("true")) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                mSharedStorage.setCust_name(jsonObject1.getString("name"));
                mSharedStorage.setCust_id(jsonObject1.getString("cus_id"));
                mSharedStorage.setCust_code(jsonObject1.getString("username"));

                logConfig.printToast(this, message);
                Intent intent = new Intent(SignUpActivity.this, OtpActivity.class);
                intent.putExtra("mobile", jsonObject1.getString("mobile"));
                intent.putExtra("cust_id", jsonObject1.getString("cus_id"));
                intent.putExtra("otp", jsonObject1.getString("otp"));
                startActivity(intent);
            } else {
                logConfig.printP("User ", "" + message);
                logConfig.printToast(this, message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back(View view) {
        finish();
    }
}