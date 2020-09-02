package com.abc.wcapp

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.admin_student_passbook.*

class AdminStudentPassbook: AppCompatActivity(){

    val database = FirebaseDatabase.getInstance()

    val spassbook:ArrayList<StudentPassbookItem> =  ArrayList()


    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_student_passbook)
        progressDialog = ProgressDialog(this@AdminStudentPassbook)


        progressDialog.setTitle("Loading...")
        progressDialog.show()

        val myref = database.getReference("Student Passbook")

        myref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                p0.children.forEach{
                    spassbook.add(StudentPassbookItem(it.child("sname").getValue(String::class.java)!!,
                            it.child("grpname").getValue(String::class.java)!!,
                            it.child("amt").getValue(String::class.java)!!,
                            it.child("note").getValue(String::class.java)!!,
                            it.child("date").getValue(String::class.java)!!))
                }
                spassbook.reverse()
                spassbook.forEach{
                    adapter.add(it)
                }
                recyclerview_showpassbook.adapter = adapter
                progressDialog.dismiss()

            }

        })



    }



}