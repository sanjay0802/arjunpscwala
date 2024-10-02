package org.apw.arjunpscwala.controller;


import lombok.extern.slf4j.Slf4j;
import org.apw.arjunpscwala.entity.User;
import org.apw.arjunpscwala.exception.ApwException;
import org.apw.arjunpscwala.model.StandardResponse;
import org.apw.arjunpscwala.model.UserResponse;
import org.apw.arjunpscwala.service.JWTService;
import org.apw.arjunpscwala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.apw.arjunpscwala.constant.ApwConstants.USER_REGISTERED_MESSAGE;

@RestController
@RequestMapping("/apw")
@Slf4j
public class UserController {


    @Autowired
    UserService service;
    @Autowired
    JWTService jwtService;

    @PostMapping("/auth/verify")
    public ResponseEntity<StandardResponse<User>> verifyUser(@RequestBody HashMap<String, String> data) {
        try {
            Long mobileNo = Long.valueOf(data.get("mobileNo"));
            String fbToken = data.get("fbToken");

            log.info("inside verifyUser()" + " Mobile No." + mobileNo + " Token: " + fbToken);

            List<Optional<User>> users = service.verifyUserDetails(mobileNo);
            if (!users.isEmpty()) {
                User user = users.get(0).get();
                user.setToken(jwtService.generateToken(user.getUserId()));

                StandardResponse<User> response = StandardResponse.success(user, "Otp Verified Successfully", HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));

            } else {

                StandardResponse<User> response = StandardResponse.failure(null, "User Does not Exist", HttpStatus.UNAUTHORIZED.value());
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(401));

            }
        } catch (Exception e) {
            System.out.println("Exception occurred" + e);
        }

        return null;
    }

    @PostMapping("/auth/register")

    public ResponseEntity<StandardResponse<User>> registerUser(@RequestBody User user) throws ApwException {

        System.out.println("user details" + user.toString());
        Optional<UserResponse> userResponse = service.registerUser(user);

        if (userResponse.get().getMsg().equalsIgnoreCase(USER_REGISTERED_MESSAGE)) {
            User resultUser = userResponse.get().getUser();
            resultUser.setToken(jwtService.generateToken(resultUser.getUserId()));
            StandardResponse<User> response = StandardResponse.success(resultUser, "User Has Been Registered Successfully", HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
        }

        return null;
    }

    @PostMapping("/update")
    public ResponseEntity<StandardResponse<User>> updateUserDetail(@RequestBody User user) {

        log.info("inside updateUserDetail(): " + user);

        int i = service.updateUserDetails(user);

        if (i > 0) {

            StandardResponse<User> response = StandardResponse.success(user, "User Details Updated Successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));

        } else {
            StandardResponse<User> response = StandardResponse.failure(null, "User Does not Exist", HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(401));


        }


    }


    @ExceptionHandler(value = ApwException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardResponse handleCustomerAlreadyExistsException(ApwException ex) {

        System.out.println("This is Custom Exception");
        String message = ex.getMessage();
        User user = ex.user;

        return StandardResponse.failure(user, message, HttpStatus.BAD_REQUEST.value());
    }
}

