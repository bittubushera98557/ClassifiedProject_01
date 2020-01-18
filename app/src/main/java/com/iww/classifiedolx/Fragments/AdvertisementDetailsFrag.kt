package com.iww.classifiedolx.Fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glide.slider.library.SliderTypes.TextSliderView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.iww.classifiedolx.Fragments.backpressed.RootFragment
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.SharedPref
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AppConstants.IMAGE_Ad_Images
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import kotlinx.android.synthetic.main.fragment_advertisement_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdvertisementDetailsFrag : RootFragment(), View.OnClickListener, OnMapReadyCallback {
    override fun onMapReady(googleMap: GoogleMap?) {

        googleMap!!.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        gMap=googleMap


    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.fl_AdvertisementDetailsFrag->
            {

            }
            R.id.tv_call->
            {
                Utility.callDialog(context as Activity,""+adsDataModel!!.ownerPhone)

            }


        }
    }

    // TODO: Rename and change types of parameters
    // private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var adsDataModel: AllApiResponse.AdverResp.AdverDataModel? = null
    var ctx: Context? = null
    var lati="0"
    var longi="0"
var gMap:GoogleMap?=null
    var TAG="AdvertisementDetailsFrag "
    @Inject
    internal lateinit var apiService: ApiService
    var sharedPref: SharedPref? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //          param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advertisement_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPref(context)

        if(!Utility.isConnected(ctx!!))
            Utility.snackBar(nsView, "Please check internet connection ")

             getAdsDetailById()

        swipe_refresh.setOnRefreshListener {
            if(!Utility.isConnected(ctx!!)) {
                swipe_refresh.isRefreshing = false
                Utility.snackBar(nsView, "Please check internet connection ")
                onBackPressed()

            }
             getAdsDetailById()
         }
        tv_call.setOnClickListener(this)
        fl_AdvertisementDetailsFrag.setOnClickListener(this)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getAdsDetailById() {
        swipe_refresh.isRefreshing = true
        apiService!!.getAdsDetailById("get_ads_detail_by_id", adsDataModel!!.id)
            .enqueue(object : Callback<AllApiResponse.AdverFullDetailResp> {
                override fun onResponse(
                    call: Call<AllApiResponse.AdverFullDetailResp>,
                    response: Response<AllApiResponse.AdverFullDetailResp>
                ) {
                    Log.e("getAdsDetailById res", "" + Gson().toJson(response.body()))
                    swipe_refresh.isRefreshing = false
                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                        var fullDetailsModel = response.body()!!.data[0]

                        val url_maps = HashMap<String, String>()
                        if (!fullDetailsModel.imageUpload1.equals("")) {
                            url_maps.put("Hannibal1", IMAGE_Ad_Images + fullDetailsModel.imageUpload1)
                        }
                        if (!fullDetailsModel.imageUpload2.equals("")) {
                            url_maps.put("Hannibal2", IMAGE_Ad_Images + fullDetailsModel.imageUpload2)
                        }
                        if (!fullDetailsModel.imageUpload3.equals("")) {
                            url_maps.put("Hannibal3", IMAGE_Ad_Images + fullDetailsModel.imageUpload3)
                        }
                        if (!fullDetailsModel.imageUpload4.equals("")) {
                            url_maps.put("Hannibal4", IMAGE_Ad_Images + fullDetailsModel.imageUpload4)
                        }

                        for (item in url_maps.values) {
                            val sliderView = TextSliderView(ctx as Activity)
                            sliderView.image(item).setProgressBarVisible(true)
                            slider.addSlider(sliderView)
                        }
                        tv_advertiseLoc.text = fullDetailsModel.areaName + ", " + fullDetailsModel.cityName
                        tv_advertiseDate.text = fullDetailsModel.date
                        tv_title.text = fullDetailsModel.title
                        tv_advertiseDesc.text = fullDetailsModel.discrption

                        var strFieldsValues = ""
                        try {

                            if (!fullDetailsModel.field1.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field1
                                if (!fullDetailsModel.fieldSecond1.equals("") && !fullDetailsModel.fieldSecond1.equals("null"))
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond5
                                strFieldsValues = strFieldsValues + "\n"


                            }
                            if (!fullDetailsModel.field2.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field2
                                if (!fullDetailsModel.fieldSecond2.equals("") && !fullDetailsModel.fieldSecond2.equals("null"))
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond2
                                strFieldsValues = strFieldsValues + "\n"


                            }

                            if (!fullDetailsModel.field3.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field3
                                if (!fullDetailsModel.fieldSecond3.equals("") && !fullDetailsModel.fieldSecond3.equals("null"))
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond3
                                strFieldsValues = strFieldsValues + "\n"

                            }
                            if (!fullDetailsModel.field4.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field4
                                if (!fullDetailsModel.fieldSecond4.equals("") && !fullDetailsModel.fieldSecond4.equals("null"))
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond4
                                strFieldsValues = strFieldsValues + "\n"

                            }

                            if (!fullDetailsModel.field5.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field5
                                if (!fullDetailsModel.fieldSecond5.equals("") && !fullDetailsModel.fieldSecond5.equals("null"))
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond5
                                strFieldsValues = strFieldsValues + "\n"
                            }

                            if (!fullDetailsModel.field6.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field6
                                if (!fullDetailsModel.fieldSecond6.equals("") && !fullDetailsModel.fieldSecond6.equals("null"))
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond6
                                strFieldsValues = strFieldsValues + "\n"

                            }
                            if (!fullDetailsModel.field7.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field7
                                if (!fullDetailsModel.fieldSecond7.equals("") && !fullDetailsModel.fieldSecond7.equals("null"))
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond7
                                strFieldsValues = strFieldsValues + "\n"

                            }

                            if (!fullDetailsModel.field8.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field8
                                if (!fullDetailsModel.fieldSecond8.equals("") && !fullDetailsModel.fieldSecond8.equals("null"))
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond8
                                strFieldsValues = strFieldsValues + "\n"

                            }
                            if (!fullDetailsModel.field9.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field9
                                if (!fullDetailsModel.fieldSecond9.equals("") && !fullDetailsModel.fieldSecond9.equals("null"))
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond9

                                strFieldsValues = strFieldsValues + "\n"
                            }
                            if (!fullDetailsModel.field10.equals("")) {
                                strFieldsValues = strFieldsValues + fullDetailsModel.field10
                                if (!fullDetailsModel.fieldSecond10.equals("") && !fullDetailsModel.fieldSecond10.equals(
                                        "null"
                                    )
                                )
                                    strFieldsValues = strFieldsValues + " > " + fullDetailsModel.fieldSecond10

                                strFieldsValues = strFieldsValues + "\n"
                            }


                        } catch (exp: Exception) {

                        }
                        tv_fieldValue.text = strFieldsValues


                        var strFieldsTitles = ""

                        try {

                            if (!fullDetailsModel.fieldTitle1.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle1 + "\n"
                            }

                            if (!fullDetailsModel.fieldTitle2.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle2 + "\n"

                            }

                            if (!fullDetailsModel.fieldTitle3.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle3 + "\n"

                            }
                            if (!fullDetailsModel.fieldTitle4.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle4 + "\n"
                            }

                            if (!fullDetailsModel.fieldTitle5.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle5 + "\n"
                            }

                            if (!fullDetailsModel.fieldTitle6.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle6 + "\n"

                            }
                            if (!fullDetailsModel.fieldTitle7.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle7 + "\n"

                            }

                            if (!fullDetailsModel.fieldTitle8.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle8 + "\n"

                            }
                            if (!fullDetailsModel.fieldTitle9.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle9 + "\n"

                            }
                            if (!fullDetailsModel.fieldTitle10.equals("")) {
                                strFieldsTitles = strFieldsTitles + fullDetailsModel.fieldTitle10 + "\n"
                             }
                        } catch (exp: Exception) {
                        }
                        tv_fieldTitle.text = strFieldsTitles
                        tv_adsId.text = "AD ID: " + fullDetailsModel.id
                        tv_owmerName.text = fullDetailsModel.ownerName
                        tv_call.visibility = View.GONE
                        if (fullDetailsModel.showMobile.equals("1")) {
                            tv_call.visibility = View.VISIBLE
                        }

                        iv_favImg.setOnClickListener {
                            if (!fullDetailsModel.ownerId.equals(sharedPref!!.userId)) {
                                if (fullDetailsModel.favorite.equals("0")) {

                                    if (Utility.isConnected(ctx!!))
                                        callChangeFavApi("add_fav_ads", fullDetailsModel)
                                    else
                                        Utility.snackBar(slider, "Please check internet ")

                                } else {
                                    if (Utility.isConnected(ctx!!))
                                        callChangeFavApi("delete_fav_ads", fullDetailsModel)
                                    else
                                        Utility.snackBar(slider, "Please check internet ")

                                }
                            }
                            else{
                                Utility.snackBar(slider, "You cannot add in favourite to it, it is your own ads")

                            }
                        }
                        if(fullDetailsModel.favorite.equals("1"))
                        {
                            iv_favImg.setImageResource(R.drawable.ic_favorite_filled)
                            //iv_favImg.setImageResource(R.drawable.ic_favorite_filled)
                        }else{
                            iv_favImg.setImageResource(R.drawable.ic_favorite_empty)

                        }

                        if(!fullDetailsModel.lat.equals("") && !fullDetailsModel.lng.equals(""))
                        {
                            lati=fullDetailsModel.lat
                            longi=fullDetailsModel.lng
                            val  latLong = LatLng(lati.toDouble(), longi.toDouble())
                              val cameraPosition = CameraPosition.Builder().target(latLong).zoom(15f).build()//.tilt(70f).build()
 //                                 gMap!!.isMyLocationEnabled = false
                            gMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                        }
                    } else {
                        Utility.snackBar(nsView, "" + response.body()!!.message)
                     }
                }

                override fun onFailure(call: Call<AllApiResponse.AdverFullDetailResp>, t: Throwable) {
                    t.printStackTrace()
                    swipe_refresh.isRefreshing = false

                }
            })
    }

    private fun callChangeFavApi(
        strAction: String,
        dataModel: AllApiResponse.AdverFullDetailResp.AdverFullDetailModel?
    ) {

        Log.e(TAG,"callChangeFavApi   adsId="+dataModel!!.id+"   action="+strAction)

        apiService!!.addInFav(""+strAction ,""+sharedPref!!.userId, dataModel!!.id).enqueue(object : Callback<AllApiResponse.CommonRes> {
            override fun onResponse(
                call: Call<AllApiResponse.CommonRes>,
                response: Response<AllApiResponse.CommonRes>
            ) {
                Log.e("callAddInFavApi res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    if(strAction.equals("add_fav_ads"))
                        dataModel!!.favorite="1"
                    if(strAction.equals("delete_fav_ads"))
                        dataModel!!.favorite="0"


                } else {
                    //swipe_refresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<AllApiResponse.CommonRes>, t: Throwable) {
                t.printStackTrace()

                //swipe_refresh.isRefreshing = false
            }
        })

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
        ((ctx!! as Activity).application as AppController).component.inject(this@AdvertisementDetailsFrag)

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: AllApiResponse.AdverResp.AdverDataModel, param2: String) =
            AdvertisementDetailsFrag().apply {
                arguments = Bundle().apply {
                    //   putString(ARG_PARAM1, param1)
                    adsDataModel = param1
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
