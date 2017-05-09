package com.example.lovemoon.talk;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.res.Resources;
import java.util.Locale;
import android.util.DisplayMetrics;
import android.widget.AdapterView.OnItemSelectedListener;

public class ManHinhChinh extends AppCompatActivity {
    TextView sin;
    LinearLayout circle;
    Locale myLocale;
    String arr[]={
            "English",
            "Vietnamese"};
    int check = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setLocaleDefault("vi");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        circle = (LinearLayout)findViewById(R.id.circle);
        sin = (TextView)findViewById(R.id.sin);

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(ManHinhChinh.this,signup.class);
                startActivity(it);

            }
        });
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ManHinhChinh.this,login.class);
                startActivity(it);

            }
        });

        Spinner spin=(Spinner) findViewById(R.id.spinner1);
        //Gán Data source (arr) vào Adapter
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (
                        this,
                        R.layout.spinner_item,
                        arr
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        //Thiết lập adapter cho Spinner
        spin.setAdapter(adapter);
        //spin.setBackgroundColor(0);
        if(getResources().getConfiguration().locale.getLanguage()=="vi") {
            arr[0] = "Tiếng Anh";
            arr[1] = "Tiếng Việt";
            spin.setSelection(1);
        }
        else
            spin.setSelection(0);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spin.setOnItemSelectedListener(new MyProcessEvent());
    }
    public void setLocale(String lang) {
        if(lang != "df")
            myLocale = new Locale(lang);
        else
            myLocale = myLocale.getDefault();
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        //Intent refresh = new Intent(this, ManHinhChinh.class);
        //startActivity(refresh);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    //Class tạo sự kiện spinner
    private class MyProcessEvent implements OnItemSelectedListener
    {
        //Khi có chọn lựa thì vào hàm này
        public void onItemSelected(AdapterView<?> arg0,
                                   View arg1,
                                   int arg2,
                                   long arg3) {
            //arg2 là phần tử được chọn trong data source
            if(++check > 1) {
                if (arg2 == 0)
                    setLocale("df");
                else
                    setLocale("vi");
            }
        }
        //Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
