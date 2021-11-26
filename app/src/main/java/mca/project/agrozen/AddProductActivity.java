package mca.project.agrozen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    EditText pdEdit, userEdit, phoneEdit, durationEdit, locationEdit, priceEdit;

    String product_name = "";
    String duration = "";
    String location = "";
    String user_name = "";
    String phone = "";
    String price = "";
    ProgressDialog progressDialog;
    Button nextBtn,imgupload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        pdEdit = findViewById(R.id.pdname_edit);
        userEdit = findViewById(R.id.username_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        durationEdit = findViewById(R.id.duration_edit);
        locationEdit = findViewById(R.id.location_edit);
        priceEdit = findViewById(R.id.price_edit);
        nextBtn = findViewById(R.id.upload);
        imgupload=findViewById(R.id.upload_img);
        imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);

            }
        });
        setTitle("Sign Up");
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputValid()) {

                    System.out.println("// Signup Api: reached in inputvalidation in oncreate ");
                    product_name = pdEdit.getText().toString();
                    duration = durationEdit.getText().toString();
                    location = locationEdit.getText().toString();
                    user_name = userEdit.getText().toString();
                    phone = phoneEdit.getText().toString();
                    price = priceEdit.getText().toString();


                    registerApi();
                }
                else {
                    Toast.makeText(AddProductActivity.this, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Input Validation
    private boolean inputValid() {

        System.out.println("// Signup Api: reached in inputvalidation ");

        if (userEdit.getText().toString().equals("")) {
            userEdit.setError("Cannot be Empty");
            return false;
        }

        if (phoneEdit.getText().toString().length() != 10) {
            phoneEdit.setError("Invalid Phone Number");
            return false;
        }
        if (priceEdit.getText().toString().equals("")) {
            priceEdit.setError("Cannot be Empty");
            return false;
        }
        if (durationEdit.getText().toString().equals("")) {
            durationEdit.setError("Cannot be Empty");
            return false;
        }
        if (locationEdit.getText().toString().equals("")) {
            locationEdit.setError("Cannot be Empty");
            return false;
        }
        if (pdEdit.getText().toString().equals("")) {
            pdEdit.setError("Cannot be Empty");
            return false;
        }

        return true;
    }

    // Register Api
    private void registerApi() {

        String url = "https://concepthover.com/agrozen/agro_rent.php";

        System.out.println("// Signup Api: " + url);

        progressDialog = new ProgressDialog(AddProductActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("");
        progressDialog.setMessage("Registering..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            System.out.println("// Signup Response: " + jsonObject);

                            String result = jsonObject.getString("result");

                            if (result.equals("1")) {

                                Toast.makeText(AddProductActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();


                                // Go to Home Screen
                                Intent intent = new Intent(AddProductActivity.this, RentActivity.class);
                                startActivity(intent);
                                finishAffinity();

                            } else {

                                String message = jsonObject.getString("message");
//                                Snackbar snackbar = Snackbar.make(coordinatorLayout, message,
//                                        Snackbar.LENGTH_LONG);
//                                snackbar.setDuration(4000);
//                                snackbar.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        System.out.println("// Error: " + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("productname", product_name);
                params.put("username", user_name);
                params.put("phonenumber", phone);
                params.put("price", price);
                params.put("duration", duration);
                params.put("location", location);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // Check internet connection
    private boolean checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}