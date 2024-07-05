package com.aditya.a_kart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.a_kart.Model.AddressModel
import com.aditya.a_kart.Model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MyViewModel : ViewModel() {


    private val _authState = MutableLiveData<Boolean>()
    val authState: LiveData<Boolean> get() = _authState

    val _getData = MutableLiveData<UserModel>()
    val _getAddress= MutableLiveData<List<AddressModel>>()


    fun authenticateUsers(email: String, password: String, context: Context) {
        GlobalScope.launch {
            val auth = Firebase.auth
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    _authState.postValue(true)
                } else {
                    _authState.postValue(false)
                }
            }
        }
    }


    fun createUsers(name: String, email: String, password: String, context: Context) {

        GlobalScope.launch {
            val auth = Firebase.auth
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val docId = FirebaseAuth.getInstance().currentUser!!.uid
                    val user = UserModel(name, email, password, docId)
                    SaveDataInDB(user, context)
                    _authState.postValue(true)
                } else {
                    _authState.postValue(false)
                }
            }
        }
    }

    fun SaveDataInDB(users: UserModel, context: Context) {
        GlobalScope.launch {
            val db = Firebase.firestore
            db.collection("Users").document(users.docId).set(users).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toasty.success(context, "Saved", Toasty.LENGTH_SHORT).show()
                } else {
                    Toasty.error(context, "Not Saved", Toasty.LENGTH_SHORT).show()
                }
            }.await()
        }
    }

    fun getUserData() {
        val db = Firebase.firestore
        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnSuccessListener {
                val name = it.get("name").toString()

            }
    }


    fun getUserProfileData() {

        val db = Firebase.firestore
        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnSuccessListener {
                val user = UserModel(
                    it.get("name").toString(),
                    it.get("email").toString(),
                    it.get("password").toString(),
                    it.get("docId").toString()
                )
                _getData.value = user
            }
    }


    fun setAddress(address: AddressModel) {
        val db = Firebase.firestore

        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("Address").document("Address").set(address).addOnCompleteListener{
                if (it.isSuccessful){
                    _authState.postValue(true)
                }else{
                    _authState.postValue(false)
                }
            }
    }

    fun getAddress(){
        val db = Firebase.firestore
        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("Address").addSnapshotListener { value, error ->
               val data=value?.toObjects(AddressModel::class.java)
                _getAddress.value=data!!

            }
    }


}