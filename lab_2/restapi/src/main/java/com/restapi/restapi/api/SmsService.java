package com.restapi.restapi.api;

import org.slf4j.Logger;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/*
    Twilio allows sending 353 messages on trial account message_cost = 0,041 $ https://console.twilio.com/
    Parsing number phone 100 request / mo https://numverify.com/usage
 */
public class SmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsService.class);

    @Value("twilio")
    private String trialNumber;

    public SmsService(String accountSid,
                      String authToken,
                      String trialNumber) {
        this.trialNumber = trialNumber;
        Twilio.init(accountSid, authToken);
    }

    public SmsService() {}

    public void sendSms(String destinationPhoneNumber, String message) {
        if (isPhoneNumberValid(destinationPhoneNumber)) {
            sendMessage(
                    destinationPhoneNumber,
                    trialNumber,
                    message);
        } else {
            throw new IllegalArgumentException(
                    "Phone number \"" + destinationPhoneNumber + "\" is not a valid number."
            );
        }
    }

    private void sendMessage(String to, String from, String message) {
        Message smsMessage = Message.creator(
                        new com.twilio.type.PhoneNumber(to),
                        new com.twilio.type.PhoneNumber(from),
                        message)
                .create();

        System.out.println(smsMessage.getSid());
        LOGGER.info("Send sms {}", message);
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
//        // todo account is not activated... https://numverify.com/usage
//        RestApiService numberValidator = new RestApiService("http://apilayer.net/api/validate");
//        numberValidator.setUrlParams("?access_key=2ecb9f06a76fc37fda470a13c0327b23&number=" + phoneNumber.replaceAll("[^0-9]",""));
//        System.out.println(numberValidator.get().toString());
//        boolean result = Objects.equals(numberValidator.getValue("valid"), "true");
        return true;
    }
}
