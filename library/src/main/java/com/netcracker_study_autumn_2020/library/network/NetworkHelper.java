package com.netcracker_study_autumn_2020.library.network;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

import static com.netcracker_study_autumn_2020.library.network.NetworkUtils.API_ADDRESS;
import static com.netcracker_study_autumn_2020.library.network.NetworkUtils.CONNECTION_TIMEOUT;

//После внедрения Retrofit больше не используется
public class NetworkHelper {

    //public static String getApiAddress() {
        //return API_ADDRESS;
    //}

    //
    public  static String requestGET(String postfix ){
        URL url = null;
        Scanner sc;
        String result = "NONE";
        try {
            url = new URL(API_ADDRESS + postfix);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream((urlConnection.getInputStream()));

            sc = new Scanner(in).useDelimiter("\\A");
            result = sc.hasNext() ? sc.next() : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.d("RESPONSE", result);
        return result;
    }

    /**
     * POST запрос, содержащий в теле json-объект, на указанный
     * API_ADDRESS + postfix
     *
     * @param  postfix  уточнение обращения к API (начинается с "/"!)
     * @param  jsonObject  json-объект, который необходимо отправить на сервер
     * @return  код ответа + текстовый результат, если ожидается
     */
    public static String requestPOST(String postfix,
                                     JSONObject jsonObject,
                                     boolean waitingForResponse){
        URL url = null;
        String data = jsonObject.toString();
        String response = "";
        try {
            url = new URL(API_ADDRESS + postfix);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection con = null;
        try {
            assert url != null;
            con = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = (HttpURLConnection)con;
        try {
            assert http != null;
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestMethod("POST");
            http.setConnectTimeout(CONNECTION_TIMEOUT);
            http.connect();


            OutputStream outputStream = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter((
                    new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)));
            writer.write(data);
            writer.close();
            outputStream.close();

            if (waitingForResponse){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        http.getInputStream(), StandardCharsets.UTF_8));
                String stringBuffer = null;
                StringBuilder responseConstructor = new StringBuilder();

                while((stringBuffer = bufferedReader.readLine()) != null){
                    responseConstructor.append(stringBuffer);
                }

                bufferedReader.close();
                response = responseConstructor.toString();
            }

            response += http.getResponseCode() + ":";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}