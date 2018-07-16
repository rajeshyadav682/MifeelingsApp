package com.example.rajesh.mifeelingsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Changepassword extends AppCompatActivity {
    private Button cngpas;
    private EditText oldpas;
    private EditText newpas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        final String user_id = getIntent().getExtras().getString("id");
        final String oldpaswrd = getIntent().getExtras().getString("oldpass");
        cngpas = (Button)findViewById(R.id.changepassbtnpr);
        cngpas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepasswordApi(user_id,oldpaswrd);
            }
        });
    }

    private void changepasswordApi(final String user_id, String oldpaswrd) {
        oldpas = (EditText)findViewById(R.id.oldpass);
        newpas = (EditText)findViewById(R.id.newpasswd);
        String old_pass = oldpas.getText().toString();
        String nwpas = newpas.getText().toString();
        if (old_pass.equals("")) {
            oldpas.setError("Enter Current Password");
        } else if (old_pass.length() > 15) {
            oldpas.setError("Max length 10 allowed");
        } else if (nwpas.equals("")) {
            newpas.setError("Enter new password");
        } else if (nwpas.length() > 15) {
            newpas.setError("Max length 15 allowed");
        }
        else if (old_pass.equals(nwpas)){
                newpas.setError("New Password Must Be diffrent");
        } else {

            if (!oldpaswrd.equals(old_pass)) {
                Toast.makeText(this, "please enter valid current password", Toast.LENGTH_SHORT).show();
            } else {

                Log.e("oldpassword verify", oldpaswrd);
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String passChangeApi = Host_ip_Config.hostofficeIp+"/apis/manage-user/users/psw-rst";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("_id",user_id);
                    jsonBody.put("password",oldpaswrd);
                    jsonBody.put("new_password",nwpas);
                    final String mRequestBody = jsonBody.toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, passChangeApi, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Put Response is", response);
                            if (response.equals("200")){
                                oldpas.setText("");
                                newpas.setText("");
                                Toast.makeText(getApplicationContext(), "Password changed Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                                intent.putExtra("_id",user_id);
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