package com.restapi.restapi.server;

import com.restapi.restapi.api.ApiController;
import com.restapi.restapi.api.SmsService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class UserController {

    private final ApiController apiController;
    private final HashMap<String, UserData> users = new HashMap<>();    // optionally add lock on phone number
    private static final String VERIFICATION_KEY = "5432";

    public UserController(Environment environment) {
        this.apiController = new ApiController(environment);
    }

    public String getUser(String phoneNum,
                          String movieId,
                          String sendSmsString,
                          String verificationKey) {
        System.err.println(sendSmsString);
//        boolean sendSms = false;
        boolean sendSms = true;
        if (Objects.equals(sendSmsString, "true") ||
                Objects.equals(sendSmsString, "True") ||
                Objects.equals(sendSmsString, "1")) {
            sendSms = true;
        }

        System.err.println("*********************************************************************************");
        System.out.println("GET: " + phoneNum + " " + movieId + " " + sendSms + " " + verificationKey);

        if (!users.containsKey(phoneNum)) {
            return "User with given number dont exists.";
        }

        if (phoneNum == null) {
            return "Require phone number!";
        }

        if (sendSms && !users.get(phoneNum).getMessageSend()) {
            apiController.sendSms("+48691720138", "Your verification kode: 5432.");
            users.get(phoneNum).setMessageSend(true);
        }

        if (movieId == null) {
            return "Require movie id!";
        }

        if (verificationKey.equals(users.get(phoneNum).getVerificationKey())) {
            apiController.generateResponse();
            return apiController.getJsonObject().toJSONString();
        } else {
            return "Wrong verification key!";
        }
    }

    public void addNewUser(String phoneNum,
                           String movieId) {
        System.out.println("POST: " + phoneNum + " " + movieId);
        if (!users.containsKey(phoneNum)) {
            // TODO : limited
            boolean isValid = new SmsService().isPhoneNumberValid(phoneNum);
            System.err.println(isValid);
            if (isValid) {
                apiController.putToJson("phoneNumberValid", "true");
            }
            users.put(phoneNum,
                    new UserData(movieId, VERIFICATION_KEY));
        } else {
            throw new IllegalArgumentException("User with given number already exists.");
        }
    }

    public void deleteUser(String phoneNum) {
        System.out.println("DELETE: " + phoneNum);
        if (users.containsKey(phoneNum)) {
            users.remove(phoneNum);
        } else {
            throw new IllegalArgumentException("User with given number dont exists.");
        }
    }

    public void updateUser(String phoneNum,
                           String movieId) {
        System.out.println("PUT: " + phoneNum + " " + movieId);
        if (users.containsKey(phoneNum)) {
            boolean wasMessageSend = users.get(phoneNum).getMessageSend();
            users.put(phoneNum,
                    new UserData(movieId, VERIFICATION_KEY, wasMessageSend));
        } else {
            throw new IllegalArgumentException("User with given number dont exists.");
        }
    }
}
