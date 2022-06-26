package com.restapi.restapi.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/downloader")
public class ServerController {

    @Autowired
    private final UserController userController;

    @Autowired
    private Environment environment;

    @Autowired
    public ServerController() {
        this.userController = new UserController(environment);
    }

    @GetMapping()
    public String getUser(
            @RequestParam(value="phoneNum", required = false) String phoneNum,
            @RequestParam(value="movieId", required = false) String movieId,
            @RequestParam(value="sendSms", required = false) String sendSms,
            @RequestParam(value= "verificationKey", required = false) String verificationKey) {
        return userController.getUser(
                phoneNum,
                movieId,
                sendSms,
                verificationKey);
    }

    @PostMapping()
    public void registerNewUser(
            @RequestParam(value="phoneNum") String phoneNumber,
            @RequestParam(value="movieId", required = false) String movieId) {
        userController.addNewUser(
                phoneNumber,
                movieId);
    }

    @DeleteMapping()
    public void deleteUser(
            @RequestParam(value="phoneNum") String phoneNum) {
        userController.deleteUser(phoneNum);
    }

    @PutMapping()
    public void updateUser(
            @RequestParam(value="phoneNum") String phoneNum,
            @RequestParam(value="movieId", required = false) String movieId) {
        userController.updateUser(
                phoneNum,
                movieId);
    }
}
