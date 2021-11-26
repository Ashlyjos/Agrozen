package mca.project.agrozen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("https://krishijagran.com/");

        FloatingActionButton mAddUserFab=findViewById(R.id.add_image_fab);
        mAddUserFab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(view.getContext(), ImageProcessingActivity.class));
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.person);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                switch (item.getItemId()) {
        case R.id.person:
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        return true;

        case R.id.rent:
        intent = new Intent(MainActivity.this, RentActivity.class);
        startActivity(intent);
        return true;

        case R.id.about_us:
        intent = new Intent(MainActivity.this, AboutUsActivity.class);
        startActivity(intent);
        return true;

        case R.id.tips_tricks:
        intent = new Intent(MainActivity.this, TipsTricks.class);
        startActivity(intent);
        return true;

        }
                return false;
            }
        });

    }

}


//bottomNavigationView.setOnNavigationItemSelectedListener(
//        new BottomNavigationView.OnNavigationItemSelectedListener() {
//@Override
//public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//        case R.id.person:
//        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//        startActivity(intent);
//        return true;
//
//        case R.id.rent:
//        intent = new Intent(MainActivity.this, RentActivity.class);
//        startActivity(intent);
//        return true;
//
//        case R.id.about_us:
//        intent = new Intent(MainActivity.this, AboutUsActivity.class);
//        startActivity(intent);
//        return true;
//        }
//        return false;
//        }
//        });

