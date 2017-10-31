package com.org.lv_crew.adblocker.adblockerforandroid;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private MainActivity self;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox cb = (CheckBox) findViewById(R.id.cbDefault);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);
        if(!isRootGiven())
        {
            showDialog("Device not rootet. Root required.",this);
        }

        cb = (CheckBox) findViewById(R.id.cbPorn);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

        cb = (CheckBox) findViewById(R.id.cbGambling);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

        cb = (CheckBox) findViewById(R.id.cbFakeNews);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

        cb = (CheckBox) findViewById(R.id.cbSocial);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);


    try {
        Process p3=Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o rw,remount /system"});
        p3.waitFor();
        InputStream is = getAssets().open("curl");
        File file = new File(getApplicationContext().getCacheDir()+"/curl");
        file.createNewFile();
        OutputStream output = new FileOutputStream(file);
        byte[] buffer = new byte[4 * 1024]; // or other buffer size
        int read;

        while ((read = is.read(buffer)) != -1) {
            output.write(buffer, 0, read);
        }

        output.flush();
        output.close();
        Process p4=Runtime.getRuntime().exec(new String[] { "su", "-c", "chmod 777 "+getApplicationContext().getCacheDir()+"/curl"});
        }catch(Exception e){}

        readPrefs();

        self=this;

        MobileAds.initialize(this,"ca-app-pub-8348435662770618~7548201316");
        AdRequest.Builder adBuilder = new AdRequest.Builder();
        adBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Context ctx=getApplicationContext();
                Toast toast;
                if(errorCode==AdRequest.ERROR_CODE_NETWORK_ERROR)
                    Toast.makeText(ctx, "can't load ad- network error", Toast.LENGTH_SHORT).show();
                else if(errorCode==AdRequest.ERROR_CODE_INVALID_REQUEST)
                    Toast.makeText(ctx, "can't load ad - invalid requiest", Toast.LENGTH_SHORT).show();
                else if(errorCode==AdRequest.ERROR_CODE_INTERNAL_ERROR)
                    Toast.makeText(ctx, "can't load ad - internal error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.i("Ads", "onAdClosed");
            }
        });
        mInterstitialAd.setAdUnitId("ca-app-pub-8348435662770618/2842669540");
        mInterstitialAd.loadAd( adBuilder.build());
        setHostsFileStatus();
    }

    public static final String PREFS_NAME = "MyPrefsFile";
    private void readPrefs()
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        ((CheckBox)findViewById(R.id.rbStevenBlack)).setChecked(settings.getBoolean("StevenBlacks", false));
        ((CheckBox)findViewById(R.id.cbPorn)).setChecked(settings.getBoolean("StevenBlacksPorn", false));
        ((CheckBox)findViewById(R.id.cbSocial)).setChecked(settings.getBoolean("StevenBlacksSocial", false));
        ((CheckBox)findViewById(R.id.cbFakeNews)).setChecked(settings.getBoolean("StevenBlacksFakeNews", false));
        ((CheckBox)findViewById(R.id.cbGambling)).setChecked(settings.getBoolean("StevenBlacksGambling", false));
        ((CheckBox)findViewById(R.id.rbHostsFileNet)).setChecked(settings.getBoolean("HostsFileNet", true));

        if(settings.getBoolean("StevenBlacks", false))
        {
            ((CheckBox)findViewById(R.id.cbSocial)).setTextColor(Color.BLACK);
            ((CheckBox)findViewById(R.id.cbSocial)).setEnabled(true);

            ((CheckBox)findViewById(R.id.cbFakeNews)).setTextColor(Color.BLACK);
            ((CheckBox)findViewById(R.id.cbFakeNews)).setEnabled(true);

            ((CheckBox)findViewById(R.id.cbGambling)).setTextColor(Color.BLACK);
            ((CheckBox)findViewById(R.id.cbGambling)).setEnabled(true);

            ((CheckBox)findViewById(R.id.cbPorn)).setTextColor(Color.BLACK);
            ((CheckBox)findViewById(R.id.cbPorn)).setEnabled(true);
        }
    }

    private void writePrefs()
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("StevenBlacks",((CheckBox)findViewById(R.id.rbStevenBlack)).isChecked());
        editor.putBoolean("StevenBlacksGambling",((CheckBox)findViewById(R.id.cbGambling)).isChecked());
        editor.putBoolean("StevenBlacksPorn",((CheckBox)findViewById(R.id.cbPorn)).isChecked());
        editor.putBoolean("StevenBlacksFakeNews",((CheckBox)findViewById(R.id.cbFakeNews)).isChecked());
        editor.putBoolean("StevenBlacksSocial",((CheckBox)findViewById(R.id.cbSocial)).isChecked());
        editor.putBoolean("HostsFileNet",((CheckBox)findViewById(R.id.rbHostsFileNet)).isChecked());
        editor.commit();
    }



    public void clkRbStevenBlacks(View view) {

        if(((CheckBox)findViewById(R.id.rbStevenBlack)).isChecked()) {
            CheckBox cb = (CheckBox) findViewById(R.id.cbPorn);
            cb.setClickable(true);
            cb.setTextColor(Color.BLACK);

            cb = (CheckBox) findViewById(R.id.cbGambling);
            cb.setClickable(true);
            cb.setTextColor(Color.BLACK);

            cb = (CheckBox) findViewById(R.id.cbFakeNews);
            cb.setClickable(true);
            cb.setTextColor(Color.BLACK);

            cb = (CheckBox) findViewById(R.id.cbSocial);
            cb.setClickable(true);
            cb.setTextColor(Color.BLACK);
        }
        else {
            CheckBox cb = (CheckBox) findViewById(R.id.cbDefault);
            cb.setClickable(false);
            cb.setTextColor(Color.GRAY);

            cb = (CheckBox) findViewById(R.id.cbFakeNews);
            cb.setClickable(false);
            cb.setTextColor(Color.GRAY);

            cb = (CheckBox) findViewById(R.id.cbGambling);
            cb.setClickable(false);
            cb.setTextColor(Color.GRAY);

            cb = (CheckBox) findViewById(R.id.cbPorn);
            cb.setClickable(false);
            cb.setTextColor(Color.GRAY);

            cb = (CheckBox) findViewById(R.id.cbSocial);
            cb.setClickable(false);
            cb.setTextColor(Color.GRAY);
        }
    }

    public void clkEditHostsFile(View view)
    {
        File f=new File("/system/etc/hosts");
        if((f.length()/1024)<2000) {
            //Read text from file
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
                //You'll need to add proper error handling here
            }
            setContentView(R.layout.activity_editor);
            ((TextView) findViewById(R.id.txtEditor)).setText(text);
        }
        else
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Hosts file too large for editor. Can only edit hosts files < 2MB.");
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
    }


    public void clkEditorSpeichern(View view)
    {

        String txt=((TextView)findViewById(R.id.txtEditor)).getText().toString();
        try {
            Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o remount,rw /system"});
            UpdateHostsFile uhf = new UpdateHostsFile(txt);
            uhf.startUpdate(getApplicationContext());
            setContentView(R.layout.activity_main);
            readPrefs();
            Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o remount,ro /system"});
        }catch (Exception e){Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(),Toast.LENGTH_LONG).show();}
    }

    public void clkRbHostsFileBlacks(View view) {


        CheckBox cb = (CheckBox) findViewById(R.id.cbDefault);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

        cb = (CheckBox) findViewById(R.id.cbFakeNews);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

        cb = (CheckBox) findViewById(R.id.cbGambling);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

        cb = (CheckBox) findViewById(R.id.cbPorn);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);

        cb = (CheckBox) findViewById(R.id.cbSocial);
        cb.setClickable(false);
        cb.setTextColor(Color.GRAY);
    }
    class mRunnable implements Runnable
    {
        public MainActivity act=null;
        public String text="";
        mRunnable(MainActivity act1,String _text)
        {
            text=_text;
            act=act1;
        }

        mRunnable(MainActivity act1)
        {
            act=act1;
        }

        @Override
        public void run() {}
    }

    public void updateStatusText(String txt) {
        runOnUiThread(new mRunnable(this, txt) {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.txtStatus)).setText(text);
            }
        });
    }
    public void setText(){
        runOnUiThread(new mRunnable(this) {
            @Override
            public void run() {
                ((Button)findViewById(R.id.bnEditHostsFile)).setEnabled(true);
                ((Button)findViewById(R.id.bnResetHostsFile)).setEnabled(true);
                ((Button)findViewById(R.id.bnUpdateHostsFile)).setEnabled(true);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(act);
                builder1.setMessage("Hosts file update complete.\nYou are now protected from Ads.");
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
        });
    }



    public void clkUpdateHostsFile(View view) {
        writePrefs();

        ArrayList l = new ArrayList();

        ((Button)findViewById(R.id.bnEditHostsFile)).setEnabled(false);
        ((Button)findViewById(R.id.bnResetHostsFile)).setEnabled(false);
        ((Button)findViewById(R.id.bnUpdateHostsFile)).setEnabled(false);
        ((TextView)findViewById(R.id.txtStatus)).setText("Status: updating...");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Download starting.");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                   doUpdate();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

private void doUpdate()
{
    UpdateThread t=new UpdateThread(this);
    t.start();
}
    public static String getURL(String surl) {
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
        } catch (Exception ex) {
            //showDialog("Verbindungsfehler.",parent);
        }

        return fullString;
    }

    public void showDialog(String data,MainActivity parent) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(parent);
        builder1.setMessage(data);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reset();
                        dialog.cancel();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public static boolean isRootAvailable(){
        for(String pathDir : System.getenv("PATH").split(":")){
            if(new File(pathDir, "su").exists()) {
                return true;
            }
        }
        return false;
    }
    public void reset()
    {
        finish();
        System.exit(0);

    }    public static boolean isRootGiven(){
        if (isRootAvailable()) {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(new String[]{"su", "-c", "id"});
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String output = in.readLine();
                if (output != null && output.toLowerCase().contains("uid=0"))
                    return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (process != null)
                    process.destroy();
            }
        }

        return false;
    }


    public void clkReseteHostsFile(View view) {
        try {

            ArrayList l = new ArrayList();




            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Download starting.");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        try{
                            UpdateHostsFile uhf = new UpdateHostsFile("127.0.0.1 localhost\n");
                            uhf.startUpdate(getApplicationContext());
                            uhf = new UpdateHostsFile("127.0.0.1 localhost\n");
                            uhf.startUpdate(getApplicationContext());
                            uhf.startUpdate(getApplicationContext());
                            uhf.startUpdate(getApplicationContext());
                            setHostsFileStatus();
                        } catch(
                        IOException e)

                        {
                            e.printStackTrace();
                        }
                    }});
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }catch (Exception ex){}
    }
    private void setHostsFileStatus()
    {
        File f=new File("/etc/hosts");
        ((TextView)findViewById(R.id.txtStatus)).setText("Status: idle\nhosts file size: "+Long.toString(((f.length()/1024)<1&&(f.length()>1))?1:f.length()/1024)+" KByte");
    }
}

