package com.restapi.restapi.api;


import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class YtMp3Download implements RapidRestApi {

    @Getter
    private JSONObject jsonObject = null;
    private final String url;
    private final String rapidApiKey;

    public YtMp3Download(String url, String rapidApiKey) {
        this.url = url;
        this.rapidApiKey = rapidApiKey;
    }

    public JSONObject get(String params, String value) {
        jsonObject = RapidRestApi.super.get(
                url,
                params + value,
                rapidApiKey);
        return jsonObject;
    }

    public String getValue(String key) {
        return RapidRestApi.super.getJsonValue(
                jsonObject,
                key);
    }

    public String getValue(String key, String subKey) {
        JSONArray jsonArray = RapidRestApi.super.getJsonObject(
                jsonObject,
                key);
        if (jsonArray == null ) {
            return null;
        }

        return RapidRestApi.super.getJsonValue(
                (JSONObject) jsonArray.get(0),
                subKey);
    }
}
