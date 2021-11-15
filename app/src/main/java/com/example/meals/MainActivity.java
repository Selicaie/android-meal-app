package com.example.meals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ListView meal_listview ;
    int images_list [] = {R.drawable.burger,R.drawable.chicken,R.drawable.crepe,R.drawable.desert,R.drawable.fishh,R.drawable.meate
            ,R.drawable.pasta,R.drawable.pizzaa};
    String image_name []= {"burger","chicken","crepe","desert","fish","meat","pasta","pizza"};

    ArrayList<Integer> images = new ArrayList();
    ArrayList<String> title = new ArrayList();
    ArrayList<String> description = new ArrayList<>();

    DB_sqlite dp = new DB_sqlite(this);

    String u_id , ismanger;

    MyAdapter adapter ;
    @Override
    protected void onResume() {
        super.onResume();
        // get current week day
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        images.clear();
        title.clear();
        description.clear();
        // put data to listview and notify change
        ArrayList arr = dp.get_meals(dayOfTheWeek);
        for(int i=0 ; i<arr.size();i++){
            String rr [] = arr.get(i).toString().split("_/_");
            System.out.println(rr[0]+" "+rr[1]+" "+rr[2]+" "+rr[3]);
            images.add(images_list[Arrays.asList(image_name).indexOf(rr[2])]);
            title.add(rr[0]);
            description.add(rr[1]);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         u_id = getIntent().getStringExtra("id");

         // check if user manger then can add meal
         ismanger = getIntent().getStringExtra("manger");
         if(ismanger.equals("no")){
             FloatingActionButton fab = findViewById(R.id.fab);
             fab.setVisibility(View.INVISIBLE);
         }
         //------------------------------------------
         adapter = new MyAdapter(this, title,  images);
         meal_listview = findViewById(R.id.menulist);
         meal_listview.setAdapter(adapter);

         meal_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this,""+i,Toast.LENGTH_LONG).show();
                Intent t = new Intent(getBaseContext(),confirm_meal.class);
                t.putExtra("name",title.get(i));
                t.putExtra("description",description.get(i));
                t.putExtra("image",images.get(i));
                t.putExtra("id",u_id);
                startActivity(t);
            }
        });
    }

    public void update(View view) {

//        Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();
        Intent t = new Intent(getBaseContext(),add_meal.class) ;
        startActivity(t);
    }


    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> rTitle;
        ArrayList<Integer> rImgs;

        MyAdapter (Context c, ArrayList<String> title, ArrayList<Integer> imgs) {
            super(c, R.layout.row, R.id.meal_name, title);
            this.context = c;
            this.rTitle = title;
            this.rImgs = imgs;

        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.meal_img);
            TextView myTitle = row.findViewById(R.id.meal_name);
            ImageView arrow = row.findViewById(R.id.arrow);

            // now set our resources on views
            images.setImageResource(rImgs.get(position));
            myTitle.setText(rTitle.get(position));
            arrow.setImageResource(R.drawable.arrow_right);

            return row;
        }
    }

}