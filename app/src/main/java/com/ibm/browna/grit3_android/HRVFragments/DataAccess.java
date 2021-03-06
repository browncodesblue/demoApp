package com.ibm.browna.grit3_android.HRVFragments;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by garrypolykoff on 1/18/17.
 */

public class DataAccess {


    public static String writeToFile(String data, Context context) {
        try {


            String filename = getCurrentDate().concat(".csv");
            //  File file = new File(context.getFilesDir(), filename);

            Log.e("Data", data);
            Log.e("filename", filename);

            //add current date-time to data

            String txt = data.concat("," + getCurrentDate());

            Log.e("Data", txt);
            Log.e("filename", filename);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(txt);
            outputStreamWriter.close();
            return filename;

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        return null;
    }


    public static String readFromFile(String filename, Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    private static String getCurrentDate() {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return dateFormat.format(date);

    }


}
