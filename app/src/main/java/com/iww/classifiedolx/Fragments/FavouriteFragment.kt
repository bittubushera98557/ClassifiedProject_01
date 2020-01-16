package com.iww.classifiedolx.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iww.classifiedolx.Fragments.backpressed.RootFragment
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.LoginSignupScreen
import com.iww.classifiedolx.MainActivity
import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.SharedPref
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.VLoveApiResp
import com.iww.classifiedolx.api.interfaces.ApiService
import kotlinx.android.synthetic.main.fragment_favourite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FavouriteFragment: Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.tv_login ->
            {
                startActivity(Intent(context,LoginSignupScreen::class.java))

            }
        }

    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var ctx : Context? = null

    var sharedPref:SharedPref?=null
    @Inject
    internal lateinit var apiService: ApiService
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
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPref(context)
        if (sharedPref!!.userId == "") {
            ll_notLoggedIn.visibility = View.VISIBLE
            ll_loggedInVw.visibility = View.GONE
        } else {
            ll_loggedInVw.visibility = View.VISIBLE
            ll_notLoggedIn.visibility = View.GONE
        }
        tv_login.setOnClickListener(this)
        setTitleBarData()
      //  callGenerateOTP("9855748751")
    }
   /* private fun     callGenerateOTP(strPhone: String)
    {
        apiService!!.generateOTP(strPhone).
            enqueue(object : Callback<VLoveApiResp.GenerateOTPResp> {
                override fun onResponse(call: Call<VLoveApiResp.GenerateOTPResp>, response: Response<VLoveApiResp.GenerateOTPResp>) {
                    Log.e( "callGenerateOTP", "response   $response")


                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                        dialog.cancel()                    } else {

                    }

                    Toast.makeText(context,""+response.body()!!.message, Toast.LENGTH_LONG).show()


                }
                override fun onFailure(call: Call<VLoveApiResp.GenerateOTPResp>, t: Throwable) {
                    t.printStackTrace()

                    //swipe_refresh.isRefreshing = false
                }
            })

    }*/
    private fun setTitleBarData() {
       // val toolbar = view!!.findViewById<View>(R.id.toolbar) as Toolbar
          // (activity as AppCompatActivity).setSupportActionBar(toolbar)

       /* val actionBar = (activity as AppCompatActivity).getSupportActionBar()
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        // add back arrow to toolbar
        if ((activity as AppCompatActivity).getSupportActionBar() != null) {
            (activity as AppCompatActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).getSupportActionBar()!!.setDisplayShowHomeEnabled(true)

        }*/
     //   toolbar.setNavigationOnClickListener(View.OnClickListener { activity!!.onBackPressed() })
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.ctx = context
      ((context!! as Activity).application as AppController).component.inject(this@FavouriteFragment)
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
            FavouriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
