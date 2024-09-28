package com.arjunpscwala.pscwala.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjunpscwala.pscwala.PhoneAuthInfo
import com.arjunpscwala.pscwala.models.ErrorMessage
import com.arjunpscwala.pscwala.models.request.ProfileRequest
import com.arjunpscwala.pscwala.models.state.UIState
import com.arjunpscwala.pscwala.randomUUID
import com.arjunpscwala.pscwala.repository.AuthRepository
import com.arjunpscwala.pscwala.repository.AuthRepositoryImpl
import com.arjunpscwala.pscwala.utils.Event
import com.arjunpscwala.pscwala.utils.EventBus
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Gender(val type: String) {
    MALE("MALE"), FEMALE("FEMALE");

    companion object {
        fun isMale(gender: String): Boolean {
            return gender == MALE.type
        }

        fun isFemale(gender: String): Boolean {
            return gender == FEMALE.type
        }
    }
}

class ProfileViewModel : ViewModel() {

    private lateinit var data: PhoneAuthInfo
    private val _profileUIState = MutableStateFlow(ProfileUIState())
    val profileUIState = _profileUIState.asStateFlow()
    private val authRepository: AuthRepository = AuthRepositoryImpl()

    private val eventBusJob = viewModelScope.launch {
        EventBus.events.collect { event ->
            if (event is Event.NavigateEvent<*>) {
                data = event.data as PhoneAuthInfo

            }
        }
    }

    fun registerProfile(
    ) {

        viewModelScope.launch {
            try {
                _profileUIState.update {
                    it.copy(isLoading = true)
                }
                val result = authRepository.updateProfile(
                    ProfileRequest(
                        mobileNo = data.mobileNo,
                        gender = _profileUIState.value.gender,
                        city = _profileUIState.value.city,
                        course = _profileUIState.value.course
                    )
                )
                if (result.status == HttpStatusCode.OK.value) {
                    _profileUIState.update {
                        it.copy(isLoading = false, navigateToHome = true)
                    }
                } else {
                    _profileUIState.update {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = randomUUID(),
                            message = result.parseError()
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }


            } catch (e: Exception) {
                _profileUIState.update {
                    val errorMessages = it.errorMessages + ErrorMessage(
                        id = randomUUID(),
                        message = e.message ?: ""
                    )
                    it.copy(errorMessages = errorMessages, isLoading = false)
                }
            }
        }
    }

    fun onGenderClick(type: String) {
        _profileUIState.update {
            it.copy(gender = type)
        }
    }

    fun onCityValueChange(city: String) {
        _profileUIState.update {
            it.copy(city = city)
        }
    }

    fun onCourseValueChange(course: String) {
        _profileUIState.update {
            it.copy(course = course)
        }
    }

    override fun onCleared() {
        super.onCleared()
        eventBusJob.cancel()
    }
}

data class ProfileUIState(
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val navigateToHome: Boolean = false,
    val gender: String = Gender.MALE.type,
    val course: String = "",
    val city: String = ""
) : UIState()