package com.iww.classifiedolx

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.iww.classifiedolx.Utilities.AlertMessage
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import kotlinx.android.synthetic.main.activity_login_signup_screen.*
import kotlinx.android.synthetic.main.progress_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern
import javax.inject.Inject
import android.R.attr.password
import android.provider.Telephony.Carriers.PASSWORD
import android.text.Editable
import android.text.TextUtils
import com.iww.classifiedolx.Utilities.SharedPref
import kotlinx.android.synthetic.main.popup_forget_psd.*
import okhttp3.FormBody
import okhttp3.RequestBody
import java.net.URLEncoder


class LoginSignupScreen : AppCompatActivity(), View.OnClickListener {
    @Inject
    internal lateinit var apiService: ApiService
    internal var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    var strEmail = ""
    var strPhone = ""
    var alertMessage = AlertMessage()
    var sharedPref: SharedPref? = null

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.tv_gotoLogin -> {
                ll_loginVw.visibility = View.VISIBLE
                ll_regVw.visibility = View.GONE
            }
            R.id.tv_gotoSignup -> {
                ll_loginVw.visibility = View.GONE
                ll_regVw.visibility = View.VISIBLE
            }
            R.id.tv_submitLogin -> {
                checkLoginFields()
            }
            R.id.tv_submitReg -> {
                checkRegFields()
            }
            R.id.tv_forgetPsd -> {
                showForgetPsdPopup()
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as AppController).component.inject(this@LoginSignupScreen)
        setContentView(R.layout.activity_login_signup_screen)
        ll_loginVw.visibility = View.VISIBLE
        ll_regVw.visibility = View.GONE

