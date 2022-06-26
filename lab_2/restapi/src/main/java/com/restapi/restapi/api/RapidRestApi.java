package com.restapi.restapi.api;

import org.asynchttpclient.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface RapidRestApi {

    default JSONObject get(String url,
                           String params,
                           String rapidApiKey) {
        try {
            final JSONObject[] jsonObject = new JSONObject[1];
            DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl
                    .config()
                    .setConnectTimeout(4000);
            AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);

            BoundRequestBuilder request = client
                    .prepare("GET", url + params)
                    .setHeader("x-rapidapi-host", url.replaceAll("https://", ""))
                    .setHeader("x-rapidapi-key", rapidApiKey);

            request.execute(new AsyncCompletionHandler<Object>() {
                @Override
                public Object onCompleted(Response response) throws ParseException {
                    JSONParser parse = new JSONParser();
                    jsonObject[0] = (JSONObject) parse.parse(response.getResponseBody());
                    return response;
                }})
                    .toCompletableFuture()
                    .join();

            client.close();
            return jsonObject[0];
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    default String getJsonValue(JSONObject jsonObject,
                                String key) {
        if (jsonObject.isEmpty() ||
                !jsonObject.containsKey(key) ||
                jsonObject.get(key).toString() == null) {
            return null;                //"No matching key."
        }
        return jsonObject.get(key).toString();
    }

    default JSONArray getJsonObject(JSONObject jsonObject,
                                    String key) {
        if (jsonObject.isEmpty() ||
                !jsonObject.containsKey(key) ||
                jsonObject.get(key).toString() == null) {
            return null;
        }
        return (JSONArray) jsonObject.get(key);
    }
}
