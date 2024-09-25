package org.apw.arjunpscwala.service;



import lombok.extern.slf4j.Slf4j;
import org.apw.arjunpscwala.entity.User;
import org.apw.arjunpscwala.exception.ApwException;
import org.apw.arjunpscwala.model.UserResponse;
import org.apw.arjunpscwala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> verifyUserDetails(Long mobileNo, String token) {

        return userRepository.findUserByMobileNoAndFtoken(mobileNo, token);


    }


    public Optional<UserResponse> registerUser(User user) throws ApwException {

        System.out.println("User Details===>" + user);
        //case-1 username and mobile are same--pass
        //case-1 username same and mobile is different

        Optional<User> user1 = userRepository.getUser(user.getMobileNo(), user.getUserName());

        if (user1.isPresent()) {
            if (user.getUserName().equalsIgnoreCase(user1.get().getUserName())) {

                throw new ApwException("User Name Already Registered",user);

            } else if (Objects.equals(user.getMobileNo(), user1.get().getMobileNo())) {

                throw new ApwException("Mobile No.Already Registered",user);

            }
        } else {
            User newUser = userRepository.save(user);
            return Optional.ofNullable(UserResponse.builder()
                    .msg("User Has Been Registered Successfully")
                    .user(newUser)
                    .build());
        }

     return Optional.empty();
    }
}