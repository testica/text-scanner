package com.ltapps.textscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;


import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.GrayQuant;


public class Binarization extends AppCompatActivity implements View.OnClickListener, AppCompatSeekBar.OnSeekBarChangeListener {
    private ImageView img;
    private Toolbar toolbar;
    private AppCompatSeekBar seekBar;
    private Pix pix;
    private FloatingActionButton fab;
    public static Bitmap umbralization;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binarization);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img = (ImageView) findViewById(R.id.croppedImage);
        fab = (FloatingActionButton) findViewById(R.id.nextStep);
        fab.setOnClickListener(this);
        pix = com.googlecode.leptonica.android.ReadFile.readBitmap(CropAndRotate.croppedImage);

        OtsuThresholder otsuThresholder = new OtsuThresholder();
        int threshold = otsuThresholder.doThreshold(pix.getData());
        threshold += 20;
       //Log.i("Threshold", String.valueOf(threshold));
        umbralization = com.googlecode.leptonica.android.WriteFile.writeBitmap(GrayQuant.pixThresholdToBinary(pix,threshold));
        img.setImageBitmap(umbralization);
        seekBar = (AppCompatSeekBar) findViewById(R.id.umbralization);
        seekBar.setProgress(Integer.valueOf((50 * threshold)/254));
        seekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        //pix = GrayQuant.pixThresholdToBinary(pix,Integer.valueOf(((254 * i)/10)));
        //Log.i("Seek",Integer.valueOf(((254 * i)/10)).toString());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
       //Log.i("Seek",Integer.valueOf(((254 * seekBar.getProgress())/50)).toString());

        umbralization = com.googlecode.leptonica.android.WriteFile.writeBitmap(
                GrayQuant.pixThresholdToBinary(pix,Integer.valueOf(((254 * seekBar.getProgress())/50)))
        );
        img.setImageBitmap(umbralization);

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextStep) {
            Intent intent = new Intent(Binarization.this, Recognizer.class);
            startActivity(intent);
        }

    }
}
