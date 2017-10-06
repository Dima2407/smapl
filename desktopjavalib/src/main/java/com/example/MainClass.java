package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
    private static final String SERVICE_ACTION = "action1";
    private static final String KEY1 = "key1";
    private static final String KEY2 = "key2";

    public static void main(String[] args) {

        final List<String> dataList = readDataFromFile("MyFile.txt");

        for (int i = 0; i < dataList.size(); i++) {
            String[] dataString = dataList.get(i).split("[ ]");
            final int delay = Integer.parseInt(dataString[0]);
            final String lat = dataString[1];
            final String lang = dataString[2];

            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String execStart = "/opt/Android/Sdk/platform-tools/adb -d shell am startservice -a " + SERVICE_ACTION + " --es " + KEY1 + " " + lat + " --es " + KEY2 + " " + lang;
                    execCommand(execStart);
                    if (finalI == dataList.size() - 1) {
                        String execStop = "/opt/Android/Sdk/platform-tools/adb -d shell am stopservice -a " + SERVICE_ACTION;
                        execCommand(execStop);
                    }
                }
            }).start();
        }
    }

    private static void execCommand(String command) {
        try {
            System.out.println(command);
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("sh", "-c", command);
            Process process = builder.start();
            process.waitFor();
            InputStream errorStream = process.getErrorStream();
            InputStream inputStream = process.getInputStream();
            System.out.println(MainClass.toString(errorStream));

            System.out.println(MainClass.toString(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readDataFromFile(String fileName) {

        List<String> resultList = new ArrayList<>();

        String text = null;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((text = br.readLine()) != null) {
                resultList.add(text);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    private static String toString(InputStream inputStream) {
        String text = null;
        StringBuilder result = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((text = br.readLine()) != null) {
                result.append(text).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
