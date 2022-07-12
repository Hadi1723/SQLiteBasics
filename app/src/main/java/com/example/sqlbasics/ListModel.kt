package com.example.sqlbasics

import android.widget.Button
import kotlin.properties.Delegates

class ListModel {

    private var id by Delegates.notNull<Int>()
    private lateinit var name: String
    private lateinit var email: String

    constructor(id: Int, name: String, email:String) {
        this.id = id
        this.name = name
        this.email = email
    }

    @JvmName("getId1")
    fun getId(): Int {
        return this.id
    }

    @JvmName("setId1")
    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return this.name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email: String) {
        this.email = email
    }
}