package org.apw.arjunpscwala.controller;


import jdk.dynalink.StandardNamespace;
import lombok.extern.log4j.Log4j;
import org.apw.arjunpscwala.entity.User;
import org.apw.arjunpscwala.model.StandardResponse;
import org.apw.arjunpscwala.model.UserResponse;
import org.apw.arjunpscwala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/apw")

public class UserController {


    @Autowired
    UserService service;

    @PostMapping("/verify")
    public ResponseEntity<StandardResponse<User>> verifyUser(@RequestBody HashMap<String,String> data)
    {

        Long mobileNo = Long.valueOf(data.get("mobileNo"));
        String fbToken = data.get("fbToken");

        System.out.println("Mobile No"+mobileNo +"fbToken"+fbToken);

        Optional<User> users = service.verifyUserDetails(mobileNo, fbToken);
        if(users.isPresent()){

            StandardResponse<User> response = StandardResponse.success(users.get(), "Otp Verified Successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));

        }else{

            StandardResponse<User> response = StandardResponse.failure("User Does not Exist", HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(401));

        }


    }

    @PostMapping("/register")

    public ResponseEntity<User> registerUser(@RequestBody User user) {

        System.out.println("user details" + user.toString());
        User users = service.registerUser(user);

        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public String updateUserDetails(@RequestBody User user)
    {
        return "update user";
    }



    //verifyUser(mobile no, firebase tocken)
    //selct * from mobile not exit then return 404
    //if exist then update the token and return user details

   //registerUer(UserDetail,firebase tocken)
   //updateProfile(UserDetails)--update the user detail
}
