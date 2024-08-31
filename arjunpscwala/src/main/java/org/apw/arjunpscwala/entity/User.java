package org.apw.arjunpscwala.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "APW_USER")
@SequenceGenerator(name = "users_generator", sequenceName = "APW_USER_seq", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    private Long userId;
    private Long mobileNo;
    private String name;
    private String gender;
    private String city;
    private  String courses;
    private String address;
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
                ", courses='" + courses + '\'' +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                ", ftoken='" + fbToken + '\'' +
                '}';
    }
}
