package com.interview.merabills;


import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    private static final String FILE_NAME = "LastPayment.txt";
    private static final Gson gson = new Gson();

    public static void saveToFile(Context context, List<Payment> payments) {
        String jsonData = gson.toJson(payments);
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             OutputStreamWriter writer = new OutputStreamWriter(fos)) {
            writer.write(jsonData);
            Log.d("FileHelper", "Payments saved successfully.");
        } catch (IOException e) {
            Log.e("FileHelper", "Error saving payments: " + e.getMessage());
        }
    }

    public static List<Payment> loadFromFile(Context context) {
        List<Payment> payments = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (!file.exists()) {
            return payments;
        }

        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonData.append(line);
            }

            Type listType = new TypeToken<List<Payment>>() {}.getType();
            payments = gson.fromJson(jsonData.toString(), listType);

            Log.d("FileHelper", "Payments loaded successfully.");
        } catch (IOException e) {
            Log.e("FileHelper", "Error loading payments: " + e.getMessage());
        }

        return payments;
    }
}