        tv_submitLogin.setOnClickListener(this)
        tv_gotoLogin.setOnClickListener(this)
        tv_submitReg.setOnClickListener(this)
        tv_gotoSignup.setOnClickListener(this)
        tv_loginWithGmail.setOnClickListener(this)
        tv_loginWithFB.setOnClickListener(this)
        tv_forgetPsd.setOnClickListener(this)
        sharedPref = SharedPref(this@LoginSignupScreen)
    }

    private fun checkRegFields() {

        var strName = et_regName.text.toString().replace(" ".toRegex(), "")
        var strEmail = et_regEmail.text.toString().replace(" ".toRegex(), "")
        var strPsd = et_regPsd.text.toString().replace(" ".toRegex(), "")
        var strPhone = et_regPhone.text.toString().replace(" ".toRegex(), "")

        if ( TextUtils.isEmpty(strName))
            Utility.snackBar(tv_loginWithFB, "Please enter name")
        else if ( TextUtils.isEmpty(strEmail))
            Utility.snackBar(tv_loginWithFB, "Please enter email")
        else if (!validateEmailId(strEmail))
            Utility.snackBar(tv_loginWithFB, "Please enter valid Email ")
        else if ( TextUtils.isEmpty(strPhone))
            Utility.snackBar(tv_loginWithFB, "Please enter mobile number")
        else if (strPhone.length!=10)
            Utility.snackBar(tv_loginWithFB, "Entered mobile number is wrong")

        else if ( TextUtils.isEmpty(strPsd))
            Utility.snackBar(tv_loginWithFB, "Enter password")
  else if (Utility.isConnected(applicationContext!!)) {
                callRegisterApi("register",  ""+et_regName.text, ""+et_regEmail.text, ""+et_regPhone.text, ""+et_regPsd.text)
            } else {
                Utility.snackBar(tv_loginWithFB, "Please Check Internet Connection")
        }
    }

    private fun callRegisterApi(strApiAction: String, name: String, email: String, phone: String, password: String) {

        alertMessage.showCommonDialog(this@LoginSignupScreen)

        apiService.registerApi(strApiAction, name, email,phone,password).enqueue(object : Callback<AllApiResponse.LoginResp> {
            override fun onResponse(
                call: Call<AllApiResponse.LoginResp>,
                response: Response<AllApiResponse.LoginResp>
            ) {
                Utility.snackBar(tv_loginWithFB, "" + response.body()!!.message)
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    var dataModel = response.body()!!.data[0]
                    sharedPref!!.userId = dataModel.id
                    sharedPref!!.email = dataModel.emailid
                    sharedPref!!.password = dataModel.password
                    sharedPref!!.phone = dataModel.phone
                    sharedPref!!.name = dataModel.name

                    var intentMain = Intent(this@LoginSignupScreen, MainActivity::class.java)
                    intentMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intentMain)
                    finish()
                } else {

                }

                alertMessage.cancelDialog()

            }

            override fun onFailure(call: Call<AllApiResponse.LoginResp>, t: Throwable) {
                t.printStackTrace()
                alertMessage.cancelDialog()
            }
        })
    }


    private fun checkLoginFields() {
        var strEtVal = et_loginEmail.text.toString().replace(" ".toRegex(), "")
        var strPsd = et_loginPsd.text.toString()
        val atleastOneAlpha = strEtVal.matches(".*[a-zA-Z]+.*".toRegex())
        if (atleastOneAlpha == true) {
            if (!validateEmailId(strEtVal))
                Utility.snackBar(tv_loginWithFB, "Please enter valid Email or Phone number")
            else {
                if (strEtVal.contains("@")) {
                    strEmail = strEtVal
                } else {
                    Utility.snackBar(tv_loginWithFB, "Please enter valid email address")
                }
                if (strPsd == "") {
                    Utility.snackBar(tv_loginWithFB, "Please enter password")
                } else if (Utility.isConnected(applicationContext!!)) {
                    callLoginApi("login", strEmail, strPsd)
                } else {
                    Utility.snackBar(tv_loginWithFB, "Please Check Internet Connection")

                }
/*                    if (SharedPref.getDeviceToken(this@LoginSignupScreen).equalsIgnoreCase("")) {
                        Utility.snackBar(tv_loginWithFB, "Please try again")
//                        alert.showErrorPopup(this@LoginActivity, "Alert", "Please try again")
                        registerBroadCast()
                    } else {
                        callLogin()
                    }*/

            }

        } else {
            if (strEtVal.length != 10) {
                Utility.snackBar(tv_loginWithFB, "Please enter valid phone number")
            } else {
                strPhone = strEtVal
            }
            if (strPhone.trim().equals("")) {
                Utility.snackBar(tv_loginWithFB, "Please enter valid phone number")
            } else if (Utility.isConnected(applicationContext!!)) {
                callLoginApi("login", strPhone, strPsd)
            } else {
                Utility.snackBar(tv_loginWithFB, "Please Check Internet Connection")

            }

        }
    }

    private fun callLoginApi(strApiAction: String, strEmail: String, strPsd: String) {
        Log.e("" + strApiAction, "strEmail=" + strEmail)

          alertMessage.showCommonDialog(this@LoginSignupScreen)

        var encodedItem = URLEncoder.encode(strEmail, "utf-8");
        apiService.loginApi(strApiAction, encodedItem, strPsd).enqueue(object : Callback<AllApiResponse.LoginResp> {
            override fun onResponse(
                call: Call<AllApiResponse.LoginResp>,
                response: Response<AllApiResponse.LoginResp>
            ) {
                Utility.snackBar(tv_loginWithFB, "" + response.body()!!.message)
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    var dataModel = response.body()!!.data[0]
                    sharedPref!!.userId = dataModel.id
                    sharedPref!!.email = dataModel.emailid
                    sharedPref!!.password = dataModel.password
                    sharedPref!!.phone = dataModel.phone
                    sharedPref!!.name = dataModel.name

                    var intentMain = Intent(this@LoginSignupScreen, MainActivity::class.java)
                    intentMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intentMain)
                    finish()
                } else {

                }

                alertMessage.cancelDialog()

            }

            override fun onFailure(call: Call<AllApiResponse.LoginResp>, t: Throwable) {
                t.printStackTrace()
                alertMessage.cancelDialog()
            }
        })
    }

    private fun validateEmailId(email: String): Boolean {
        // TODO Auto-generated method stub
        val EMAIL_ADDRESS_PATTERN = Pattern
            .compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "("
                        + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}"
                        + ")+"
            )

        val validEmail = EMAIL_ADDRESS_PATTERN.matcher(email)
            .matches()
        // validate email address
        return if (!validEmail) {
            false
            // return false;
        } else true
    }

    private fun showForgetPsdPopup() {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.popup_forget_psd)
        var tv_submitForgetPsd = dialog.findViewById(R.id.tv_submitForgetPsd) as TextView
        var iv_close = dialog.findViewById(R.id.iv_close) as ImageView

        var et_email = dialog.findViewById(R.id.et_email) as EditText

        tv_submitForgetPsd.setOnClickListener(View.OnClickListener {
            var strEmail = et_email.text.replace(" ".toRegex(), "").toString()
            et_email.setText(strEmail)

            if (strEmail.trim().equals("")) {
                Utility.snackBar(tv_loginWithFB, "Please enter email or phone number")

            } else if (!isEmailValid(strEmail)) {

                Utility.snackBar(tv_loginWithFB, "Enter a valid Email")
            } else if (Utility.isConnected(applicationContext!!)) {
                dialog.cancel()
                callForgetPsd("forget_password", strEmail, dialog)
            } else {
                Utility.snackBar(tv_loginWithFB, "Please Check Internet Connection")

            }
        })

        iv_close.setOnClickListener(View.OnClickListener {
            dialog.cancel()

        })

        var lWindowParams = WindowManager.LayoutParams()
        lWindowParams.copyFrom(dialog.window.attributes)
        lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window.attributes = lWindowParams
        dialog.show()
    }


    private fun callForgetPsd(strApi: String, strEmail: String, dialog: Dialog) {


        alertMessage.showCommonDialog(this@LoginSignupScreen)
        apiService!!.forgetPassword(strApi, strEmail).enqueue(object : Callback<AllApiResponse.CommonRes> {
            override fun onResponse(
                call: Call<AllApiResponse.CommonRes>,
                response: Response<AllApiResponse.CommonRes>
            ) {
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    dialog.cancel()
                } else {
                    Utility.snackBar(tv_loginWithFB, "" + response.body()!!.message)
                }
                Toast.makeText(this@LoginSignupScreen, "" + response.body()!!.message, Toast.LENGTH_LONG).show()
                //   onHideProgressBar(false)
                alertMessage.cancelDialog()
            }

            override fun onFailure(call: Call<AllApiResponse.CommonRes>, t: Throwable) {
                t.printStackTrace()
                //     onHideProgressBar(false)
                alertMessage.cancelDialog()
                //swipe_refresh.isRefreshing = false
            }
        })


    }

    fun onHideProgressBar(showOrHide: Boolean) {
        if (showOrHide)
            progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return if (email.matches(emailPattern.toRegex())) {
            true
        } else false
    }

}
