package org.apw.arjunpscwala.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "APW_USER")
@SequenceGenerator(name = "users_generator", sequenceName = "APW_USER_seq", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    private Long userId;
    private Long mobileNo;
    private String userName;
    private String name;
    private String gender;
    private String city;
    private  String course;
    private LocalDateTime createdAt;
    private String fbToken;

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", mobileNo=" + mobileNo +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", courses='" + course + '\'' +
                ", createdAt=" + createdAt +
                ", ftoken='" + fbToken + '\'' +
                ", userName='" + fbToken + '\'' +
                '}';
    }
}
