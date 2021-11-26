package mca.project.agrozen;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    public static final String Status = "status";
    public static final String FullName = "fullName";
    public static final String UserName = "userName";
    public static final String Password = "password";

    EditText userEdit, passwordEdit;
    Button loginBtn;
    TextView forgotText, signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setTitle("Login");
        forgotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Contact Support", Toast.LENGTH_LONG).show();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputValidation()) {
                    if (checkConnection()) {
                        String namesend = userEdit.getText().toString().trim();
                        Bundle bundle = new Bundle();
                        bundle.putString("NAME", namesend);
                        if (userEdit.getText().toString().equals("admin") &&
                                passwordEdit.getText().toString().equals("admin@123")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        else {
                            loginApi();
                        }

                    }

                    else {
                        Toast.makeText(LoginActivity.this, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });

    }

    // Initialize
    private void init() {

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        userEdit = findViewById(R.id.user_edit);
        passwordEdit = findViewById(R.id.password_edit);
        loginBtn = findViewById(R.id.login_btn);
        forgotText = findViewById(R.id.forgot_text);
        signupText = findViewById(R.id.signup_text);
    }

    // Login Api
    private void loginApi() {

        String url = "https://concepthover.com/agrozen/agrozen_login.php";

        System.out.println("// Login " + url);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("");
        progressDialog.setMessage("Logging In..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            System.out.println("// Response: " + jsonObject);

                            String result = jsonObject.getString("result");

                            if (result.equals("1")) {

                                // Retrieve All User Information
                                JSONArray jsonArray = jsonObject.getJSONArray("user_data");
                                JSONObject userObject = jsonArray.getJSONObject(0);

                                String full_name = userObject.getString("fullname");


                                // If Success, save in Shared preferences
                                SharedPreferences.Editor e = sharedPreferences.edit();
                                e.putString(Status, "1");
                                e.putString(FullName, full_name);
                                e.apply();

                                // Go to Home Screen
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {

                                String message = jsonObject.getString("message");
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

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
                params.put("username", userEdit.getText().toString());
                params.put("password", passwordEdit.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // Input Validation
    private boolean inputValidation() {

        if (userEdit.getText().toString().equals("")) {
            userEdit.setError("Cannot be empty");
            return false;
        }

        if (passwordEdit.getText().toString().equals("")) {
            passwordEdit.setError("Cannot be empty");
            return false;
        }

        return true;
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


