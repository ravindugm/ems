package com.practicecode.secureclient.service;

import com.practicecode.secureclient.entity.User;
import com.practicecode.secureclient.entity.VerificationToken;
import com.practicecode.secureclient.exception.UserNotFoundException;
import com.practicecode.secureclient.model.UserModel;
import com.practicecode.secureclient.repository.UserRepository;
import com.practicecode.secureclient.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User newUser(UserModel userModel) {
        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUser(int userId) throws UserNotFoundException {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "Invalid Token";
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "Expired Token";
        }

        user.setEnabled(true);
        userRepository.save(user);

        return "valid";
    }
}
