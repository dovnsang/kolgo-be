package com.dtu.kolgo.service;

import com.dtu.kolgo.dto.request.ChangePasswordRequest;
import com.dtu.kolgo.dto.response.WebResponse;
import com.dtu.kolgo.model.User;

import java.security.Principal;

public interface UserService {

    User save(User user);

    User get(long id);

    User get(String email);

    WebResponse changePassword(Principal principal, ChangePasswordRequest request);

}
