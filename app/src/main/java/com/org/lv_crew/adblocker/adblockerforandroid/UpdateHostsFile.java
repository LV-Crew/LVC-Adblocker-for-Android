package com.org.lv_crew.adblocker.adblockerforandroid;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.R.attr.category;
import static android.R.attr.data;

/**
 * Created by denni on 24.10.2017.
 */

public class UpdateHostsFile {
    private String _text;
    UpdateHostsFile(String text) throws IOException {
        _text=text;
    }

    public void startUpdate(Context context)
    {
        try {


        }catch(Exception ex) {
            Toast.makeText( context, "Exception."+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            setPermissions();
            setText(context);


            resetPermissions();
        }catch(IOException e){
            Toast.makeText( context, "Exception."+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void setText(Context context) throws IOException {
        //FileOutputStream fs=new FileOutputStream(new File("/system/etc/hosts"));
        //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("/system/etc/hosts", Context.MODE_APPEND));
        File outputDir = context.getCacheDir();
        FileOutputStream fs=new FileOutputStream (new File( outputDir+"/hosts.tmp")); // true will be same as Context.MODE_APPEND
        ;
        fs.write(_text.getBytes());
        fs.close();

        Runtime.getRuntime().exec(new String[] { "su", "-c", "cp "+outputDir+"/hosts.tmp"+" /system/etc/hosts"});
    }

    private void setPermissions() throws IOException {
        Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o remount,rw /system"});


    }

    private void resetPermissions() throws IOException {
        Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o remount,ro /system"});
    }

}
