package mca.project.agrozen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewImages extends AppCompatActivity {

    private static final String REQUEST_URL = "https://concepthover.com/agrozen/retrivimage.php";
    RecyclerView imagelist;

    ImAdapter imageAdapter;
    ArrayList<String> imglist;
    ArrayList<String> rslist;
    ArrayList<String> dnlist;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);

        imagelist = findViewById(R.id.image_recycleview);
        imagelist.setHasFixedSize(true);
        imagelist.setLayoutManager(new LinearLayoutManager(ViewImages.this));

        imglist = new ArrayList<>();
        rslist = new ArrayList<>();
        dnlist = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        pd=new ProgressDialog(ViewImages.this);
        pd.setMessage("Fetching your result...");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("failure")) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                }
                else if(response.equals("no data found"))
                {
                    pd.dismiss();
                    Toast.makeText(ViewImages.this, "no data found", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd.dismiss();

                    try {

                        JSONObject obj = new JSONObject(response);
                        JSONArray arr = obj.getJSONArray("project_details");//json array formate name in php

                        //traversing through all the object
                        for (int i = 0; i < arr.length(); i++) {

                            //getting product object from json array
                            JSONObject newsmessage = arr.getJSONObject(i);

                            imglist.add(newsmessage.getString("imageurl"));
                            rslist.add(newsmessage.getString("result"));
                            dnlist.add(newsmessage.getString("dname"));

                            //adding the product to product list);

                        }

                        ImageRecyclerView imageAdapter = new ImageRecyclerView(getApplicationContext() , imglist , dnlist , rslist);
                        imagelist.setAdapter(imageAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Creating a shared preference`
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}
