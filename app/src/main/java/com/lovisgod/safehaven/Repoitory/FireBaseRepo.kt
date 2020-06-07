package com.lovisgod.safehaven.Repoitory

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lovisgod.safehaven.model.AppEvent
import com.lovisgod.safehaven.model.Users
import org.greenrobot.eventbus.EventBus


class FireBaseRepo() {
    val auth = Firebase.auth
    val db = Firebase.firestore

    fun getCurrentUser(email: String) = db.collection("Users").document(email).get()

    fun  registerUser(email:String, password:String) = auth.createUserWithEmailAndPassword(email, password)


    fun loginUser(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)


   fun saveUserDetails(email:String, name:String, phoneNumber: String) {
       val user = Users.tomap(email = email, name = name, phoneNumber = phoneNumber)
       db.collection("Users").document(email).set(user, SetOptions.merge())
           .addOnFailureListener { e -> println(e.localizedMessage) }
           .addOnSuccessListener { println("success success") }
   }

   fun saveFriendDetails(name:String, phoneNumber: String, userEmail: String) {
       val friend = Users.friendMap(name = name, phone_number = phoneNumber, user_email = userEmail)
       db.collection("Friends").add(friend)
           .addOnFailureListener {
               EventBus.getDefault().post(AppEvent(event = "error", message = "An error occurred"))
           }
           .addOnSuccessListener { EventBus.getDefault().post(AppEvent(event = "success", message = "Friend added successfully")) }
   }


   fun getSafeFriendList(email: String) = db.collection("Friends")
       .whereEqualTo("user_email", email)


}