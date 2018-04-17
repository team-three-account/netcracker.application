package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.utilites.VerificationToken;

import javax.jws.soap.SOAPBinding;

public interface UserService {

    VerificationToken createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String token);



    void saveRegisteredUser(User user);

    void deleteVerificationToken(VerificationToken verificationToken);

    User findUserByEmail(String email);

    User getAuthenticatedUser();

    void changeUserPassword(String password, String email);
}
