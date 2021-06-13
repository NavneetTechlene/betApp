package tl.betapp.view.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedSaved {

    private static SharedPreferences sharedPreferences = null;
    private static SharedSaved instance;
    private static Context mContext;

    private SharedSaved(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedSaved getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new SharedSaved(context);
        }
        return instance;
    }

    public void setLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loginStatus", status);
        editor.apply();
    }

    public boolean getLoginStatus() {
        return sharedPreferences.getBoolean("loginStatus", false);
    }

    public void setUser_json(String user_json) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_json", user_json);
        editor.apply();
    }

    public String getUser_json() {
        return sharedPreferences.getString("user_json", "");
    }


    public void setCust_id(String user_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cust_id", user_id);
        editor.apply();
    }

    public String getCust_id() {
        return sharedPreferences.getString("cust_id", "");
    }

    public void setCust_code(String user_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cust_code", user_id);
        editor.apply();
    }

    public String getCust_code() {
        return sharedPreferences.getString("cust_code", "");
    }

    public void setCust_name(String user_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cust_name", user_id);
        editor.apply();
    }

    public String getCust_name() {
        return sharedPreferences.getString("cust_name", "");
    }

    public void setUser_id(String user_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", user_id);
        editor.apply();
    }

    public String getUser_id() {
        return sharedPreferences.getString("user_id", "");
    }

    public void setUser_otp(String user_otp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_otp", user_otp);
        editor.apply();
    }

    public String getUser_otp() {
        return sharedPreferences.getString("user_otp", "");
    }

    public void setUser_status(String user_status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_status", user_status);
        editor.apply();
    }

    public String getUser_status() {
        return sharedPreferences.getString("user_status", "P");
    }

    public void setUser_name(String user_name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", user_name);
        editor.apply();
    }

    public String getUser_name() {
        return sharedPreferences.getString("user_name", "");
    }

    public void setUser_image(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_image", value);
        editor.apply();
    }

    public String getUser_image() {
        return sharedPreferences.getString("user_image", "");
    }

    public void setUser_email(String user_email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", user_email);
        editor.apply();
    }

    public String getUser_email() {
        return sharedPreferences.getString("user_email", "");
    }

    public void setUser_mobile(String user_mobile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_mobile", user_mobile);
        editor.apply();
    }

    public String getUser_mobile() {
        return sharedPreferences.getString("user_mobile", "");
    }

    public void setUser_type(String user_type) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_type", user_type);
        editor.apply();
    }

    public String getUser_type() {
        return sharedPreferences.getString("user_type", "");
    }


    public void setDeviceToken(String reg_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("device_token", reg_id);
        editor.apply();
    }

    public String getDeviceToken() {
        return sharedPreferences.getString("device_token", "");
    }


    public void setEmp_code(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("emp_code", value);
        editor.apply();
    }

    public String getEmp_code() {
        return sharedPreferences.getString("emp_code", "");
    }

    public void setDealerWarehouseId(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dealer_warehouse_id", value);
        editor.apply();
    }

    public String getDealerWarehouseId() {
        return sharedPreferences.getString("dealer_warehouse_id", "");
    }

    public void setWarehouseCityId(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("warehouse_city_id", value);
        editor.apply();
    }

    public String getWarehouseCityId() {
        return sharedPreferences.getString("warehouse_city_id", "");
    }

    public void setApproveStatus(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("approveProfile", value);
        editor.apply();
    }

    public String getApproveStatus() {
        return sharedPreferences.getString("approveProfile", "");
    }
    public void setLicenseStatus(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("approve", value);
        editor.apply();
    }

    public String getLicenseStatus() {
        return sharedPreferences.getString("approve", "");
    }

    public void setWarehouseName(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("warehouseName", value);
        editor.apply();
    }

    public String getWarehouseName() {
        return sharedPreferences.getString("warehouseName", "");
    }

    public void setWarehouseAddress(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("warehouseAddress", value);
        editor.apply();
    }

    public String getWarehouseAddress() {
        return sharedPreferences.getString("warehouseAddress", "");
    }

    public void setWarehouseLatitude(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("warehouse_latitude", value);
        editor.apply();
    }

    public String getWarehouseLatitude() {
        return sharedPreferences.getString("warehouse_latitude", "0.0");
    }

    public void setWarehouseLongitude(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("warehouse_longitude", value);
        editor.apply();
    }

    public String getWarehouseLongitude() {
        return sharedPreferences.getString("warehouse_longitude", "0.0");
    }

    public int getCartCount() {
        return sharedPreferences.getInt("cart_count", 0);
    }

    public void setCartCount(int cart_count) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cart_count", cart_count);
        editor.apply();
    }

}
