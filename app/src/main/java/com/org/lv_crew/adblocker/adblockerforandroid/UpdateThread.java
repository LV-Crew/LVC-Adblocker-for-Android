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


        cb = (CheckBox) act.findViewById(R.id.cbDefault);
        if (cb.isChecked())
            Runtime.getRuntime().exec(new String[]{"su", "-c", act.getApplicationContext().getCacheDir()+"/curl -o "+act.getApplicationContext().getCacheDir() + "/hosts.tmp.5 https://raw.githubusercontent.com/StevenBlack/hosts/master/hosts"});

        //Runtime.getRuntime().exec(new String[]{"su", "-c", " sed -i '$ a\´´' test cat  "+act.getApplicationContext().getCacheDir() + "/hosts.tmp.5 >> "+act.getApplicationContext().getCacheDir() + "/hosts.tmp"});

        String txt="#!/system/bin/sh\ncat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.1 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.2 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.3 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.4 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.5 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.6 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n"+
                "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.7 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp\n";

        File file = new File(act.getApplicationContext().getCacheDir()+"/copy.sh");
        file.createNewFile();
        FileOutputStream fOut = new FileOutputStream(file);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
        myOutWriter.append(txt);

        myOutWriter.close();

        fOut.flush();
        fOut.close();


/*
        p10=Runtime.getRuntime().exec( new String[]{"sh","-c", act.getApplicationContext().getCacheDir()+"/copy.sh"});
        p10.waitFor();
        p10=Runtime.getRuntime().exec( new String[]{"bash","-c", "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.2 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp"} );
        p10.waitFor();
        p10=Runtime.getRuntime().exec( new String[]{"bash","-c", "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.3 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp"} );
        p10.waitFor();
        p10=Runtime.getRuntime().exec( new String[]{"bash","-c", "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.4 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp"} );
        p10.waitFor();
        p10=Runtime.getRuntime().exec( new String[]{"bash","-c", "cat "+act.getApplicationContext().getCacheDir()+"/hosts.tmp.5 >> "+act.getApplicationContext().getCacheDir()+"/hosts.tmp"} );
        p10.waitFor();*/
        Process p4=Runtime.getRuntime().exec(new String[]{"su", "-c","cp "+act.getApplicationContext().getCacheDir() + "/hosts.tmp /system/etc/hosts"});
        p4.waitFor();
    } else {
        //Process p1=Runtime.getRuntime().exec(new String[]{"su", "-c", "wget -O - http://winhelp2002.mvps.org/hosts.txt >> "+ act.getApplicationContext().getCacheDir() + "//hosts.tmp"});

        //p3.waitFor();

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
        act.updateStatusText("Status: idle\nFile size: "+Long.toString((f).getTotalSpace()/1024)+" KBytes");
/*
        BufferedReader br = new BufferedReader(new FileReader(act.getApplicationContext().getCacheDir()+"/hosts.tmp"));
        String line;
        int lineCount = 0;
        while ((line = br.readLine()) != null) {
            lineCount++;
        }

        act.updateStatusText("Status: idle\nEntries downloaded: "+Integer.toString(lineCount));*/
        //Runtime.getRuntime().exec(new String[]{"su", "-c", "wget http://winhelp2002.mvps.org/hosts.txt -O - > " + act.getApplicationContext().getCacheDir() + "//hosts.tmp"});
        //UpdateHostsFile uhf = new UpdateHostsFile(hostsfile);
        //uhf.startUpdate(act.getApplicationContext());
    } catch (Exception ex) {
        Toast.makeText(act.getApplicationContext(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    }
    Looper.prepare();

    act.setText();
    //((TextView)act.findViewById(R.id.txtStatus)).setText("Status: idle");
    Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o ro,remount /system"});
}catch(Exception e){}

                }

    private static String getURL(String surl,MainActivity act) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.setProperty("java.net.preferIPv4Addresses", "true");
        System.setProperty("java.net.preferIPv6Addresses", "false");
        System.setProperty("validated.ipv6", "false");
        String fullString = "";
        try {

            URL url = new URL(surl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            long count=0;
            while ((line = reader.readLine()) != null) {
                fullString += line+"\n";
             /*   if(count%1000==0)
                    act.updateStatusText("Status: updating...\nLines done: "+count);
                count++;*/
            }
            reader.close();
        } catch (Exception ex) {
            //showDialog("Verbindungsfehler.",parent);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(act);
            builder1.setMessage("Error: "+ex.getMessage());
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        return fullString;
    }


        }

