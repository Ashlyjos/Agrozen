package mca.project.agrozen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageProcessingActivity extends AppCompatActivity {

    ImageView capture,gallery;

    Bitmap profilePicture;
    ImageView img;
    boolean IMAGE_STATUS = false;
    InputStream imageStream;
    String profileimg;
    EditText nm;
    Button submit,viewimglist;
    ProgressDialog pd;
    Dialog d;
    FloatingActionButton viewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_processing);

        viewResult=findViewById(R.id.view_result);


        capture = findViewById(R.id.camera_pic);
        gallery = findViewById(R.id.gallery_pic);

        checkLocationPermission();

    }

    @Override
    protected void onResume() {
        super.onResume();

        viewResult.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(view.getContext(), ViewImages.class));
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , UploadImage.class).putExtra("k1","camera"));
                finish();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , UploadImage.class).putExtra("k1","gallery"));
                finish();
            }
        });

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(ImageProcessingActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


                ActivityCompat.requestPermissions(ImageProcessingActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
                checkmediaPermission();


            } else {
                ActivityCompat.requestPermissions(ImageProcessingActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
                checkmediaPermission();
            }
            return false;
        } else {
            return true;
        }

    }

    public boolean checkmediaPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_MEDIA_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(ImageProcessingActivity.this,
                    Manifest.permission.ACCESS_MEDIA_LOCATION)) {


                ActivityCompat.requestPermissions(ImageProcessingActivity.this,
                        new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION},
                        1);


            } else {
                ActivityCompat.requestPermissions(ImageProcessingActivity.this,
                        new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION},
                        1);
            }
            return false;
        } else {
            return true;
        }

    }

}
