package com.example.mye_pargne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn = findViewById<Button>(R.id.btConnexion)
        val login = findViewById<TextInputEditText>(R.id.login)
        val password = findViewById<TextInputEditText>(R.id.password)

        btn.setOnClickListener{

            findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
            val loginResponse = AuthentificationService.userAccountRoutes.logIn(login.text.toString(), password.text.toString() )

            val jwtToken: JWTToken = JWTToken.getInstance(this@LoginActivity)

            loginResponse.enqueue( object : Callback<AuthentificationResult> {
                override fun onResponse (call: Call<AuthentificationResult>, response: Response<AuthentificationResult>) {

                    val body = response.body()
                    if (response.code() == 200 && (body?.token != null && body.token != "")) {
                        jwtToken.setTokenValue(body.token)

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        jwtToken.setTokenValue("")
                        Tools.displayError(this@LoginActivity, "Error during login, please try again")
                    }
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<AuthentificationResult>, t: Throwable) {
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                    jwtToken.setTokenValue("")
                    Tools.displayError(
                        this@LoginActivity,
                        "Error communicating with the server, please try again"
                    )
                }
            })


        }
    }
}