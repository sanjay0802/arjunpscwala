package org.apw.arjunpscwala.service;



import lombok.extern.slf4j.Slf4j;
import org.apw.arjunpscwala.entity.User;
import org.apw.arjunpscwala.model.UserResponse;
import org.apw.arjunpscwala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> verifyUserDetails(Long mobileNo, String token){

     return   userRepository.findUserByMobileNoAndFtoken(mobileNo, token);



    }



public Optional<User> registerUser(User user){

    System.out.println("User Details"+user);

    User newUser = userRepository.save(user);
    return Optional.of(newUser);
}
}
