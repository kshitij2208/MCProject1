package com.ksapps.mcproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private VideoView myVideoView;
    private MediaController mediaControls;
    private Button button,button2,button3;
    private Spinner spinner;
    private TextView textView;
    private HashMap<String,String> hashMap;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    private String[] arr;
    private int praticeNum = 1;
    private String selectedPath;
    private File folder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }

        hashMap=new HashMap<>();
        hashMap.put("buy","https://www.signingsavvy.com/media/mp4-ld/6/6442.mp4");
        hashMap.put("house","https://www.signingsavvy.com/media/mp4-ld/23/23234.mp4");
        hashMap.put("fun","https://www.signingsavvy.com/media/mp4-ld/22/22976.mp4");
        hashMap.put("hope","https://www.signingsavvy.com/media/mp4-ld/22/22197.mp4");
        hashMap.put("arrive","https://www.signingsavvy.com/media/mp4-ld/26/26971.mp4");
        hashMap.put("really","https://www.signingsavvy.com/media/mp4-ld/24/24977.mp4");
        hashMap.put("read","https://www.signingsavvy.com/media/mp4-ld/7/7042.mp4");
        hashMap.put("lip","https://www.signingsavvy.com/media/mp4-ld/26/26085.mp4");
        hashMap.put("mouth","https://www.signingsavvy.com/media/mp4-ld/22/22188.mp4");
        hashMap.put("some","https://www.signingsavvy.com/media/mp4-ld/23/23931.mp4");
        hashMap.put("communicate","https://www.signingsavvy.com/media/mp4-ld/22/22897.mp4");
        hashMap.put("write","https://www.signingsavvy.com/media/mp4-ld/27/27923.mp4");
        hashMap.put("create","https://www.signingsavvy.com/media/mp4-ld/22/22337.mp4");
        hashMap.put("pretend","https://www.signingsavvy.com/media/mp4-ld/25/25901.mp4");
        hashMap.put("sister","https://www.signingsavvy.com/media/mp4-ld/21/21587.mp4");
        hashMap.put("man","https://www.signingsavvy.com/media/mp4-ld/21/21568.mp4");
        hashMap.put("one","https://www.signingsavvy.com/media/mp4-ld/26/26492.mp4");
        hashMap.put("drive","https://www.signingsavvy.com/media/mp4-ld/23/23918.mp4");
        hashMap.put("perfect","https://www.signingsavvy.com/media/mp4-ld/24/24791.mp4");
        hashMap.put("mother","https://www.signingsavvy.com/media/mp4-ld/21/21571.mp4");
        arr =  hashMap.values().toArray(new String[0]);
        //initialize the VideoView
        myVideoView = (VideoView) findViewById(R.id.video_view);
        mediaControls = new MediaController(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        textView = (TextView) findViewById(R.id.text);

        File file = new File("/sdcard/MCProject1/buy.mp4");
        folder = new File(Environment.getExternalStorageDirectory() + File.separator + "MCProject1");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (file.isFile()) {
        }else{
            new DownloadFileFromURL().execute(arr);
        }
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praticeNum++;
                uploadVideo();
                button3.setVisibility(View.INVISIBLE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                button3.setVisibility(View.INVISIBLE);
                button.setText("Capture Video");
                button2.setVisibility(View.INVISIBLE);

                try {
                    //set the media controller in the VideoView
                    myVideoView.setMediaController(mediaControls);
                    Uri uri = Uri.parse(Environment.getExternalStorageDirectory() +"/MCProject1/"+spinner.getSelectedItem().toString()+".mp4");
                    myVideoView.setVideoURI(uri);
                    myVideoView.start();
                    mediaControls.setMediaPlayer(myVideoView);
                    myVideoView.setMediaController(mediaControls);
                    myVideoView.requestFocus();

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                folder = new File(Environment.getExternalStorageDirectory() + "/MCProject1/PracticeVideos/");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                folder = new File(Environment.getExternalStorageDirectory() + "/MCProject1/PracticeVideos/"+spinner.getSelectedItem().toString().toUpperCase()+"_PRATICE_"+(praticeNum)+"_SUTAR.mp4");

                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
                takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(folder));

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gesture, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                button.setText("Capture Video");
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                if(parent.getSelectedItem().toString().equals("Select an Gesture Option")){
                    myVideoView.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                }else{
                    try {
                        //set the media controller in the VideoView
                        myVideoView.setVisibility(View.VISIBLE);
                        button.setVisibility(View.VISIBLE);
                        myVideoView.setMediaController(mediaControls);
                        Uri uri = Uri.parse(Environment.getExternalStorageDirectory() +"/MCProject1/"+parent.getSelectedItem().toString()+".mp4");
                        myVideoView.setVideoURI(uri);
                        myVideoView.start();
                        mediaControls.setMediaPlayer(myVideoView);
                        myVideoView.setMediaController(mediaControls);
                        myVideoView.requestFocus();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String getKey(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            myVideoView.setVideoURI(videoUri);
            myVideoView.start();
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button.setText("Record Again");

            selectedPath= videoUri.getPath();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> implements com.ksapps.mcproject1.DownloadFileFromURL {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                for (int i = 0; i < f_url.length; i++) {
                    URL url = new URL(f_url[i]);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    int lenghtOfFile = conection.getContentLength();

                    InputStream input = new BufferedInputStream(
                            url.openStream(), 8192);
                    System.out.println("Data::" + getKey(hashMap, f_url[i]));
                    getKey(hashMap, f_url[i]);
                    OutputStream output = new FileOutputStream(
                            "/sdcard/MCProject1/" + getKey(hashMap, f_url[i]) + ".mp4");

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        this.publishProgress(""+(int)(total * 100)/lenghtOfFile);

                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }
            } catch (Exception e) {
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Integer... progress) {
            pDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String file_url) {
            removeDialog(progress_bar_type);
        }

    }

    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(MainActivity.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                textView.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                textView.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.uploadVideo(selectedPath);
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();

    }

}
