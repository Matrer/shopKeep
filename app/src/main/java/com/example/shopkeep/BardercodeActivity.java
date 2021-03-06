package com.example.shopkeep;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class BardercodeActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;

    String qrCode = "";
    //Intent produktAct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bardercode_main);
        surfaceView = findViewById(R.id.camerapreview);
        textView = findViewById(R.id.textView);
        findViewById(R.id.buttonAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qrCode.length()  == 13)
                {
                    Intent intent = new Intent(getApplicationContext(), MakeBarcodeActivity.class);
                    intent.putExtra("barcode",qrCode);
                    getApplicationContext().startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Niepoprawny kod kreskowy",Toast.LENGTH_SHORT).show();
                }
            }
        });

        barcodeDetector = new BarcodeDetector.Builder(this).
                setBarcodeFormats(Barcode.EAN_13).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 480)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                try {
                    cameraSource.start(holder);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if(qrCodes.size()!=0) {

                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(qrCodes.valueAt(0).displayValue);
                            Vibrator vibraton = (Vibrator)getApplication().getSystemService(Context.VIBRATOR_SERVICE);
                         //   vibraton.vibrate(500);
                            //produktAct = new Intent(getApplicationContext(), productActivity.class);
                           // produktAct.putExtra("kod", textView.getText());
                           //startActivity(produktAct)


                            qrCode = qrCodes.valueAt(0).displayValue;
                            textView.setText(qrCode);




                        }
                    });






                }


            }
        });
    }

}

