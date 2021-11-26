package mca.project.agrozen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Splase extends AppCompatActivity {

    EditText n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splase);

        Handler h = new Handler();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(in);
                finish();
            }
        };

        h.postDelayed(r,3000);
    }
}
