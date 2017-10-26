package com.org.lv_crew.adblocker.adblockerforandroid;

import android.graphics.Color;
import android.os.StrictMode;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox cb=(CheckBox)findViewById(R.id.cbDefault);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);
    }

    public void clkRbStevenBlacks(View view) {
        RadioButton rb=(RadioButton) findViewById(R.id.rbHostsFileNet);
        rb.setChecked(false);

        CheckBox cb=(CheckBox)findViewById(R.id.cbPorn);
        cb.setClickable(true);
        cb.setTextColor(Color.BLACK);

        cb=(CheckBox)findViewById(R.id.cbGambling);
        cb.setClickable(true);
        cb.setTextColor(Color.BLACK);

        cb=(CheckBox)findViewById(R.id.cbFakeNews);
        cb.setClickable(true);
        cb.setTextColor(Color.BLACK);

        cb=(CheckBox)findViewById(R.id.cbSocial);
        cb.setClickable(true);
        cb.setTextColor(Color.BLACK);

    }

    public void clkRbHostsFileBlacks(View view) {
        RadioButton rb=(RadioButton) findViewById(R.id.rbStevenBlack);
        rb.setChecked(false);

        CheckBox cb=(CheckBox)findViewById(R.id.cbDefault);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

         cb=(CheckBox)findViewById(R.id.cbFakeNews);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

         cb=(CheckBox)findViewById(R.id.cbGambling);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

         cb=(CheckBox)findViewById(R.id.cbPorn);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

         cb=(CheckBox)findViewById(R.id.cbSocial);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);
    }

    public void clkUpdateHostsFile(View view) {
        String hostsfile="";
        HostsFileDownloader dlr=new HostsFileDownloader();
        ArrayList l=new ArrayList();
        HostsFileDownloader.StevenBlackSublist sl[] = {};

            RadioButton rb=(RadioButton) findViewById(R.id.rbStevenBlack);
        Toast.makeText( getApplicationContext(), "Hosts file downloading.", Toast.LENGTH_LONG).show();
        if(rb.isChecked())
        {
            CheckBox cb=(CheckBox)findViewById(R.id.cbSocial);
            if(cb.isChecked())
               hostsfile+=getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/social/hosts");


            cb=(CheckBox)findViewById(R.id.cbFakeNews);
            if(cb.isChecked())
                hostsfile+=getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/fakenews/hosts");

            cb=(CheckBox)findViewById(R.id.cbGambling);
            if(cb.isChecked())
                hostsfile+=getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/gambling/hosts");

            cb=(CheckBox)findViewById(R.id.cbPorn);
            if(cb.isChecked())
                hostsfile+=getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/porn/hosts");

            cb=(CheckBox)findViewById(R.id.cbDefault);
            if(cb.isChecked())
                hostsfile+=getURL("https://raw.githubusercontent.com/StevenBlack/hosts/master/hosts");
        }
        else
        {
            hostsfile=getURL("http://winhelp2002.mvps.org/hosts.txt");
        }

        try {
            Toast.makeText( getApplicationContext(), "Hosts file updat," +
                    "eing.", Toast.LENGTH_LONG).show();
            UpdateHostsFile uhf = new UpdateHostsFile(hostsfile);
            uhf.startUpdate(getApplicationContext());
        }catch (Exception ex)
        {
            Toast.makeText( getApplicationContext(), "Exception."+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText( getApplicationContext(), "Hosts file update finished.", Toast.LENGTH_LONG).show();
    }


    public static String getURL(String surl)
    {
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
            while ((line = reader.readLine()) != null) {
                fullString += line;
            }
            reader.close();
        }catch (Exception ex){
            //showDialog("Verbindungsfehler.",parent);
        }

        return fullString;
    }
}
