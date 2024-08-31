package org.apw.arjunpscwala.model;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apw.arjunpscwala.entity.User;

@Data
@ToString
@Builder
public class UserResponse {
     User user;
     String token;
     String msg;
}
