package com.example.actearn.model.modelview

import com.example.actearn.model.entity.Activity

data class QuizSubjectData(
    val activity: Activity,
    val isPassed: Boolean? = null
)