package com.abc.wcapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.parent_payment.*
import java.text.SimpleDateFormat
import java.util.*

class ParentPayment : AppCompatActivity() {

    lateinit var amountEt: EditText
    lateinit var noteEt: EditText
    lateinit var nameEt: EditText
    lateinit var upiIdEt: EditText
    lateinit var send: Button
    private val UPI_PAYMENT = 0
//    private var batch: String =""

    val database = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_payment)

        initializeViews()




        send.setOnClickListener {

            //Getting the values from the EditTexts

            val amount = amountEt.text.toString()
            val note = noteEt.text.toString()
            val name = nameEt.text.toString()
            val upiId = upiIdEt.text.toString()
            payUsingUpi(amount, upiId, name, note)

        }
    }

    private fun initializeViews() {
        send = findViewById(R.id.send)
        amountEt = findViewById(R.id.amount_et)
        noteEt = findViewById(R.id.note)
        nameEt = findViewById(R.id.name)
        upiIdEt = findViewById(R.id.upi_id)
    }

    private fun payUsingUpi(amount: String, upiId: String, name: String, note: String) {

        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", note)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()


        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri

        // will always show a dialog to user to choose an app
        val chooser = Intent.createChooser(upiPayIntent, "Pay with")

        // check if intent resolves
        if (null != chooser.resolveActivity(packageManager)) {
            startActivityForResult(chooser, UPI_PAYMENT)
        } else {
            Toast.makeText(this@ParentPayment, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            UPI_PAYMENT -> if (Activity.RESULT_OK == resultCode || resultCode == 11) {
                if (data != null) {
                    val trxt = data.getStringExtra("response")
                    Log.d("UPI", "onActivityResult: $trxt")
                    val dataList = ArrayList<String>()
                    dataList.add(trxt)
                    upiPaymentDataOperation(dataList)
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null")
                    val dataList = ArrayList<String>()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            } else {
                Log.d("UPI", "onActivityResult: " + "Return data is null") //when user simply back without payment
                val dataList = ArrayList<String>()
                dataList.add("nothing")
                upiPaymentDataOperation(dataList)
            }
        }
    }

    private fun upiPaymentDataOperation(data: ArrayList<String>) {
        if (isConnectionAvailable(this@ParentPayment)) {
            var str: String? = data[0]
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str!!)
            var paymentCancel = ""
            var status = ""
            var approvalRefNo = ""
            val response = str.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in response.indices) {
                val equalStr = response[i].split("=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                if (equalStr.size >= 2) {
                    if (equalStr[0].toLowerCase() == "Status".toLowerCase()) {
                        status = equalStr[1].toLowerCase()
                    } else if (equalStr[0].toLowerCase() == "ApprovalRefNo".toLowerCase() || equalStr[0].toLowerCase() == "txnRef".toLowerCase()) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }

            when {
                status == "success" -> {
                    //Code to handle successful transaction here.

                    Log.d("UPI", "responseStr: $approvalRefNo")
                    studentPassbook()
                    startActivity(Intent(this,ThankYouActivity::class.java))
                    Toast.makeText(this@ParentPayment, "Transaction successful.", Toast.LENGTH_SHORT).show()
                }
                "Payment cancelled by user." == paymentCancel -> Toast.makeText(this@ParentPayment, "Payment cancelled by user.", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this@ParentPayment, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@ParentPayment, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun studentPassbook(){


            val myRefpassbook = database.getReference("Student Passbook")
            var date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            var studentpassbook = StudentPassbookItem(
                sender_name.text.toString(),
                sender_batch.text.toString(),
                amountEt.text.toString(),
                noteEt.text.toString(),
                date
            )

            myRefpassbook.push().setValue(studentpassbook)
    }



    companion object {

        fun isConnectionAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected
                && netInfo.isConnectedOrConnecting
                && netInfo.isAvailable) {
                return true
            }
            return false
        }
    }


}
