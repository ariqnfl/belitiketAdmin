package com.example.adminappbelitiket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyMenuAct extends AppCompatActivity {
    Button btn_edit_wisata,btn_edit_artikel,btn_sign_out;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_menu);

        btn_edit_wisata = findViewById(R.id.btn_edit_wisata);
        btn_edit_artikel = findViewById(R.id.btn_edit_artikel);
        btn_sign_out = findViewById(R.id.btn_sign_out);

        btn_edit_wisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyMenuAct.this,HomeAct.class));
            }
        });
        btn_edit_artikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyMenuAct.this,AddArticleAct.class));
            }
        });
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key,null);
                startActivity(new Intent(MyMenuAct.this,GetStartedAct.class));
                finish();
            }
        });
    }
}
