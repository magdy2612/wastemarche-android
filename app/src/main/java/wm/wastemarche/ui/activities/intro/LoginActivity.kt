package wm.wastemarche.ui.activities.intro

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import wm.wastemarche.R
import wm.wastemarche.services.http.Response
import wm.wastemarche.services.http.authentication.LoginApi.LoginApi
import wm.wastemarche.services.http.authentication.LoginApi.LoginApiProtocol
import wm.wastemarche.ui.activities.main.MainActivity

class LoginActivity : AppCompatActivity(), LoginApiProtocol {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onLoginButtonClicks(view: View) {
        val login = findViewById<EditText>(R.id.loginTextField)
        val password = findViewById<EditText>(R.id.passwordTextField)
        val loginApi = LoginApi(this)
        loginApi.start(login.text.toString(), password.text.toString())
    }

    fun onForgotPasswordButtonClicks(view: View) {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    fun onRegisterButtonClicks(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun loginApiCompleted() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun verifyAccount(code: String?) {
        ValidateActivity.sharedCode = code
        val intent= Intent(this, ValidateActivity::class.java)
        startActivity(intent)
    }

    override fun loginApiFailed(error: String) {
        Response.log(error)
    }

    override fun forgotPasswordCompleted() {
    }
}
