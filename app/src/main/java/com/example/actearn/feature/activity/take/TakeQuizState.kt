package com.example.actearn.feature.activity.take

sealed class TakeQuizState {
    object NavigateBack : TakeQuizState()

    data class StudentEarnedPoints(val points: Int) : TakeQuizState()
}