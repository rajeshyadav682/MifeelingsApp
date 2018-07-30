package com.example.rajesh.mifeelingsapp.user_manage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.rajesh.mifeelingsapp.Home_Page.BaseActivity;
import com.example.rajesh.mifeelingsapp.Host_ip_Config;
import com.example.rajesh.mifeelingsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends BaseActivity {
    private NestedScrollView nestedScrollView;
    private TextView textInputEditTextUserid;
    private EditText textInputEditTextAge;
    private EditText textInputEditTextUserName;
    private EditText textInputEditTextPhone;
    private EditText textInputEditTextGender;
    private EditText textInputEditTextEmail;
    private TextView textInputEditTextPassword;
    private Button profileDone;
    private Button profileEdit;
    private CircleImageView profileImage;
    String message, encodedImage;
    private ProgressDialog pDialog;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String userChoosenTask;
    Bitmap thumbnail = null;
    public String user_id;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_my_profile, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);
        Toast.makeText(this, navigationView.getMenu().getItem(1).setChecked(true).toString(), Toast.LENGTH_SHORT).show();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        user_id = ((MyApplication)this.getApplication()).getUserId();
      //  String user_id="dinesh007";
        Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();



        userDetails(user_id);
        profileEdit = (Button) findViewById(R.id.profil_edit);
        profileEdit.setVisibility(View.VISIBLE);

    }

    public void userDetails(final String user_id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Host_ip_Config.hostofficeIp + "/apis/manage-user/users/users-profile/" + user_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.e("Response is: ", response.toString());
                        //     Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Log.e("my profile response", response);
                            String uid = jsonObj.getString("_id");
                            final String uname = jsonObj.getString("user_name");
                            String phone = jsonObj.getString("mob_no");
                            String gender = jsonObj.getString("gender");
                            String age = jsonObj.getString("age");
                            final String email_id = jsonObj.getString("email");
                            final String pass = jsonObj.getString("password");
                            String image = jsonObj.getString("image");
                            textInputEditTextUserid = (TextView) findViewById(R.id.getid);
                            textInputEditTextUserName = (EditText) findViewById(R.id.getname);
                            textInputEditTextPhone = (EditText) findViewById(R.id.getphone);
                            textInputEditTextGender = (EditText) findViewById(R.id.getgender);
                            textInputEditTextAge = (EditText) findViewById(R.id.getage);
                            textInputEditTextEmail = (EditText) findViewById(R.id.getemail);
                            textInputEditTextPassword = (TextView) findViewById(R.id.getpass);
                            textInputEditTextUserid.setText(uid);
                            textInputEditTextUserid.setVisibility(View.VISIBLE);
                            textInputEditTextUserName.setVisibility(View.VISIBLE);
                            textInputEditTextPhone.setVisibility(View.VISIBLE);
                            textInputEditTextGender.setVisibility(View.VISIBLE);
                            textInputEditTextAge.setVisibility(View.VISIBLE);
                            textInputEditTextEmail.setVisibility(View.VISIBLE);
                            textInputEditTextPassword.setVisibility(View.VISIBLE);
                            profileImage = (CircleImageView) findViewById(R.id.user_profile_photo);
                            Glide.with(getApplicationContext()).load(Host_ip_Config.hostofficeIp + image).into(profileImage);

                            textInputEditTextUserName.setText(uname);
                            textInputEditTextPhone.setText(phone);
                            textInputEditTextGender.setText(gender);
                            textInputEditTextAge.setText(age);
                            textInputEditTextEmail.setText(email_id);
                            textInputEditTextPassword.setText("*******");
                            textInputEditTextUserName.setEnabled(false);
                            textInputEditTextPhone.setEnabled(false);
                            textInputEditTextGender.setEnabled(false);
                            textInputEditTextAge.setEnabled(false);
                            textInputEditTextEmail.setEnabled(false);
                            profileEdit = (Button) findViewById(R.id.profil_edit);
                            profileDone = (Button) findViewById(R.id.profile_done);
                            profileEdit.setOnClickListener(new View.OnClickListener() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onClick(View v) {
                                    profileEdit.setVisibility(View.INVISIBLE);
                                    profileDone.setVisibility(View.VISIBLE);
                                    textInputEditTextUserName.setEnabled(true);
                                    textInputEditTextPhone.setEnabled(true);
                                    textInputEditTextGender.setEnabled(true);
                                    textInputEditTextAge.setEnabled(true);
                                    textInputEditTextEmail.setEnabled(true);
                                    textInputEditTextUserName.setTextColor(R.color.red);
                                    textInputEditTextPhone.setTextColor(R.color.red);
                                    textInputEditTextGender.setTextColor(R.color.red);
                                    textInputEditTextAge.setTextColor(R.color.red);
                                    textInputEditTextEmail.setTextColor(R.color.red);
                                    textInputEditTextPassword.setTextColor(R.color.red);

                                    Toast.makeText(MyProfile.this, "Edit Enable", Toast.LENGTH_SHORT).show();


                                }
                            });
                            profileDone.setOnClickListener(new View.OnClickListener() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onClick(View v) {
                                    updateDetails(user_id);
                                    Toast.makeText(MyProfile.this, "Profile Details Updated", Toast.LENGTH_SHORT).show();
                                    textInputEditTextUserName.setEnabled(false);
                                    textInputEditTextPhone.setEnabled(false);
                                    textInputEditTextGender.setEnabled(false);
                                    textInputEditTextAge.setEnabled(false);
                                    textInputEditTextEmail.setEnabled(false);
                                    profileDone.setVisibility(View.INVISIBLE);
                                    profileEdit.setVisibility(View.VISIBLE);
                                    textInputEditTextUserName.setTextColor(R.color.black);
                                    textInputEditTextPhone.setTextColor(R.color.black);
                                    textInputEditTextGender.setTextColor(R.color.black);
                                    textInputEditTextAge.setTextColor(R.color.black);
                                    textInputEditTextEmail.setTextColor(R.color.black);
                                    textInputEditTextPassword.setTextColor(R.color.black);
                                }
                            });
                            textInputEditTextPassword.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String old_pass = pass;
                                    Intent intent = new Intent(getApplicationContext(), Changepassword.class);
                                    intent.putExtra("oldpass", pass);
                                    intent.putExtra("id", user_id);
                                    startActivity(intent);
                                    Log.e("username get", uname);
                                    Log.e("email get", email_id);

                                }
                            });



                          profileImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (Build.VERSION.SDK_INT >= 23) {
                                        if (checkPermission()==true) {
                             //               Toast.makeText(MyProfile.this, "true", Toast.LENGTH_SHORT).show();
                                            selectimage();
                                        } else {
                                            requestPermission();
                                  //          Toast.makeText(MyProfile.this, "false", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                            if (e.getMessage().equals("com.android.volley.NoConnectionError: java.net.ConnectException: Network is unreachable")) {
                                Toast.makeText(getApplicationContext(), "please enable internet connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("That didn't work!", error.toString());
                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.ConnectException: Network is unreachable")) {
                    Toast.makeText(getApplicationContext(), "please enable internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(stringRequest);
    }

    public void updateDetails(String id) {
        try {
            String updateName = textInputEditTextUserName.getText().toString();
            String updatePhoneNo = textInputEditTextPhone.getText().toString();
           String updateGender = textInputEditTextGender.getText().toString();
            String updateEmail = textInputEditTextEmail.getText().toString();
            String updateAge = textInputEditTextAge.getText().toString();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_name", updateName);
            jsonBody.put("mob_no", updatePhoneNo);
            jsonBody.put("email", updateEmail);
            jsonBody.put("gender", updateGender);
            jsonBody.put("age", updateAge);

            String URL = Host_ip_Config.hostofficeIp + "/apis/manage-user/users/alter-users-profile/" + id;

            final String mRequestBody = jsonBody.toString();
            Log.e("json data", mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(" update Response is", response);
                    if (response.equals("200")) {
                        Toast.makeText(getApplicationContext(), "Details Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error is", error.toString());
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


            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void requestPermission() {
        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
     //       Toast.makeText(this, " Please allow this permission in App Settings.", Toast.LENGTH_SHORT).show();

        
            ActivityCompat.requestPermissions(MyProfile.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);

        }



         if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(MyProfile.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        try {
            int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

            if (result == PackageManager.PERMISSION_GRANTED || result1==PackageManager.PERMISSION_GRANTED) {
                return true;

            } else {
              //  requestPermission();
                Toast.makeText(this, "Permission Necessary for Camera & Gallery", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }
    public void imageUplaod(String id) {
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG,20, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            Log.e("encode image",encodedImage);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            final JSONObject jsonBody = new JSONObject();
            //Toast.makeText(this, imagedata, Toast.LENGTH_SHORT).show();

            jsonBody.put("user_id", id);
            jsonBody.put("image",encodedImage);
            String URL = Host_ip_Config.hostofficeIp + "/apis/manage-user/users/usr-prf-img";

            final String mRequestBody = jsonBody.toString();
            Log.e("json data", mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Log.e(" update Response is", response);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        int data=  jsonObject.getInt("msg_code");
                        if (data==1) {
                            Toast.makeText(getApplicationContext(), "Profile Pic Updated", Toast.LENGTH_SHORT).show();

                        }
                        if (data!=1){
                            Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Log.e("Error is", error.toString());
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

            };

            requestQueue.add(stringRequest);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    public void selectimage(){
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = checkPermission();

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)


                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)

                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Toast.makeText(this, destination.toString(), Toast.LENGTH_SHORT).show();

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileImage.setImageBitmap(thumbnail);
        imageUplaod(user_id);

    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        progressDialog = new ProgressDialog(this);
        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        profileImage.setImageBitmap(thumbnail);
        imageUplaod(user_id);
    }
    public void clearCache() {
        Log.e("cache", "Clearing Cache.");
        File[] dir = this.getCacheDir().listFiles();
        if(dir != null){
            for (File f : dir){
                f.delete();
            }
        }
    }
}