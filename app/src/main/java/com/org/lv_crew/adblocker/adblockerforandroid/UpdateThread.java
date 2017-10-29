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
import java.io.InputStreamReader;
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

                RadioButton rb = (RadioButton) act.findViewById(R.id.rbStevenBlack);
                String hostsfile="";
                if (rb.isChecked()) {
                    CheckBox cb = (CheckBox) act.findViewById(R.id.cbSocial);
                    if (cb.isChecked())
                        hostsfile += getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/social/hosts",act);


                    cb = (CheckBox) act.findViewById(R.id.cbFakeNews);
                    if (cb.isChecked())
                        hostsfile += getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/fakenews/hosts",act);

                    cb = (CheckBox) act.findViewById(R.id.cbGambling);
                    if (cb.isChecked())
                        hostsfile += getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/gambling/hosts",act);

                    cb = (CheckBox) act.findViewById(R.id.cbPorn);
                    if (cb.isChecked())
                        hostsfile += getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/porn/hosts",act);

                    cb = (CheckBox) act.findViewById(R.id.cbDefault);
                    if (cb.isChecked())
                        hostsfile += getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/hosts",act);
                } else {
                    hostsfile = getURL("http://winhelp2002.mvps.org/hosts.txt",act);
                }

                try {
                    UpdateHostsFile uhf = new UpdateHostsFile(hostsfile);
                    uhf.startUpdate(act.getApplicationContext());
                } catch (Exception ex) {
                    Toast.makeText(act.getApplicationContext(),"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            Looper.prepare();

            act.setText();
            //((TextView)act.findViewById(R.id.txtStatus)).setText("Status: idle");

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
                act.updateStatusText("Status: updating...\nLines done: "+count);
                count++;
            }
            reader.close();
        } catch (Exception ex) {
            //showDialog("Verbindungsfehler.",parent);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(act);
            builder1.setMessage("Error: "+ex.getMessage());
            builder1.setCancelable(true);

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

