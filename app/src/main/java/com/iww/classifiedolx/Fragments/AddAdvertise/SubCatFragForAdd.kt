package com.iww.classifiedolx.Fragments.AddAdvertise

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent.getIntent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.iww.classifiedolx.Fragments.SubCateBaseAdd
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.MainActivity
import com.iww.classifiedolx.R

import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AllApiResponse.AddAdvertiseRequest
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import com.iww.classifiedolx.recyclerview.setUp
import kotlinx.android.synthetic.main.fragment_sub_cat_frag_for_add.*
import kotlinx.android.synthetic.main.item_sub_category_lst.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SubCatFragForAdd : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id)
        {
           R.id. fl_subCateForAdd   ->
           {

           }
        }


    }

    // TODO: Rename and change types of parameters
    @Inject
    internal lateinit var apiService: ApiService
    // TODO: Rename and change types of parameters
    private var mainCatName: String? = null
    private var mainCatId: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var subCatItemData : MutableList<AllApiResponse.SubCategoryRes.SubCategoryItem>? = null
     var  ctx:Context?=null
var TAG="SubCatFragForAdd "
    var advertiseAddRequestParams:  AddAdvertiseRequest ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mainCatName = it.getString(ARG_PARAM1)
            mainCatId = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_cat_frag_for_add, container, false)
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
        ((ctx!! as Activity).application as AppController).component.inject(this@SubCatFragForAdd)

    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_choosedMainCat.text = ""+mainCatName
        subCatItemData = mutableListOf()

        advertiseAddRequestParams= AllApiResponse.AddAdvertiseRequest()

        rv_subCatForAdd.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_subCatForAdd.layoutManager = mLayoutManager

        rv_subCatForAdd.setUp(subCatItemData!!, R.layout.item_sub_category_lst, { it1 ->
            this.tv_subCatTitle.text = it1.subcategory

            this.ll_subCatItem.setOnClickListener {

                advertiseAddRequestParams!!.CatId=mainCatId
                advertiseAddRequestParams!!.CatName=mainCatName
                advertiseAddRequestParams!!.SubCatId=it1.id
                advertiseAddRequestParams!!.SubCatName=it1.subcategory

                Utility.enterNextReplaceFragment(
                    R.id.fl_subCateForAdd,
                    FieldForAddNewAdvertise.newInstance(advertiseAddRequestParams!!,it1.id),
                    (ctx as MainActivity).supportFragmentManager
                )
            }
        }, { view1: View, i: Int -> })
        fetchAllSubCat("get_subcategory")
        rv_subCatForAdd.layoutManager = LinearLayoutManager(context)
        fl_subCateForAdd.setOnClickListener(this)

    }



    private fun fetchAllSubCat(strApi: String) {

        apiService!!.fetchSubCategory(strApi, mainCatId).enqueue(object : Callback<AllApiResponse.SubCategoryRes> {
            override fun onResponse(
                call: Call<AllApiResponse.SubCategoryRes>,
                response: Response<AllApiResponse.SubCategoryRes>
            ) {
                Log.e(TAG+"fetchAllSubCat res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    subCatItemData!!.clear()
                    subCatItemData!!.addAll(response.body()!!.data)
                    rv_subCatForAdd.adapter!!.notifyDataSetChanged()
                    //    swipe_refresh.isRefreshing = false
                } else {
                    tvNoData.visibility = View.VISIBLE
                    rv_subCatForAdd.visibility = View.GONE
                    //swipe_refresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<AllApiResponse.SubCategoryRes>, t: Throwable) {
                t.printStackTrace()
                tvNoData.visibility = View.VISIBLE
                rv_subCatForAdd.visibility = View.GONE
                //swipe_refresh.isRefreshing = false
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubCatFragForAdd().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
