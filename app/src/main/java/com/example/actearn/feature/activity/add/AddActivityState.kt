package com.example.actearn.feature.activity.add

import com.example.actearn.model.entity.Subject

sealed class AddActivityState {
    data class Subjects(val subject: List<Subject>) : AddActivityState()
}