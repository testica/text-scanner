package com.ltapps.textscanner;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Recognizer extends AppCompatActivity implements  Toolbar.OnMenuItemClickListener {
    private Toolbar toolbar;
    private TextView textView;
    ProgressDialog progressCopy, progressOcr;
    TessBaseAPI baseApi;
    AsyncTask<Void, Void, Void> copy = new copyTask();
    AsyncTask<Void, Void, Void> ocr = new ocrTask();

    private static final String DATA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.ltapps.textscanner/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recognizer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        textView = (TextView) findViewById(R.id.textExtracted);
        // Setting progress dialog for copy job.
        progressCopy = new ProgressDialog(Recognizer.this);
        progressCopy.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressCopy.setIndeterminate(true);
        progressCopy.setCancelable(false);
        progressCopy.setTitle("Dictionaries");
        progressCopy.setMessage("Copying dictionary files");
        // Setting progress dialog for ocr job.
        progressOcr = new ProgressDialog(this);
        progressOcr.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressOcr.setIndeterminate(true);
        progressOcr.setCancelable(false);
        progressOcr.setTitle("OCR");
        progressOcr.setMessage("Extracting text, please wait");

        copy.execute();
        ocr.execute();


    }

    private void recognizeText(){
        baseApi = new TessBaseAPI();
        baseApi.init(DATA_PATH, "eng",TessBaseAPI.OEM_TESSERACT_ONLY);
        baseApi.setImage(Binarization.umbralization);

    }


    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            String dirout= DATA_PATH + "tessdata/";
            File outFile = new File(dirout, filename);
            if(!outFile.exists()) {
                try {
                    in = assetManager.open(filename);
                    (new File(dirout)).mkdirs();
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                } catch (IOException e) {
                    Log.e("tag", "Error creating files", e);
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private class copyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressCopy.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressCopy.cancel();

        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("CopyTask","copying..");
            copyAssets();
            return null;
        }
    }

    private class ocrTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressOcr.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressOcr.cancel();
            textView.setText(baseApi.getUTF8Text());

        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("OCRTask","extracting..");
            recognizeText();
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.copy_text:
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextScanner", textView.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Recognizer.this,"Text has been copied to clipboard", Toast.LENGTH_LONG).show();
                break;
            case R.id.new_scan:
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
        return false;
    }
}
