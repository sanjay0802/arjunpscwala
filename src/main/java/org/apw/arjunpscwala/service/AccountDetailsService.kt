package org.apw.arjunpscwala.service

import org.apw.arjunpscwala.exception.UserNotFoundException
import org.apw.arjunpscwala.model.AuthUserDetails
import org.apw.arjunpscwala.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class AccountDetailsService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(Exception::class)
    fun loadUserById(id: Long): AuthUserDetails {
        val user = userRepository.findById(id)

        //TODO Implement Role here
        //      val grantedAuthoritySet: Collection<GrantedAuthority> = hashSetOf();
//        user.authorities = grantedAuthoritySet

        return user.map(::AuthUserDetails).orElseThrow { UserNotFoundException("Invalid User") }
    }

    @Throws(Exception::class)
    override fun loadUserByUsername(username: String?): AuthUserDetails? {
        return userRepository.findByUserName(username)
            .map(::AuthUserDetails).orElseThrow {
                UserNotFoundException("Invalid User")
            }
    }


}