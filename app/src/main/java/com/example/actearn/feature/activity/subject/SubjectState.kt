package com.example.actearn.feature.activity.subject

import com.example.actearn.model.entity.Subject

sealed class SubjectState {
    data class Subjects(val subjects: List<Subject>) : SubjectState()
}
