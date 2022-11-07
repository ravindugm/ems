package com.practicecode.secureclient.service;

import com.practicecode.secureclient.entity.User;
import com.practicecode.secureclient.exception.UserNotFoundException;
import com.practicecode.secureclient.model.UserModel;

public interface UserService {

    User newUser(UserModel userModel);

    User getUser(int userId) throws UserNotFoundException;

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);
}
