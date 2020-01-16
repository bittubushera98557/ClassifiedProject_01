package com.iww.classifiedolx.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.gson.Gson
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
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
import kotlinx.android.synthetic.main.fragment_my_ads.*
import kotlinx.android.synthetic.main.item_advertisement.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyAdsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var ctx: Context? = null
    private var myAdsItemData: MutableList<AllApiResponse.AdverResp.AdverDataModel>? = null
var TAG="MyAdsFragment "
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

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdsItemData = mutableListOf()
        sharedPref = SharedPref(context)
        if (sharedPref!!.userId == "") {
            ll_notLoggedIn.visibility = View.VISIBLE
            ll_loggedInVw.visibility = View.GONE
        } else {
            ll_loggedInVw.visibility = View.VISIBLE
            ll_notLoggedIn.visibility = View.GONE
            fetchAdvertiseList("get_Ad_by_UserId")
        }

        rv_myAddedAds.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_myAddedAds.layoutManager = mLayoutManager

        rv_myAddedAds.setUp(myAdsItemData!!, R.layout.item_advertisement, { it1 ->
            this.tv_advertiseDate.text = it1.subcategory
            val circularProgressDrawable = CircularProgressDrawable(ctx as Activity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

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
                .placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(this.iv_advertiseImg)


            tv_advertisePrice.text = it1.title
            tv_advertiseDate.text = it1.date
            tv_advertiseDesc.text = it1.discrption

            var strFeatured = ""
       try {
           if (it1.featField1.equals("1")) {
               if (!it1.field1.equals("")) {
                   strFeatured = strFeatured + it1.field1
                   if (!it1.fieldSecond1.equals(""))
                       strFeatured = strFeatured + " " + it1.fieldSecond1
                   strFeatured = strFeatured + " - "

               }
           }
           if (it1.featField2.equals("1")) {
               if (!it1.field2.equals("")) {
                   strFeatured = strFeatured + it1.field2
                   if (!it1.fieldSecond2.equals(""))
                       strFeatured = strFeatured + " " + it1.fieldSecond2
                   strFeatured = strFeatured + " - "
               }
           }

           if (it1.featField3.equals("1")) {
               if (!it1.field3.equals("")) {
                   strFeatured = strFeatured + it1.field3
                   if (!it1.fieldSecond3.equals(""))
                       strFeatured = strFeatured + " " + it1.fieldSecond3
                   strFeatured = strFeatured + " - "
               }
           }
           if (it1.featField4.equals("1")) {
               if (!it1.field4.equals("")) {
                   strFeatured = strFeatured + it1.field4
                   if (!it1.fieldSecond4.equals(""))
                       strFeatured = strFeatured + " " + it1.fieldSecond4
                   strFeatured = strFeatured + " - "
               }
           }

           if (it1.featField5.equals("1")) {
               if (!it1.field5.equals("")) {
                   strFeatured = strFeatured + it1.field5
                   if (!it1.fieldSecond5.equals(""))
                       strFeatured = strFeatured + " " + it1.fieldSecond5
                   strFeatured = strFeatured + " - "
               }
           }
           if (it1.featField6.equals("1")) {
               if (!it1.field6.equals("")) {
                   strFeatured = strFeatured + it1.field6
                   if (!it1.fieldSecond6.equals(""))
                       strFeatured = strFeatured + " " + it1.fieldSecond6
                   strFeatured = strFeatured + " - "
               }
           }
       }
catch (exp:Exception)
{

}
            tv_advertiseMoreInfo.text=strFeatured

            this.ll_advertItemLinear.setOnClickListener {
                  Utility.enterNextReplaceFragment(
                      R.id.fl_myAdsFrag,
                      AdvertisementDetailsFrag.newInstance(  it1 ,""),
                      (ctx as MainActivity).supportFragmentManager
                  )
            }
        }, { view1: View, i: Int -> })
        //get_all_ad($con,$CatId,$SubCatId)
        rv_myAddedAds.layoutManager = LinearLayoutManager(context)


    }

    private fun fetchAdvertiseList(strApi: String) {

        apiService!!.fetchAdsByUserIdList(strApi, sharedPref!!.userId)
            .enqueue(object : Callback<AllApiResponse.AdverResp> {
                override fun onResponse(
                    call: Call<AllApiResponse.AdverResp>,
                    response: Response<AllApiResponse.AdverResp>
                ) {
                    Log.e("fetchAdvertiseList res", "" + Gson().toJson(response.body()))
                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                        myAdsItemData!!.clear()
                        myAdsItemData!!.addAll(response.body()!!.data)
                        rv_myAddedAds.adapter!!.notifyDataSetChanged()
                        //    swipe_refresh.isRefreshing = false
                    } else {
                        tvNoData.visibility = View.VISIBLE
                        rv_myAddedAds.visibility = View.GONE
                        //swipe_refresh.isRefreshing = false
                    }
                }

                override fun onFailure(call: Call<AllApiResponse.AdverResp>, t: Throwable) {
                    t.printStackTrace()
                    tvNoData.visibility = View.VISIBLE
                    rv_myAddedAds.visibility = View.GONE
                    //swipe_refresh.isRefreshing = false
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_ads, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        ((ctx!! as Activity).application as AppController).component.inject(this@MyAdsFragment)
Log.d( ""+TAG,"onAttach" )
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyAdsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
