package com.example.actearn.feature.records

import com.example.actearn.model.entity.Subject

sealed class RecordState {
    data class Subjects(val subjects: List<Subject>) : RecordState()
}
