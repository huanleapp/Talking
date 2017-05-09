package com.example.lovemoon.talk;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import barcode_generator.Contents;
import barcode_generator.QRCodeEncoder;

public class qrcode_generator extends AppCompatActivity {
    private TextView barcodetext_btn;
    private EditText barcodetext;
    private TextView barcodetext_btn_down;
    private LinearLayout download1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);
        barcodetext_btn_down = (TextView) findViewById(R.id.barcodetext_btn_down);
        download1 = (LinearLayout) findViewById(R.id.download1);

        barcodetext_btn = (TextView)findViewById(R.id.barcodetext_btn);
        barcodetext = (EditText) findViewById(R.id.barcodetext);
        barcodetext_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView) findViewById(R.id.barcodeimg);

                String qrData = barcodetext.getText().toString().trim();
                int qrCodeDimention = 500;

                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null,
                            Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

                try {
                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                    imageView.setImageBitmap(bitmap);
                    download1.setVisibility(View.VISIBLE);
                    barcodetext_btn_down.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    download1.setVisibility(View.INVISIBLE);
                    barcodetext_btn_down.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }
            }
        });
    }
}
