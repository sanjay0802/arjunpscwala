package org.apw.arjunpscwala.model

import org.apw.arjunpscwala.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AuthUserDetails(val user: User) : UserDetails {
    val userId = user.userId
    val userName = user.userName

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return emptySet()
    }

    override fun getPassword(): String? {
        return ""
    }

    override fun getUsername(): String? {
        return userName
    }
}

