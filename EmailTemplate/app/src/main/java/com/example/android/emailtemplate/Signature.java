package com.example.android.emailtemplate;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Eric Andow on 9/20/2015.
 */
public class Signature {
    private static final String TAG = "Signature";
    private static final String filename = "signature.txt";
    private static String filePath;

    private static Signature sSignature;

    private String mSignature;

    public static Signature getInstance(Context context) {
        if(sSignature == null) {
            sSignature = new Signature(context);
        }

        return sSignature;
    }

    private Signature(Context context) {
        filePath = context.getFilesDir()+"/";

        try {
            FileReader fileReader = new FileReader(filePath + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuffer stringBuffer = new StringBuffer("");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append("\n");
                stringBuffer.append(line);
            }

            bufferedReader.close();

            String contents = stringBuffer.substring(1);

            mSignature = contents;
            Log.i(TAG, "The saved signature is: " + contents);
        } catch (FileNotFoundException fnfe) {
            Log.i(TAG, "We will be creating the file "+filePath+filename);
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage());
        }
    }

    //Returns the user's signature as a string
    public String getSignature() {
        return mSignature;
    }

    //Sets the user's signature to the provided string
    public void setSignature(String signature) {
        mSignature = signature;
    }

    //Saves the signature to file. Please call this before the application is destroyed
    public void saveSignature() {
        try {
            File sigFile = new File(filePath, filename);
            FileWriter fileWriter = new FileWriter(sigFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(mSignature);

            bufferedWriter.close();

        } catch (IOException ioe) {
            Log.e(TAG, "Could not write to "+filePath+filename+": "+ioe.getMessage());
        }
    }
}
