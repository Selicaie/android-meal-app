package com.example.meals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class login extends AppCompatActivity {

    DB_sqlite dp = new DB_sqlite(this);
    EditText email,pass ;
    RadioButton manger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        manger = findViewById(R.id.manger);
    }

    public void login(View view) {
        if(email.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(), "enter email", Toast.LENGTH_SHORT).show();
        }else{
            if(pass.getText().toString().isEmpty()){
                Toast.makeText(getBaseContext(), "enter pass", Toast.LENGTH_SHORT).show();
            }else {
                String temp = dp.have_user(email.getText().toString(),pass.getText().toString());
                if(!temp.isEmpty()){
                    String s[] = temp.split("_/_");
                    Intent t = new Intent(getBaseContext(),MainActivity.class);
                    t.putExtra("id",s[0]);
                    t.putExtra("manger",s[1]);
                    startActivity(t);
                }else
                    Toast.makeText(getBaseContext(), "no user found", Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void signup(View view) {
        if(email.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(), "enter email", Toast.LENGTH_SHORT).show();
        }else{
            if(pass.getText().toString().isEmpty()){
                Toast.makeText(getBaseContext(), "enter pass", Toast.LENGTH_SHORT).show();
            }else {
                String temp = "" ;
                if(manger.isChecked()) temp="yes";  else  temp="no";
                if(dp.insert_user(email.getText().toString(),pass.getText().toString(),temp)){
                    Toast.makeText(getBaseContext(), "SignUp Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}