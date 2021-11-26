package mca.project.agrozen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadImage extends AppCompatActivity {

    ImageView pic;
    TextView name;

    Button upload;

    InputStream imageStream;

    String profileimg;
    Bitmap profilePicture;
    ProgressDialog pd;

    private final static int requst=1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        pic = findViewById(R.id.upload_img);
        name = findViewById(R.id.upload_name);
        upload = findViewById(R.id.upload_uploadbtn);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        name.setText("leafImage-"+currentDateandTime);

        if(getIntent().getStringExtra("k1").equals("camera")){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, requst);
        } else if (getIntent().getStringExtra("k1").equals("gallery")){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 1000);
        }

        pd = new ProgressDialog(UploadImage.this);
        pd.setTitle("Uploading");
        pd.setMessage("Loading Please Wait....");


    }

    @Override
    protected void onResume() {
        super.onResume();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1 = name.getText().toString();
                if(name1.length()!=0){
                    pd.show();
                    convertBitmapToString(profilePicture);
                    upload(name.getText().toString());
                }else { name.setError("enter valid name"); }
            }
        });
    }

    public void upload(final String img1)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://concepthover.com/agrozen/imgupload.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UploadImage.this,ViewImages.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", img1);
                params.put("image",profileimg);
                params.put("res","new upload");
                params.put("dname","waitting");
                return params;
            }

        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }
    
            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==requst) {
            Bundle extras = data.getExtras();
            profilePicture = (Bitmap) extras.get("data");
            pic.setImageBitmap(profilePicture);
            Uri imageUri = data.getData();//Geting uri of the data
        }

        else if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
                profilePicture = BitmapFactory.decodeStream(imageStream);
                pic.setImageBitmap(profilePicture);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    private void convertBitmapToString(Bitmap profilePicture) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        profilePicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] array = byteArrayOutputStream.toByteArray(   );
        profileimg = Base64.encodeToString(array, Base64.DEFAULT);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
        finish();
    }
}
