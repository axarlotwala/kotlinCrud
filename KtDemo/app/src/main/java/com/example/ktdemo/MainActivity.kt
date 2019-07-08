package com.example.ktdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.log

class MainActivity : AppCompatActivity(){

    val fireDb  = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_create.setOnClickListener {
            SaveClient()
        }

        SetClients()

    }

    private fun SaveClient() {

        val reference = fireDb.collection("Mfg").document("abc").collection("clients").document()
        val clientId = reference.id

        var clientName = et_client_name.text.toString().trim()
        var clientAddress  = et_address.text.toString().trim()

        if (clientName.isEmpty()){
            et_client_name.error = "require name"
            return
        }

        if (clientAddress.isEmpty()){
            et_address.error = "require address"
            return
        }


    val client = HashMap<String,Any>()
        client["clientId"] = clientId
        client["clientName"] = clientName
        client["address"] = clientAddress


        reference
            .set(client)
            .addOnSuccessListener { void ->

            }
            .addOnFailureListener { exception ->
                Log.e("getError",exception.toString())
            }
    }

    private fun SetClients(){

        fireDb.collection("Mfg").document("abc").collection("clients")
            .get()
            .addOnSuccessListener { result ->
                val clientList = ArrayList<HashMap<String, Any>>()
                for (document in result) {
                    clientList.add(document.data as HashMap<String, Any>)
                    Log.d("getData", "${document.id} => ${document.data}")

                }


            }
            .addOnFailureListener { exception ->
                Log.d("error", "Error getting documents: ", exception)
            }
    }
}
