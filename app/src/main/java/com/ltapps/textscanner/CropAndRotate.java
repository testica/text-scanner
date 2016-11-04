package com.ltapps.textscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;


public class CropAndRotate extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener{
    private CropImageView mCropView;
    private Toolbar toolbar;
    private FloatingActionButton mFab;
    public static Bitmap croppedImage;
    private String message;
    Parcelable p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_and_rotate);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        mFab = (FloatingActionButton) findViewById(R.id.nextStep);
        mFab.setOnClickListener(this);
        mCropView.startLoad(
                Uri.parse(message)
                ,
                new LoadCallback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError() {}
                });

        mCropView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mFab.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        mFab.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextStep){
            mCropView.startCrop(null, new CropCallback() {
                @Override
                public void onSuccess(Bitmap cropped) {
                    croppedImage = cropped;
                    Intent intent = new Intent(CropAndRotate.this, Umbralization.class);
                    startActivity(intent);
                }

                @Override
                public void onError() {

                }
            }, new SaveCallback() {
                @Override
                public void onSuccess(Uri outputUri) {

                }

                @Override
                public void onError() {

                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rotate, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rotate_left:
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
                break;
            case R.id.rotate_right:
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                break;
        }
        return false;
    }
}
