package com.example.android.noteapp.signin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.android.noteapp.MainActivity
import com.example.android.noteapp.R
import com.example.android.noteapp.signup.SignUpActivity
import com.example.android.noteapp.utilities.AuthUtil
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //Sign in the user if the account information is correct
        signin_button.setOnClickListener {
            val email = email_edittext.text.toString()
            val password = password_edittext.text.toString()

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                AuthUtil.signIn(email, password, this)
            }else{
                Toast.makeText(this, "Make sure you filled out both fields!", Toast.LENGTH_SHORT).show()
            }
        }

        //Send the user to the register page
        need_a_account_textview.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }




}
