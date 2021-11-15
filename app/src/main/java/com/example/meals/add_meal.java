package com.example.meals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

public class add_meal extends AppCompatActivity {

    String[] days = {"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
    Integer images [] = {R.drawable.burger,R.drawable.chicken,R.drawable.crepe,R.drawable.desert,R.drawable.fishh,R.drawable.meate
            ,R.drawable.pasta,R.drawable.pizzaa};
    String image_name_list []= {"burger","chicken","crepe","desert","fish","meat","pasta","pizza"};
    EditText meal_name,meal_descripe;
    DB_sqlite dp = new DB_sqlite(this);
    String day=days[0],image_name=image_name_list[0];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        //--------------------------------week days spinner -------------------------
        Spinner  spiner =  findViewById(R.id.days_spinner);
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                day = days[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getBaseContext(), "nothing", Toast.LENGTH_SHORT).show();
            }
        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,days);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spiner.setAdapter(aa);

       //------------------------------------ food image spinner -------------------------------------
        Spinner image = findViewById(R.id.images);
        SimpleImageArrayAdapter adapter = new SimpleImageArrayAdapter(this,images);
        image.setAdapter(adapter);
        image.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                image_name = image_name_list[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getBaseContext(), "nothing", Toast.LENGTH_SHORT).show();
            }
        });
        //--------------------------------------------------------------------
        meal_name = findViewById(R.id.meal_name_input);
        meal_descripe = findViewById(R.id.meal_descripe);
    }

    public void add_meal(View view) {
        if(meal_name.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(), "fill meal name", Toast.LENGTH_SHORT).show();
        }else {
            if(meal_descripe.getText().toString().isEmpty()){
                Toast.makeText(getBaseContext(), "fill meal describe", Toast.LENGTH_SHORT).show();
            }else{
               if(dp.insert_meal(meal_name.getText().toString()
                       ,meal_descripe.getText().toString(),day,image_name)){
                   Toast.makeText(getBaseContext(), "item added successfully", Toast.LENGTH_SHORT).show();
                   meal_descripe.setText("");
                   meal_name.setText("");
               }
            }
        }
    }


    public class SimpleImageArrayAdapter extends ArrayAdapter<Integer> {
        private Integer[] images;

        public SimpleImageArrayAdapter(Context context, Integer[] images) {
            super(context, android.R.layout.simple_spinner_item, images);
            this.images = images;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getImageForPosition(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getImageForPosition(position);
        }

        private View getImageForPosition(int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(images[position]);
            imageView.setLayoutParams(new AbsListView.LayoutParams(500,300));


            return imageView;
        }
    }

}