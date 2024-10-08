package org.apw.arjunpscwala.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apw.arjunpscwala.entity.User;
import org.apw.arjunpscwala.exception.ApwException;
import org.apw.arjunpscwala.model.UserResponse;
import org.apw.arjunpscwala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<Optional<User>> verifyUserDetails(Long mobileNo) {

           log.info("inside verifyUserDetails()");

        return userRepository.findUserByMobileNoAndFtoken(mobileNo);


    }

    public Optional<UserResponse> registerUser(User user) throws ApwException {
        log.info("inside registerUser()"+ user);


        //case-1 username and mobile are same--pass
        //case-1 username same and mobile is different

        List<Optional<User>> user1 = userRepository.getUser(user.getMobileNo(), user.getUserName());

        if (!user1.isEmpty()) {
            if (user.getUserName().equalsIgnoreCase(user1.get(0).get().getUserName())) {

                throw new ApwException("User Name Already Registered",user);

            } else if (Objects.equals(user.getMobileNo(), user1.get(0).get().getMobileNo())) {

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


    @Transactional
    public int updateUserDetails(User user) throws ApwException {

        int i=0;

        if(user!=null) {
             i = userRepository.updateUserDetails(user.getCourse(), user.getCity(), user.getCourse(), user.getMobileNo());

            return i;

        }


    return i;

    }

}