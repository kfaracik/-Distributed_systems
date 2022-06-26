package com.restapi.restapi.server;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {

    public UserData(String movieId,
                    String verificationKey) {
        this.movieId = movieId;
        this.verificationKey = verificationKey;
        this.messageSend = false;
    }

    public UserData(String movieId,
                    String verificationKey,
                    boolean messageSend) {
        this.movieId = movieId;
        this.verificationKey = verificationKey;
        this.messageSend = messageSend;
    }

    private String movieId;
    private String verificationKey;
    private Boolean messageSend;
}
