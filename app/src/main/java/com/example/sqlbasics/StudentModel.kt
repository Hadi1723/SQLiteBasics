package com.example.sqlbasics

import java.util.*

data class StudentModel (
    var id: Int = getAutoId(),
    var name: String = " ",
    var email: String = " "

        ) {

    // the data class is used to define a new data type or model

    // the body of the data class is used for companion objects or overridding certin methods such as hashcode and equals

    // the companion object allows us to use the getAutoId function inside the parameters of the data class
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }

}

