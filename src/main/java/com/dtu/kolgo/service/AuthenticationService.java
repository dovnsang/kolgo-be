package com.dtu.kolgo.service;

import com.dtu.kolgo.dto.request.LoginRequest;
import com.dtu.kolgo.dto.request.RegisterRequest;
import com.dtu.kolgo.dto.request.ResetPasswordRequest;
import com.dtu.kolgo.dto.request.UpdatePasswordRequest;
import com.dtu.kolgo.dto.response.TokenResponse;
import com.dtu.kolgo.dto.response.WebResponse;

public interface AuthenticationService {

    WebResponse register(RegisterRequest request);

    WebResponse verify(String token, boolean isBiz);

    Object login(LoginRequest request);

    void authenticate(long id, String password);

    TokenResponse refreshToken(String refreshToken);

    WebResponse resetPassword(ResetPasswordRequest request);

    WebResponse updatePassword(String resetPasswordToken, UpdatePasswordRequest request);

}
