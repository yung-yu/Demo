package com.di.component

import dagger.Component
import javax.inject.Inject

class Student {
    init {
        DaggerStudentComponent.create().injectStudent(this)
    }
    @Inject
    lateinit var test: Test
}

class Test(val score: Number) {
    @Inject
    constructor() : this(60)
}

@Component
interface StudentComponent {
    fun injectStudent(student: Student)
}