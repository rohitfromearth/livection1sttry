package com.example.livection1sttry;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import androidx.core.accessibilityservice.AccessibilityServiceInfoCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class accessibiltyserve extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";
    TextView number;

    Handler handler = new Handler();
    Runnable runnable;
    int serverResponseCode = 0;
    String upLoadServerUri = null;

    int delay = 5000;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        String str1 = String.valueOf(event.getEventType());


        String str5 = String.valueOf(AccessibilityEvent.WINDOWS_CHANGE_TITLE);

        String str6 = String.valueOf(event.getClassName());


        String str8 = String.valueOf(event.getContentDescription());


        final Calendar c = Calendar.getInstance();
        String dte = String.valueOf(c.get(Calendar.DATE));
        String mnt = String.valueOf(c.get(Calendar.MONTH) + 1);
        String yer = String.valueOf(c.get(Calendar.YEAR));
        String sec = String.valueOf(c.get(Calendar.SECOND));
        String min = String.valueOf(c.get(Calendar.MINUTE));
        String hr = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mili = String.valueOf(c.get(Calendar.MILLISECOND));


        String str16 = String.valueOf(event.getPackageName());


        String str22 = String.valueOf(event.describeContents());

        String str23 = String.valueOf(event.getText());


        //////////////////////////////////

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s2 = sh.getString("Userid", "");

        Log.e("Resid", s2);
        //////////////////////////////////////////////////////////////////////////////////////////

        Log.e(TAG, str1);
        Log.e(TAG, str5);
        Log.e(TAG, str6);
        Log.e(TAG, str8);
        Log.e(TAG, str16);
        Log.e(TAG, str22);
        Log.e(TAG, str23);
        String must_dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator +"lifeaction";
         String fldr_nme= dte+"-"+mnt+"-"+yer;
//        String dir =getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)+"/"+"lifection";
       Log.e("dirr",dir);
        String str_tym = yer + "-" + mnt + "-" + dte + ":" + hr + ":" + min + ":" + sec + ":" + mili;
        Log.e(TAG, str_tym);


        try {

            FileWriter wrt = new FileWriter(dir + File.separator + dte + "_" + mnt + "_" + yer + "_" + s2 + "_" + hr +"_"+ min + ".txt", true);
Log.e("still","here");

            wrt.append("~NewEvent:" + "event_info^" + str16 + "*" + str6 + "*" + str5 + "*" + str1 + "*" + str22 + "*" + "^text^" + str23 + "^description^" + str8 + "^event_time^" + str_tym);
            wrt.close();

        } catch (IOException e) {
            e.printStackTrace();
Log.e("exp", String.valueOf(e));
        }
/////////////////////////////
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (!(netInfo == null)){
Log.e("nice","net connected");
//            File f = new File(must_dir, dte + "-" + mnt + "-" + yer);

            File f = new File(must_dir,"lifeaction");
           try{File[] file= f.listFiles();
            Log.e("lenn", String.valueOf(file.length));
            for(int i = 0; i < file.length; i++){
                String uploadFileName = file[i].getName();
               Log.e("file nam ", uploadFileName);
        String fnm=dte + "_" + mnt + "_" + yer + "_" + s2 + "_" + hr +"_"+min + ".txt";
            //    20_12_2022_62_157.txt
if (!uploadFileName.equals(fnm)){


    //      uploadFile(dir + "/" + uploadFileName);
    String file_absol = dir + File.separator+ uploadFileName;

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    StrictMode.setThreadPolicy(policy);
//                    https://perfect-eel-fashion.cyclic.app
   upLoadServerUri = "https://perfect-eel-fashion.cyclic.app/rawData/writeFile"; //original dont touch
    //upLoadServerUri = "https://689e-122-169-93-39.in.ngrok.io/rawData/writeFile";//temp


    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;
    File sourceFile = new File(file_absol);

    if (!sourceFile.isFile()) {


        Log.e("uploadFile", "Source File not exist :"
                + dir + "" + uploadFileName);

    } else {
        try {
            Log.e("conn", "reched here");

            Log.e("read", String.valueOf(sourceFile));
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("file", file_absol);
            conn.setRequestProperty("ultra", "45");///new
            conn.setRequestProperty("userId", s2);//// userid



            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
                    + file_absol + "\"" + lineEnd);
            Log.e("f_name", file_absol);
            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();
            // create a buffer of  maximum size


            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();

            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFileress", "HTTP Response is : "
                    + serverResponseMessage + ": " + serverResponseCode);


            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();
            if(serverResponseCode==200){
                sourceFile.delete();
            }


        } catch (MalformedURLException ex) {


            ex.printStackTrace();


            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {


            e.printStackTrace();


            Log.e(" server Exception", "Exception : "
                    + e.getMessage(), e);
        }

        Log.e("delta", file_absol);

//                sourceFile.delete();
    }

}

 // End else block



            }
           }
           catch(Exception e){
               Log.e(" server", "Exception : "
                       + e.getMessage(), e);
           }
            Log.e("dgre","");
//            if (!(file.length>1)){
//                for (int i = 0; i < file.length; i++) {
//
//
//
//
//                }
//            }




        }





        //////////////////////////////////////


        AccessibilityNodeInfo source = event.getSource();
        if (source != null) {
            Log.d(TAG, "onAccessibilityEvent:event.getSource():-  " + source);
            AccessibilityNodeInfo rowNode = AccessibilityNodeInfo.obtain(source);
            Log.d(TAG, "onAccessibilityEvent: rowNode :- " + rowNode);
        }
    }


    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt: Something went wrong" );
    }
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();

        info.eventTypes = AccessibilityEvent.TYPE_WINDOWS_CHANGED|AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED|AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS|
                AccessibilityServiceInfo.CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT|AccessibilityServiceInfo.FEEDBACK_VISUAL|
                AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS|AccessibilityEvent.WINDOWS_CHANGE_TITLE|
                AccessibilityEvent.WINDOWS_CHANGE_ADDED|AccessibilityEvent.WINDOWS_CHANGE_CHILDREN|
                AccessibilityServiceInfo.CONTENTS_FILE_DESCRIPTOR;//giving

        info.feedbackType = AccessibilityServiceInfoCompat.FEEDBACK_ALL_MASK;
        info.flags= AccessibilityServiceInfoCompat.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        info.packageNames = new String[]
          {"com.samsung.android.dialer","com.sec.android.app.launcher","com.android.settings","com.android.systemui","com.android.chrome","com.amazon.avod.thirdpartyclient"," com.spotify.music","com.netflix.mediaclient", "in.startv.hotstar","com.google.android.youtube","com.graymatrix.did","com.jio.media.jiobeats"};

        info.notificationTimeout = 100;

        this.setServiceInfo(info);
        Log.e(TAG, "onServiceConnected: ");
    }

}
