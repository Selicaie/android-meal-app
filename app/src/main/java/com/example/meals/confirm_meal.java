package com.example.meals;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class confirm_meal extends AppCompatActivity {

    String uid,date;
    DB_sqlite dp = new DB_sqlite(this);
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_meal);

        ImageView image = findViewById(R.id.img_meal);
        TextView description = findViewById(R.id.description_meal);
        TextView name = findViewById(R.id.name_meal);
        confirm = findViewById(R.id.confirm);

        image.setImageResource(getIntent().getIntExtra("image",0));
        description.setText(getIntent().getStringExtra("description"));
        name.setText(getIntent().getStringExtra("name"));
        uid = getIntent().getStringExtra("id");

        // get current date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        date= df.format(c);

        if(dp.have_meal(uid,date)){
            confirm.setBackgroundColor(Color.GRAY);
            confirm.setActivated(false);
            confirm.setEnabled(false);
        }

    }

    public void confirm(View view) {
        System.out.println("heree");
        if(dp.confirm(uid,date)) {
            confirm.setBackgroundColor(Color.GRAY);
            confirm.setActivated(false);
            confirm.setEnabled(false);
        }else
            System.out.println("ereeor");
    }
}