package com.org.lv_crew.adblocker.adblockerforandroid;

import android.content.DialogInterface;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

/**
 * Created by denni on 29.10.2017.
 */

public class UpdateThread extends Thread {
        public MainActivity act=null;
        UpdateThread(MainActivity _act) {
            act=_act;
        }

        public void run() {
        try {
    Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o rw,remount /system"});
    CheckBox rb = (CheckBox) act.findViewById(R.id.rbStevenBlack);
    String hostsfile = "";
    Process p3=Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o rw,remount /system"});
    p3.waitFor();
    Process px=Runtime.getRuntime().exec(new String[]{"su", "-c", "rm "+ act.getApplicationContext().getCacheDir()+"//hosts.tmp.*"});
    Process p=Runtime.getRuntime().exec(new String[]{"su", "-c", "rm "+ act.getApplicationContext().getCacheDir()+"//hosts.tmp"});
    p.waitFor();
    p=Runtime.getRuntime().exec(new String[]{"su", "-c", "touch "+ act.getApplicationContext().getCacheDir()+"//hosts.tmp"});
    p.waitFor();
    if (rb.isChecked()) {
        CheckBox cb = (CheckBox) act.findViewById(R.id.cbSocial);
        if (cb.isChecked())
        Runtime.getRuntime().exec(new String[]{"su", "-c", act.getApplicationContext().getCacheDir()+"/curl -o "+act.getApplicationContext().getCacheDir() + "/hosts.tmp.1 https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/social/hosts"});

        cb = (CheckBox) act.findViewById(R.id.cbFakeNews);
        if (cb.isChecked())
        Runtime.getRuntime().exec(new String[]{"su", "-c", act.getApplicationContext().getCacheDir()+"/curl -o "+act.getApplicationContext().getCacheDir() + "/hosts.tmp.2 https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/fakenews/hosts"});

        cb = (CheckBox) act.findViewById(R.id.cbGambling);
        if (cb.isChecked())
        Runtime.getRuntime().exec(new String[]{"su", "-c", act.getApplicationContext().getCacheDir()+"/curl -o "+act.getApplicationContext().getCacheDir() + "/hosts.tmp.3 https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/gambling/hosts"});

        cb = (CheckBox) act.findViewById(R.id.cbPorn);
        if (cb.isChecked())
        Runtime.getRuntime().exec(new String[]{"su", "-c", act.getApplicationContext().getCacheDir()+"/curl -o "+act.getApplicationContext().getCacheDir() + "/hosts.tmp.4 https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/porn/sinfonietta/hosts"});

        Process pz=Runtime.getRuntime().exec(new String[]{"su", "-c", act.getApplicationContext().getCacheDir() + "/curl -o " + act.getApplicationContext().getCacheDir() + "/hosts.tmp.5 https://raw.githubusercontent.com/StevenBlack/hosts/master/hosts"});
        pz.waitFor();

        String txt="#!/system/bin/sh\ncat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.1 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.2 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.3 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.4 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.5 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.6 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.7 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.8 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n";

        File file = new File(act.getApplicationContext().getCacheDir()+"/copy.sh");
        file.createNewFile();
        FileOutputStream fOut = new FileOutputStream(file);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
        myOutWriter.append(txt);

        myOutWriter.close();

        fOut.flush();
        fOut.close();

        Process p4=Runtime.getRuntime().exec(new String[]{"su", "-c","cp "+act.getApplicationContext().getCacheDir() + "/hosts.tmp /system/etc/hosts"});
        p4.waitFor();
    }
    rb = (CheckBox) act.findViewById(R.id.rbHostsFileNet);
    if(rb.isChecked()){
        Process p1=Runtime.getRuntime().exec(new String[]{"su", "-c", act.getApplicationContext().getCacheDir()+"/curl -o "+act.getApplicationContext().getCacheDir() + "/hosts.tmp.8 https://raw.githubusercontent.com/AdAway/adaway.github.io/master/hosts.txt"});
        p1.waitFor();
        Process p2=Runtime.getRuntime().exec(new String[]{"su", "-c", act.getApplicationContext().getCacheDir()+"/curl -o "+act.getApplicationContext().getCacheDir() + "/hosts.tmp.6 https://hosts-file.net/download/hosts.txt"});
        p2.waitFor();
        p2=Runtime.getRuntime().exec(new String[]{"su", "-c", act.getApplicationContext().getCacheDir()+"/curl -o "+act.getApplicationContext().getCacheDir() + "/hosts.tmp.7 https://hosts-file.net/hphosts-partial.txt"});
        p2.waitFor();
    }

    try {
        Process p10=Runtime.getRuntime().exec(new String[]{"su", "-c", "chmod 777 "+act.getApplicationContext().getCacheDir()+"/copy.sh"});
        p10.waitFor();
        ProcessBuilder builder = new ProcessBuilder("su", "-c",act.getApplicationContext().getCacheDir()+"/copy.sh");
        Process px1 = builder.start(); // may throw IOException
        px1.waitFor();
        Process p4=Runtime.getRuntime().exec(new String[]{"su", "-c","cp "+act.getApplicationContext().getCacheDir() + "/hosts.tmp /system/etc/hosts"});
        p4.waitFor();

        File f=new File(act.getApplicationContext().getCacheDir()+"/hosts.tmp");
        act.updateStatusText("Status: idle\nFile size: "+Long.toString((f).length()/1024)+" KBytes");

    } catch (Exception ex) {
        Toast.makeText(act.getApplicationContext(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    }
    Looper.prepare();
    act.setText();
    Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o ro,remount /system"});
}catch(Exception e){
        }
}
}

