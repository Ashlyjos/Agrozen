package mca.project.agrozen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
Button signout_btn;
TextView name_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name_txt = findViewById(R.id.txtFirstName);
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            String nameget = bundle.getString("NAME");
            name_txt.setText("Total Cost=Rs."+nameget);
        }

        signout_btn = findViewById(R.id.signOutButton);
        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}