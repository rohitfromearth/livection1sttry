package com.example.livection1sttry;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class Dir_serve {
    public void letshit(){

        final Calendar c = Calendar.getInstance();
        String dte = String.valueOf(c.get(Calendar.DATE));
        String mnt = String.valueOf(c.get(Calendar.MONTH));
        String yer = String.valueOf(c.get(Calendar.YEAR));
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
       // String dir =getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
//        String fldr_nme= dte+"-"+mnt+"-"+yer;
        String fldr_nme= "lifeaction";
        File f = new File(dir,fldr_nme);

        if (!f.exists()){
            f.mkdir();
        }
    }
}
