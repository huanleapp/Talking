package com.example.lovemoon.talk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    private EditText barcodetextScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scan);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        List<BarcodeFormat> ALL_FORMATS = new ArrayList();
        ALL_FORMATS.add(BarcodeFormat.QR_CODE);
        //ALL_FORMATS.add(BarcodeFormat.DATA_MATRIX);
        //mScannerView.setFormats(ALL_FORMATS);
        setContentView(mScannerView);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getBarcodeFormat().toString().trim() , Toast.LENGTH_LONG).show();

       // Toast.makeText(getApplicationContext(),result.getText().toString().trim() , Toast.LENGTH_LONG).show();
        qrcode_scanner.barcodetextScan.setText(result.getText().toString().trim());
        onBackPressed();
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}
