package com.example.sdvs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class LoginActivity extends Activity {

    EditText edit_user, edit_pass;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edit_user = findViewById(R.id.edit_username);
        edit_pass = findViewById(R.id.edit_password);
        start = (Button) findViewById(R.id.button_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edit_user.getText().toString();
                String password = edit_pass.getText().toString();

                if (username.equals("admin") & password.equals("admin123")) {
                       Intent intent = new Intent(LoginActivity.this,AddEmployee.class);
                       startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
