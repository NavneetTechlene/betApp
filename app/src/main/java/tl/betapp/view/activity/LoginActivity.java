package tl.betapp.view.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tl.betapp.R;
import tl.betapp.databinding.ActivityLoginBinding;
import tl.betapp.BaseFragmentActivity;
import tl.betapp.view.service.NetworkApiCall;
import tl.betapp.view.service.ServiceResponse;
import tl.betapp.view.service.WebUrls;

public class LoginActivity extends BaseFragmentActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    public void loginApi(View view) {
        String email =binding.emailEV.getText().toString().trim();
        String password =binding.passwordEV.getText().toString().trim();

        if(binding.emailEV.getText().toString().trim().equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
        }
        else  if(binding.passwordEV.getText().toString().trim().equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Password .", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("email", "" + email);
                jsonObject.put("password", "" + password);

                logConfig.printP("UserCheck jsonObject: ", jsonObject.toString());

                NetworkApiCall mNetworkApiCall = new NetworkApiCall(this, jsonObject,
                        new ServiceResponse() {
                            @Override
                            public void requestResponse(String result) {
                                logConfig.printP("CheckUser response: ", result);
                                pareLoginResponse(result);
                            }
                        });
                mNetworkApiCall.callLink(WebUrls.HOST + WebUrls.login);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void pareLoginResponse(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            if (status.equals("true")) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                mSharedStorage.setLoginStatus(true);
                logConfig.printToast(this, "" + message);

            } else {
                logConfig.printP("User ", "" + message);
                logConfig.printToast(this, "" + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
    }
}