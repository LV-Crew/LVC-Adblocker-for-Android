package com.org.lv_crew.adblocker.adblockerforandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by denni on 24.10.2017.
 */

public class HostsFileDownloader {

    public enum FileType{
        FILE_TYPE_STEVENBLACK,
        FILE_TYPE_HOSTSFILE
    }

    public enum StevenBlackSublist{
        SL_PORN,
        SL_FAKENEWS,
        SL_GAMBLING,
        SL_SOCIAL,
        SL_DEFAULT
    }

    public String getHostsFile(FileType type ) {
        StevenBlackSublist sl[] = {StevenBlackSublist.SL_DEFAULT};
        String ret=getHostsFile(type,sl);
        return ret;
    }

    public String getHostsFile(FileType type,  StevenBlackSublist[] sl)
    {
        String ret="";
        try {
            if (type == FileType.FILE_TYPE_HOSTSFILE)
                ret+= downloadString("http://winhelp2002.mvps.org/hosts.txt");
            else
                ret+=downloadString("https://raw.githubusercontent.com/StevenBlack/hosts/master/hosts");

            if(sl.length>1)
            {
                for (StevenBlackSublist sli:sl) {
                    if(sli==StevenBlackSublist.SL_FAKENEWS)
                    {
                        ret+=downloadString("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/fakenews/hosts");
                    }
                    if(sli==StevenBlackSublist.SL_GAMBLING)
                    {
                        ret+=downloadString("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/gambling/hosts");
                    }
                    if(sli==StevenBlackSublist.SL_PORN)
                    {
                        ret+=downloadString("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/porn/hosts");
                    }
                    if(sli==StevenBlackSublist.SL_SOCIAL)
                    {
                        ret+=downloadString("https://raw.githubusercontent.com/StevenBlack/hosts/master/extensions/social/hosts");
                    }
                }
            }

        }catch(Exception ex){
            String a=ex.getMessage();
        }
        return ret;
    }




    private String downloadString(String _url) throws IOException {
        URL url = new URL(_url);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        String ret="";
        while ((str = in.readLine()) != null)
        {
            ret+=str+"\r\n";
        }
        in.close();
        return ret;
    }
}
