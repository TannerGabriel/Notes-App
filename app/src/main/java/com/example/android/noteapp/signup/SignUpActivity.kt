package com.example.android.noteapp.signup


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.android.noteapp.MainActivity
import com.example.android.noteapp.R
import com.example.android.noteapp.signin.SignInActivity
import com.example.android.noteapp.utilities.AuthUtil
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Create a account if the account details are correct
        signup_button.setOnClickListener {
            val email = email_signup_edittext.text.toString()
            val password = password_signup_edittext.text.toString()

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                AuthUtil.signUp(email, password, this)

                if(AuthUtil.getAuthState()){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this, "Make sure you filled out both fields!", Toast.LENGTH_SHORT).show()
            }
        }

        //Send the user to the sign in screen
        already_have_an_account_textview.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
