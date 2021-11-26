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
import android.widget.TextView;
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

public class SignupActivity extends AppCompatActivity {

    EditText nameEdit, userEdit, phoneEdit, passwordEdit;
    TextView txt_mov;
    String full_name = "";
    String user_name = "";
    String phone = "";
    String password = "";
    ProgressDialog progressDialog;
    Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameEdit = findViewById(R.id.fullname_edit);
        userEdit = findViewById(R.id.username_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        passwordEdit = findViewById(R.id.password_edit);
        nextBtn = findViewById(R.id.continue_btn);
        txt_mov = findViewById(R.id.login_go);
        txt_mov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
        setTitle("Sign Up");
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputValidation()) {

                    full_name = nameEdit.getText().toString();
                    user_name = userEdit.getText().toString();
                    phone = phoneEdit.getText().toString();
                    password = passwordEdit.getText().toString();


                    registerApi();
                }
                else {
                    Toast.makeText(SignupActivity.this, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
       }


    // Input Validation
    private boolean inputValidation() {

        if (userEdit.getText().toString().equals("")) {
            userEdit.setError("Cannot be Empty");
            return false;
        }

        if (phoneEdit.getText().toString().length() != 10) {
            phoneEdit.setError("Invalid Phone Number");
            return false;
        }
        if (passwordEdit.getText().toString().equals("")) {
            passwordEdit.setError("Cannot be Empty");
            return false;
        }
        if (nameEdit.getText().toString().equals("")) {
            nameEdit.setError("Cannot be Empty");
            return false;
        }

        return true;
    }

    // Register Api
    private void registerApi() {

        String url = "https://concepthover.com/agrozen/agrozen_signup.php";

        System.out.println("// Signup Api: " + url);

        progressDialog = new ProgressDialog(SignupActivity.this);
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

                                Toast.makeText(SignupActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();


                                // Go to Home Screen
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
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
                params.put("fullname", full_name);
                params.put("username", user_name);
                params.put("phonenumber", phone);
                params.put("password", password);
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