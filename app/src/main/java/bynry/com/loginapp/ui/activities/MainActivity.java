package bynry.com.loginapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;

import bynry.com.loginapp.R;
import bynry.com.loginapp.interfaces.ApiServiceCaller;
import bynry.com.loginapp.utility.App;
import bynry.com.loginapp.utility.CommonUtility;
import bynry.com.loginapp.webservices.ApiConstants;
import bynry.com.loginapp.webservices.JsonResponse;
import bynry.com.loginapp.webservices.WebRequest;

public class MainActivity extends ParentActivity implements View.OnClickListener, ApiServiceCaller {

    private Context mContext;

    private EditText edtId, edtPassword;
    private Button btnLogin, btnSignOut;
    private String userId, userPass;
    private TextView txtId;
    private LinearLayout linearInput, linearView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        txtId = findViewById(R.id.txt_id);

        edtId = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);

        btnLogin = findViewById(R.id.btn_login);
        btnSignOut = findViewById(R.id.btn_log_out);

        linearInput = findViewById(R.id.linear_input);
        linearView = findViewById(R.id.linear_view);

        btnLogin.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);

        Drawable drawable = edtId.getBackground(); // get current EditText drawable
        drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP); // change the drawable color

        edtId.setBackground(drawable); // set the new drawable to EditText
        edtPassword.setBackground(drawable); // set the new drawable to EditText

    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin){
            isValidate();
        } else if (v == btnSignOut){
            btnSignOut.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            linearInput.setVisibility(View.VISIBLE);
            edtId.setText("");
            edtPassword.setText("");
        }
    }

    public void isValidate() {
        userId = edtId.getText().toString().trim();
        userPass = edtPassword.getText().toString();/*
        if (!TextUtils.isEmpty(userId)) {
            if (!TextUtils.isEmpty(userPass)) {*/
                doLogin();

            /*} else
                edtPassword.setError("Please enter Password");
        } else {
            edtId.setError("Please enter ID");
        }*/
    }


    public void doLogin() {

        if (CommonUtility.getInstance(this).checkConnectivity(mContext)) {
            showLoadingDialog();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", userId);
                jsonObject.put("password", userPass);

//                Log.d("2222222222",""+Build.VERSION.SDK_INT);


                /*if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    @SuppressLint("MissingPermission") String imeiNumber = telephonyManager.getDeviceId();
                    jsonObject.put("imei_no", imeiNumber);
                } else {
                    String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    jsonObject.put("imei_no", androidId);
                }*/
//                jsonObject.put("imei_no", "353398090944795");
                JsonObjectRequest request = WebRequest.callPostMethod1(Request.Method.POST, jsonObject,
                        ApiConstants.LOGIN_URL, this, "");
                App.getInstance().addToRequestQueue(request, ApiConstants.LOGIN_URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "Please check your internet connectivity", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case ApiConstants.LOGIN_URL: {
                dismissLoadingDialog();

                Log.d("11111111111111",""+jsonResponse);
                String value = jsonResponse.success;
                if (value.equals("true")){
                    Log.d("11111111111111",""+jsonResponse);

                    Toast.makeText(mContext, jsonResponse.success, Toast.LENGTH_SHORT).show();
                    setValues();
                } else {
                    Toast.makeText(mContext, jsonResponse.success, Toast.LENGTH_SHORT).show();

                }

            }}
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        dismissLoadingDialog();
        Log.d("22222222222222",""+response);
        Toast.makeText(mContext, getString(R.string.api_fail_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAsyncCompletelyFail(String message, String label) {
        dismissLoadingDialog();
        Log.d("33333333333333",""+label);
        Toast.makeText(mContext, getString(R.string.api_fail_message), Toast.LENGTH_SHORT).show();

    }

    private void setValues(){
        btnLogin.setVisibility(View.GONE);
        btnSignOut.setVisibility(View.VISIBLE);
        linearInput.setVisibility(View.GONE);
        linearView.setVisibility(View.VISIBLE);
        txtId.setText("Hello "+userId);
    }
}
