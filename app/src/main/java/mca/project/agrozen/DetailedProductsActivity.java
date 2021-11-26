package mca.project.agrozen;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailedProductsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private ImageView mImage;
    private TextView mname, mphone, mPrice, mLoc, mdura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_products);

        mToolbar = findViewById(R.id.toolbar);
        mImage = findViewById(R.id.image_view);
        mPrice = findViewById(R.id.price);
        mname = findViewById(R.id.name);
        mphone = findViewById(R.id.phonenumber);
        mLoc = findViewById(R.id.location);
        mdura = findViewById(R.id.duration);

        // Setting up action bar
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));

        // Catching incoming intent
        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        String title = intent.getStringExtra("productname");
        String image = intent.getStringExtra("image");
        String pno = intent.getStringExtra("phonenumber");
        String loc = intent.getStringExtra("location");
        String dur = intent.getStringExtra("duration");

        if (intent !=null){

            mActionBar.setTitle(title);
            mname.setText("Name: "+title);
            mphone.setText("Phone Number: "+pno);
            mLoc.setText("Location: "+loc);
            mdura.setText("Duration: "+dur);
            mPrice.setText("Price:"+String.valueOf(price));
            Glide.with(DetailedProductsActivity.this).load(image).into(mImage);
        }

    }
}