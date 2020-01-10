package com.iww.classifiedolx.Fragments

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.iww.classifiedolx.Fragments.backpressed.RootFragment
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener

import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.Utilities.Utility.isConnected
import kotlinx.android.synthetic.main.activity_login_signup_screen.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFrag : Fragment(), View.OnClickListener {
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

    private fun checkRegFields() {


    }

    private fun checkLoginFields() {


    }

    private fun showForgetPsdPopup()
         {

            var dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.popup_forget_psd)
            var tv_submitForgetPsd = dialog.findViewById(R.id.tv_submitForgetPsd) as TextView
            var iv_close= dialog.findViewById(R.id.iv_close) as ImageView

            var et_email = dialog.findViewById(R.id.et_email) as EditText

            tv_submitForgetPsd.setOnClickListener(View.OnClickListener {
                var strEmail = et_email.text.replace(" ".toRegex(), "").toString()
                et_email.setText(strEmail)
                if (strEmail.trim().equals("")) {
                    Utility.snackBar(tv_loginWithFB        ,"Please enter email or phone number")

                } else if (!isEmailValid(strEmail)) {

                    Utility.snackBar(tv_loginWithFB,"Enter a valid Email")
                }else if (isConnected(context!!)) {

                       // callForgetPsd("Individual", strEmail,dialog)
                    } else {
                    Utility.snackBar(tv_loginWithFB,"Please Check Internet Connection")

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



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    internal var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_login_signup_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ll_loginVw.visibility = View.VISIBLE
        ll_regVw.visibility = View.GONE

        tv_submitLogin.setOnClickListener(this)
        tv_gotoLogin.setOnClickListener(this)
        tv_submitReg.setOnClickListener(this)
        tv_gotoSignup.setOnClickListener(this)
        tv_loginWithGmail.setOnClickListener(this)
        tv_loginWithFB.setOnClickListener(this)
        tv_forgetPsd.setOnClickListener(this)

    }
    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return if (email.matches(emailPattern.toRegex())) {
            true
        } else false
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
