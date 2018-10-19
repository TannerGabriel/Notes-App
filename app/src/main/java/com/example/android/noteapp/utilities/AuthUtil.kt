package com.example.android.noteapp.utilities

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.android.noteapp.MainActivity
import com.google.firebase.auth.FirebaseAuth
import android.app.Activity



/**
 * This class contains all the functions needed for Authentication of the user
 */

class AuthUtil {
    companion object {

        /**
         * This method signs in users using Firebase Auth
         *
         * @param email The email of the user that is trying to sign in
         * @param password The password of the user that is trying to sign in
         * @param content The context of the Activity where the signin is happening
         */
        fun signIn(email: String, password: String, context: Context) {
            var mAuth = FirebaseAuth.getInstance()

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Succesfully logged in!", Toast.LENGTH_SHORT).show()

                        //Open MainActivity
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Error while logging in!", Toast.LENGTH_SHORT).show()
                    }
        }

        /**
         * This method creates a user Account in Firebase Auth
         *
         * @param email The email of the user that is trying to sign up
         * @param password The password of the user that is trying to sign up
         * @param content The context of the Activity where the sign up is happening
         */
        fun signUp(email: String, password: String, context: Context) {
            var mAuth = FirebaseAuth.getInstance()

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Succesfully signed up!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Error while signing up: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
        }

        /**
         * Logging out the User
         */
        fun logout() {
            var mAuth = FirebaseAuth.getInstance()

            mAuth.signOut()
        }

        /**
         * Get the current AuthState
         */
        fun getAuthState(): Boolean {
            var mAuth = FirebaseAuth.getInstance()

            return mAuth.currentUser != null
        }


    }
}

