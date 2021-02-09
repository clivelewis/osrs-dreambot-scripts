package com.clivelewis.chatSpammer.utils;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.Timer;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Clive on 10/2/2019
 */
public class Utils {
    public static final Tile GRAND_EXCHANGE = new Tile(3165 ,3484);
    public static final Tile LUMBRIDGE = new Tile(3224, 3217);

    public static final Color GUI_BACKGROUND_COLOR = new Color(66, 66, 66);

    public static final long MINUTES = 60000;


    public static String getFormattedTimeLeft(Timer timer){
        long timeLeft = timer.remaining();
        long second = (timeLeft / 1000) % 60;
        long minute = (timeLeft / (1000 * 60)) % 60;
        long hour = (timeLeft / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static boolean isConnectedToInternet(){
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            MethodProvider.log("Stable connection");

            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
