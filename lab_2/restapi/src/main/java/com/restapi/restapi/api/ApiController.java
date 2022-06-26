package com.restapi.restapi.api;

import lombok.Getter;
import org.json.simple.JSONObject;
import org.springframework.core.env.Environment;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ApiController {

    @Getter
    Thread mainThread;
    private Boolean isMainThreadRunning = false;
    @Getter
    Thread smsThread;
    private Boolean isSmsThreadRunning = false;
    @Getter
    private JSONObject jsonObject;
    private static final int MAX_RUNNING_MILLIS = 10000;
    private final Environment environment;
    private static final int MAX_TRY = 2;

    public ApiController(Environment environment) {
        this.environment = environment;
        jsonObject = new JSONObject();
        jsonObject.put("phoneNumberValid", "false");
        jsonObject.put("smsSend", "false");
        jsonObject.put("VideoTitle", "null");
    }

    public void generateResponse() {
        mainThread = new Thread(() -> {
            isMainThreadRunning = true;
            String clipTitle = getYtVideo();
            getJoke(clipTitle);
//            getJoke("Linux");
            isMainThreadRunning = false;
        });
        mainThread.start();
        startWatchDog();
    }

    public void putToJson(String key,
                          String value) {
        jsonObject.put(key, value);
    }

    public void startWatchDog() {
        Instant start = Instant.now();
        new Thread(() -> {
            while (true) {
                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start, end);
                if (timeElapsed.toMillis() > MAX_RUNNING_MILLIS) {
                    System.err.println("Timeout!");
                    stop();
                    return;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void stop() {
        if (isMainThreadRunning) {
            mainThread.interrupt();
        }

        if (isSmsThreadRunning) {
            smsThread.interrupt();
        }
    }

    private String getYtVideo() {
        YtMp3Download ytMp3Download = new YtMp3Download(
                "https://youtube-video-info.p.rapidapi.com",
                environment.getProperty("rapid_api.rapidapi-key"));
        ytMp3Download.get("/video_formats?video=", "edPREMPZ5RA");
        System.out.println(ytMp3Download.getValue("VideoTitle"));

        jsonObject.put("Mp3Link", ytMp3Download.getValue("AllFormats", "Link"));
        jsonObject.put("VideoTitle", ytMp3Download.getValue("VideoTitle"));

        return ytMp3Download.getValue("VideoTitle");
    }

    public void sendSms(String phoneNumber,
                        String message) {
        smsThread = new Thread(() -> {
            isSmsThreadRunning = true;
            SmsService smsService = new SmsService(
                    environment.getProperty("twilio.account_sid"),
                    environment.getProperty("twilio.auth_token"),
                    environment.getProperty("twilio.trial_number"));
            smsService.sendSms(phoneNumber, message);
            jsonObject.put("smsSend", "true");
            isSmsThreadRunning = false;
        });
        smsThread.start();
        startWatchDog();
    }

    private void getJoke(String title) {
        RestApiService jokeApi = new RestApiService("https://v2.jokeapi.dev/joke");
        if (title == null || !getJokeFromSentence(jokeApi, title)) {
            jokeApi.setUrlParams("/Programming");   // get default joke category
            jokeApi.get();
        }

        System.out.println(jokeApi.getValue("setup"));
        System.out.println(jokeApi.getValue("delivery"));

        jsonObject.put("setup", jokeApi.getValue("setup"));
        jsonObject.put("delivery", jokeApi.getValue("delivery"));
    }

    private Boolean getJokeFromSentence(RestApiService jokeApi, String sentence) {
        List<String> words = Arrays.asList(sentence
                .replaceAll("[^a-zA-Z\\s+]", "")
                .split("\\s+"));
        int curId = 0;
        while (curId < words.size()) {
            if (getJokeAbout(jokeApi, words.get(curId).toLowerCase(Locale.ROOT))) {
                return true;
            } else {
                curId++;
            }
        }
        return false;
    }

    private Boolean getJokeAbout(RestApiService jokeApi,
                                 String keyWord) {
        System.out.println("GET jokeApi " + keyWord);
        jokeApi.setUrlParams("/Any?contains=" + keyWord);
        jokeApi.get();

        int nTry = 0;
        while (jokeApi.getValue("setup") == null ||
                jokeApi.getValue("delivery") == null) {
            jokeApi.get();
            nTry++;
            if (nTry >= MAX_TRY) {
                return false;
            }
        }
        return true;
    }
}
