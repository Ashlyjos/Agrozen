package mca.project.agrozen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RentActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://concepthover.com/agrozen/getProducts.php";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager manager;
    private RecyclerView recyclerView;
    private List<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        FloatingActionButton mAddUserFab=findViewById(R.id.add_image_fab);
        mAddUserFab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(view.getContext(), AddProductActivity.class));




                recyclerView = findViewById(R.id.products_recyclerView);
                manager = new GridLayoutManager(RentActivity.this, 2);
                recyclerView.setLayoutManager(manager);
                products = new ArrayList<>();
                getProducts();
            }
        });
    }

    private void getProducts(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    System.out.println("// Response: "+response);

                    String result = response.getString("message");

                    if (result.equals("Success")) {

                        JSONArray array = response.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);

                            String pdname = object.getString("productname");
                            String price = object.getString("price");
                            String phoneno = object.getString("phonenumber");
                            String duration = object.getString("duration");
                            String loc = object.getString("location");
                            String image = object.getString("image");

                            Product product = new Product(pdname, price, phoneno, duration, loc, image);
                            products.add(product);
                        }
                    }
                    else {
                        // show a toast or message , aything else? no bro baaki ellam set aanu ok tahnkyou bro cheers!! Good Night good night broooo
                    }


                } catch (Exception e){
                    }

                mAdapter = new RecyclerAdapter(RentActivity.this,products);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RentActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });


        Volley.newRequestQueue(RentActivity.this).add(jsonObjectRequest);
    }
}