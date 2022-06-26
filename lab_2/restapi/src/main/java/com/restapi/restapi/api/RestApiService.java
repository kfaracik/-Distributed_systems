package com.restapi.restapi.api;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RestApiService implements RapidRestApi {

    @Setter
    private URL url;
    @Getter
    private JSONObject jsonObject;

    //"https://api.covid19api.com/summary"
    public RestApiService(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            System.err.println("Can not set URL!");
        }
    }

    public void setUrlParams(String urlParams) {
        try {
            url = new URL( url.toString() + urlParams);
        } catch (MalformedURLException e) {
            System.err.println("Can not add params to URL!");
        }
    }

    public JSONObject get() {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                jsonObject = (JSONObject) parse.parse(stringBuilder.toString());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public String getValue(String key) {
        return RapidRestApi.super.getJsonValue(jsonObject, key);
    }
}
