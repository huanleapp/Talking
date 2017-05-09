package com.example.lovemoon.talk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;



public class qrcode_scanner extends AppCompatActivity {
    private TextView barcodetext_btnScan;
    public static EditText barcodetextScan;

    //private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        barcodetext_btnScan = (TextView) findViewById(R.id.barcodetext_btnScan);
        barcodetextScan = (EditText) findViewById(R.id.barcodetextScan);
        //Initializing scan object
                //qrScan = new IntentIntegrator(this);
        barcodetext_btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //qrScan.initiateScan();
                Intent intent = new Intent(qrcode_scanner.this, ScanActivity.class);
                startActivity(intent);
            }
        });
    }
    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    //barcodetextScan.setText(obj.getString("name"));
                    barcodetextScan.setText(result.getContents());
                    //textViewAddress.setText(obj.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/

}
