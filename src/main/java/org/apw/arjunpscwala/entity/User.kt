package org.apw.arjunpscwala.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import lombok.Data
import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime


@Entity
@Data
@Table(name = "APW_USER")
@SequenceGenerator(name = "users_generator", sequenceName = "APW_USER_seq", allocationSize = 1)
class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    val userId: Long? = null
    val mobileNo: Long? = null

    @Getter
    val userName: String? = null
    val name: String? = null
    val gender: String? = null
    val city: String? = null
    val course: String? = null
    private val createdAt: LocalDateTime? = null
    private val fbToken: String? = null

    @Setter
    @Getter
    @Transient
    var token: String? = null


    override fun toString(): String {
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
                '}'
    }
}

