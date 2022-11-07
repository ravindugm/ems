package com.practicecode.secureclient.controller;

import com.practicecode.secureclient.entity.User;
import com.practicecode.secureclient.event.RegistrationCompleteEvent;
import com.practicecode.secureclient.exception.UserNotFoundException;
import com.practicecode.secureclient.model.UserModel;
import com.practicecode.secureclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.newUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User Verifies Successfully";
        }
        return "Bad User";
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
