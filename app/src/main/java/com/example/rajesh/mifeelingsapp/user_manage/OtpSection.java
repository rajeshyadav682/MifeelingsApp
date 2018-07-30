package com.example.rajesh.mifeelingsapp.user_manage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rajesh.mifeelingsapp.R;
import com.example.rajesh.mifeelingsapp.user_manage.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class OtpSection extends AppCompatActivity {
    private EditText otpnum;
    private EditText newpass;
    private Button submit;
    private TextView phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_section);
        otpnum = (EditText) findViewById(R.id.otp);
        newpass = (EditText) findViewById(R.id.otp_newpass);
        submit = (Button) findViewById(R.id.changepassbtn);
        phn = (TextView)findViewById(R.id.phn) ;
        String phonNumber =getIntent().getExtras().getString("phoneNumber");
        phn.setText("******"+phonNumber.substring(6,10));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordchangeRequest();
            }
        });
    }

    public void passwordchangeRequest() {
        String id= getIntent().getExtras().getString("userid");

        String otpcheck = otpnum.getText().toString().trim();
        String changepass = newpass.getText().toString();

        if (otpcheck.equals("")) {
            otpnum.setError("Enter Valid otp");
        } else if (otpcheck.length() > 10) {
            otpnum.setError("Max length 10 allowed");
        } else if (changepass.equals("")) {
            newpass.setError("Enter new password");
        } else if (changepass.length() > 15) {
            newpass.setError("Max length 15 allowed");
        } else {
            String otpverify = getIntent().getExtras().getString("otp");
            String phonNumber =getIntent().getExtras().getString("phoneNumber");
            Log.e("success otp", otpverify);
            if (!otpcheck.equals(otpverify)) {
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            } else {

                Log.e("OTP verified",otpverify);
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String newPassword = newpass.getText().toString();
                String passChangeApi = "http://110.227.190.241:7070/apis/manage-user/users/psw-frgt/upt-psw-req";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("password",newPassword);
                    jsonBody.put("verify_id",id);
                    final String mRequestBody = jsonBody.toString();
                    Log.e("json otp section",mRequestBody);
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, passChangeApi, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Put Response is", response);
                            if (response.equals("200")){
                                Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Put Error is", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/jsonBodybody; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {

                                responseString = String.valueOf(response.statusCode);

                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }




        }
    }
}