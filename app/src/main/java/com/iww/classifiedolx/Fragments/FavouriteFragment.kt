package com.iww.classifiedolx.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.gson.Gson
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.LoginSignupScreen
import com.iww.classifiedolx.MainActivity
import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.SharedPref
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AppConstants
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import com.iww.classifiedolx.recyclerview.setUp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ads_done.view.*
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.item_advertisement.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FavouriteFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_login -> {
                startActivity(Intent(context, LoginSignupScreen::class.java))

            }
        }

    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var ctx: Context? = null
    private var favAdsItem: MutableList<AllApiResponse.AdverResp.AdverDataModel>? = null
    var TAG = "FavouriteFragment "
    @Inject
    internal lateinit var apiService: ApiService
    var sharedPref: SharedPref? = null
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

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPref(context)
        if (sharedPref!!.userId == "") {
            ll_notLoggedIn.visibility = View.VISIBLE
            ll_loggedInVw.visibility = View.GONE
        } else {
            ll_loggedInVw.visibility = View.VISIBLE
            ll_notLoggedIn.visibility = View.GONE
            fetchFavouriteAdsList("get_fav_ads")
            if(!Utility.isConnected(ctx!!))
                Utility.snackBar(rv_favAds, "Please check internet connection ")
        }
        favAdsItem = mutableListOf()
        tv_login.setOnClickListener(this)
        rv_favAds.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_favAds.layoutManager = mLayoutManager

        rv_favAds.setUp(favAdsItem!!, R.layout.item_advertisement, { it1 ->
            val circularProgressDrawable = CircularProgressDrawable(ctx as Activity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            if (it1.message.equals("")) {
                var strImgUrl = ""
                if (!it1.imageUpload1.equals("")) {
                    strImgUrl = it1.imageUpload1
                } else if (!it1.imageUpload2.equals("")) {
                    strImgUrl = it1.imageUpload2
                } else if (!it1.imageUpload3.equals("")) {
                    strImgUrl = it1.imageUpload3

                } else if (!it1.imageUpload4.equals("")) {
                    strImgUrl = it1.imageUpload4
                }
                Picasso.with(context).load(Uri.parse(AppConstants.IMAGE_Ad_Images + strImgUrl))
                    .placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(iv_advertiseImg)


                tv_advertisePrice.text = it1.title
                tv_advertiseDate.text = it1.date
                tv_advertiseDesc.text = it1.discrption

                var strFeatured = ""
                tv_advertisePrice.text = it1.title
                tv_advertiseDate.text = it1.date

                try {
                    if (it1.featField1.equals("1")) {
                        if (!it1.field1.equals("")) {
                            strFeatured = strFeatured + it1.field1
                            if (!it1.fieldSecond1.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond1
                            strFeatured = " - " + strFeatured

                        }
                    }
                    if (it1.featField2.equals("1")) {
                        if (!it1.field2.equals("")) {
                            strFeatured = strFeatured + it1.field2
                            if (!it1.fieldSecond2.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond2
                            strFeatured = " - " + strFeatured

                        }
                    }

                    if (it1.featField3.equals("1")) {
                        if (!it1.field3.equals("")) {
                            strFeatured = strFeatured + it1.field3
                            if (!it1.fieldSecond3.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond3
                            strFeatured = " - " + strFeatured
                        }
                    }
                    if (it1.featField4.equals("1")) {
                        if (!it1.field4.equals("")) {
                            strFeatured = strFeatured + it1.field4
                            if (!it1.fieldSecond4.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond4
                            strFeatured = " - " + strFeatured
                        }
                    }

                    if (it1.featField5.equals("1")) {
                        if (!it1.field5.equals("")) {
                            strFeatured = strFeatured + it1.field5
                            if (!it1.fieldSecond5.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond5
                            strFeatured = " - " + strFeatured
                        }
                    }
                    if (it1.featField6.equals("1")) {
                        if (!it1.field6.equals("")) {
                            strFeatured = strFeatured + it1.field6
                            if (!it1.fieldSecond6.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond6
                            strFeatured = " - " + strFeatured
                        }
                    }
                    if (it1.featField7.equals("1")) {
                        if (!it1.field7.equals("")) {
                            strFeatured = strFeatured + it1.field7
                            if (!it1.fieldSecond7.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond7
                            strFeatured = " - " + strFeatured
                        }
                    }
                    if (it1.featField8.equals("1")) {
                        if (!it1.field8.equals("")) {
                            strFeatured = strFeatured + it1.field8
                            if (!it1.fieldSecond8.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond8
                            strFeatured = " - " + strFeatured
                        }
                    }
                    if (it1.featField9.equals("1")) {
                        if (!it1.field9.equals("")) {
                            strFeatured = strFeatured + it1.field9
                            if (!it1.fieldSecond9.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond9
                            strFeatured = " - " + strFeatured
                        }
                    }
                    if (it1.featField10.equals("1")) {
                        if (!it1.field10.equals("")) {
                            strFeatured = strFeatured + it1.field10
                            if (!it1.fieldSecond10.equals(""))
                                strFeatured = strFeatured + " " + it1.fieldSecond10
                            strFeatured = " - " + strFeatured
                        }
                    }

                } catch (exp: Exception) {

                }
                if (strFeatured.startsWith(" - "))
                    strFeatured = strFeatured.replace(" - ", "")

                if (strFeatured.equals("")) {
                    strFeatured = it1.discrption
                }
                tv_advertiseMoreInfo.text = strFeatured
                iv_favImg.setImageResource(R.drawable.ic_favorite_filled)

                ll_advertItemLinear.setOnClickListener {

                    if (it1.message.equals("")) {
                        Utility.enterNextReplaceFragment(
                            R.id.fl_favouriteFragment,
                            AdvertisementDetailsFrag.newInstance(it1, ""),
                            (ctx as MainActivity).supportFragmentManager
                        )
                    } else {
                        Utility.snackBar(rv_favAds, "" + it1.message)

                    }
                }
            } else {
                iv_favImg.setImageResource(R.drawable.ic_favorite_filled)

                Picasso.with(context).load(Uri.parse("http://sasnagar.co.in/classified/Ad_Images/1579174037479.jpg"))
                    .placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(iv_advertiseImg)
                tv_advertiseMoreInfo.text = it1.message
                tv_advertisePrice.text ="Ads Removed"

            }


            iv_favImg.setOnClickListener {
/*
                var itemPosition = rv_favAds.getChildLayoutPosition(view);
    var item = favAdsItem!!.get(itemPosition);*/
                    callRemoveFromFav(it1 )
            }

        }, { view1: View, i: Int -> })
        rv_favAds.layoutManager = LinearLayoutManager(context)

        swipe_refresh.setOnRefreshListener {
            if (sharedPref!!.userId == "") {
                ll_notLoggedIn.visibility = View.VISIBLE
                ll_loggedInVw.visibility = View.GONE
            } else {
                ll_loggedInVw.visibility = View.VISIBLE
                ll_notLoggedIn.visibility = View.GONE
                fetchFavouriteAdsList("get_fav_ads")
                if(!Utility.isConnected(ctx!!))
                    Utility.snackBar(rv_favAds, "Please check internet connection ")

            }
        }
    }

    private fun callRemoveFromFav(dataModel: AllApiResponse.AdverResp.AdverDataModel )
        {
            swipe_refresh.isRefreshing = true

            apiService!!.addInFav("delete_fav_ads"  ,""+sharedPref!!.userId, dataModel!!.id).enqueue(object : Callback<AllApiResponse.CommonRes> {
                override fun onResponse(
                    call: Call<AllApiResponse.CommonRes>,
                    response: Response<AllApiResponse.CommonRes>
                ) {
                    Log.e(TAG+"callAddInFavApi res", "" + Gson().toJson(response.body())  )
                    swipe_refresh.isRefreshing = false
                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
favAdsItem!!.remove(dataModel)
                        rv_favAds.adapter!!.notifyDataSetChanged()
                      /*  rv_favAds.adapter!!.notifyItemRemoved(dataModel.)
                        */
                    } else {
                    }
                }

                override fun onFailure(call: Call<AllApiResponse.CommonRes>, t: Throwable) {
                    t.printStackTrace()
                    tvNoData.visibility = View.VISIBLE
                    rv_favAds.visibility = View.GONE
                     swipe_refresh.isRefreshing = false
                }
            })

        }



    private fun fetchFavouriteAdsList(strAction: String) {
      swipe_refresh.isRefreshing=true
        apiService!!.getAllFavAdsLst(strAction, sharedPref!!.userId).enqueue(object :
            Callback<AllApiResponse.AdverResp> {
            override fun onResponse(
                call: Call<AllApiResponse.AdverResp>,
                response: Response<AllApiResponse.AdverResp>
            ) {
                Log.e("fetchAdvertiseList res", "" + Gson().toJson(response.body()))
                swipe_refresh.isRefreshing = false
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    favAdsItem!!.clear()
                    favAdsItem!!.addAll(response.body()!!.data)
                    rv_favAds.adapter!!.notifyDataSetChanged()
                    tvNoData.visibility = View.GONE
                    rv_favAds.visibility = View.VISIBLE
                } else {
                    tvNoData.visibility = View.VISIBLE
                    rv_favAds.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<AllApiResponse.AdverResp>, t: Throwable) {
                t.printStackTrace()
                tvNoData.visibility = View.VISIBLE
                rv_favAds.visibility = View.GONE
                swipe_refresh.isRefreshing = false
            }
        })

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


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.ctx = context
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        ((context!! as Activity).application as AppController).component.inject(this@FavouriteFragment)

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
