package com.systemscanner.api.service;

import com.systemscanner.api.model.dto.SignupDTO;
import com.systemscanner.api.model.projection.UserLight;

public interface SignupService {

	UserLight signup(SignupDTO signupDTO);

}
