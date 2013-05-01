/*
 * --------------------------------------------------------------------------
 * OpenURL.java
 * --------------------------------------------------------------------------
 * Description:       Class for opening an URL on the browser
 * Developer:         FG
 * Created:           08 February 2011
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.utils;

/**
 * This class allows to open a URL on any browser.
 * @author FG
 * @param url URL
 */
public class OpenURL {
    String sUrl = "";
    String os = System.getProperty("os.name").toLowerCase();
    Runtime rt = Runtime.getRuntime();
    
    //... Constructor ...//
    public OpenURL(String url)
    {
        this.sUrl = url;
        
        //... Validate broswer settings ...//
        try{ 
            if (os.indexOf( "win" ) >= 0) {
                rt.exec( "rundll32 url.dll,FileProtocolHandler " + sUrl);

            } else if (os.indexOf( "mac" ) >= 0) {
                rt.exec( "open " + sUrl);
            } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {
                String[] browsers = {"firefox","mozilla","konqueror","opera","links","lynx"};

                StringBuilder cmd = new StringBuilder();
                for (int i=0; i<browsers.length; i++)
                    cmd.append((i==0  ? "" : " || " ) + browsers[i] + " \"" + sUrl + "\" ");
                rt.exec(new String[] { "sh", "-c", cmd.toString() });
           } else {
                return;
           }
        }catch (Exception e){
            return;
        }         
    }       
}
