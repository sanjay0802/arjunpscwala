package org.apw.arjunpscwala.repository;

import org.apw.arjunpscwala.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    @Query("Select u from User u where u.mobileNo= :mobileNo")
    List<Optional<User>> findUserByMobileNoAndFtoken(@Param("mobileNo") Long mobileNo);

    @Query("Select u from User u where u.mobileNo= :mobileNo or u.userName= :userName")
    List<Optional<User>> getUser(@Param("mobileNo") Long mobileNo, @Param("userName") String userName);

}
