package com.example.sqlbasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqliteHelper: SQLiteHelper

    private lateinit var listRV: RecyclerView


    //incorporating each item in recyclerview
    private lateinit var detailArrayList: ArrayList<ListModel>
    //incorporating the recyclerview item
    private lateinit var detailAdapter: ListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)

        listRV = findViewById(R.id.listOfStudents)

        sqliteHelper = SQLiteHelper(this)

        //defining list that contain each item in recylerview item
        detailArrayList = arrayListOf()

        //laying out the entire recyclerview
        detailAdapter = ListAdapter(this, detailArrayList)
        listRV.adapter = detailAdapter

        detailAdapter?.setOnClickDeleteItem {
            deleteStudent(it.id)
        }

        btnAdd.setOnClickListener{
            added()
        }

        btnView.setOnClickListener{
            viewResults()
        }

        btnUpdate.setOnClickListener{
            update()
        }
    }

    private fun deleteStudent(id: Int) {
        sqliteHelper.deleteStudent(id)

        /*
        detailArrayList.filter {
            it.getId() == id
        }

        detailAdapter.notifyDataSetChanged() */


        for (i in 0..(detailArrayList.size-1)) {
            if (detailArrayList[i].getId() == id) {
                detailArrayList.removeAt(i)
                detailAdapter.notifyDataSetChanged()
                break
            }
        } 
    }

    private fun viewResults() {

        detailArrayList.clear()

        var students = sqliteHelper.getAllStudent()

        if (students.isEmpty()) {
            Toast.makeText(this, "No values to show", Toast.LENGTH_SHORT).show()
        } else
        {
            for (student in students) {
                detailArrayList.add(ListModel(student.id,student.name,student.email))
            }

            detailAdapter.notifyDataSetChanged()
        }
    }

    private fun added() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Enter information", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name = name, email = email)
            val status = sqliteHelper.insertStudent(std)

            if (status > -1) {
                Toast.makeText(this, "Student added ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun update() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Enter information", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name = name, email = email)
            val status = sqliteHelper.updateStudent(std)

            if (status > -1) {
                Toast.makeText(this, "Student Updated ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}