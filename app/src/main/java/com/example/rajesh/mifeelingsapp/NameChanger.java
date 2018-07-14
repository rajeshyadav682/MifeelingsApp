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

public class NameChanger extends AppCompatActivity {
    private EditText namechange;
    private Button nameChnageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_changer);
        namechange = (EditText) findViewById(R.id.newname);
        final String name = getIntent().getExtras().getString("oldname");
        final String uid = getIntent().getExtras().getString("_id");
        namechange.setText(name);
        nameChnageBtn = (Button)findViewById(R.id.changenameBtnn);

       nameChnageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chnageName(uid);




            }
        });

    }

    private void chnageName(final String user_id) {

        String newname = namechange.getText().toString();


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String NameChnageApi = Host_ip_Config.hostofficeIp + "/apis/manage-user/users/alter-users-profile/" + user_id;

        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("user_name", newname);
            final String mRequestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, NameChnageApi, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Put Response is", response);
                    if (response.equals("200")) {

                        Toast.makeText(getApplicationContext(), "Name changed Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
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