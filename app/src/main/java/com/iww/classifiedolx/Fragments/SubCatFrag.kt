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
import com.google.gson.Gson
import com.iww.classifiedolx.Fragments.backpressed.RootFragment
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.MainActivity

import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import com.iww.classifiedolx.recyclerview.setUp
import kotlinx.android.synthetic.main.fragment_frag_sub_cat.*
import kotlinx.android.synthetic.main.item_sub_category_lst.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

class SubCatFrag : Fragment() {
    // TODO: Rename and change types of parameters
    @Inject
    internal lateinit var apiService: ApiService
    private var mainCatId: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var ctx: Context? = null
    private var subCatItemData: MutableList<AllApiResponse.SubCategoryRes.SubCategoryItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mainCatId = it.getString(ARG_PARAM1)
        }
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         subCatItemData = mutableListOf()
        rv_subCatLst.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_subCatLst.layoutManager = mLayoutManager

        rv_subCatLst.setUp(subCatItemData!!, R.layout.item_sub_category_lst, { it1 ->
            this.tv_subCatTitle.text = it1.subcategory

            this.ll_subCatItem.setOnClickListener {
                Utility.enterNextReplaceFragment(
                    R.id.fl_subCatFrag,
                    SubCateBaseAdd.newInstance(""+mainCatId,it1.id),
                    (ctx as MainActivity).supportFragmentManager
                )
            }
        }, { view1: View, i: Int -> })
        if(!Utility.isConnected(ctx!!))
            Utility.snackBar(rv_subCatLst, "Please check internet connection ")
        fetchAllSubCat("get_subcategory")
        rv_subCatLst.layoutManager = LinearLayoutManager(context)

        swipe_refresh.setOnRefreshListener {
            if(!Utility.isConnected(ctx!!))
                Utility.snackBar(rv_subCatLst, "Please check internet connection ")
            fetchAllSubCat("get_subcategory")
        }

    }



    private fun fetchAllSubCat(strApi: String) {
        swipe_refresh.isRefreshing =  true
        apiService!!.fetchSubCategory(strApi, mainCatId).enqueue(object : Callback<AllApiResponse.SubCategoryRes> {
            override fun onResponse(
                call: Call<AllApiResponse.SubCategoryRes>,
                response: Response<AllApiResponse.SubCategoryRes>
            ) {
                Log.e("fetchAllSubCat res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    subCatItemData!!.clear()
                    subCatItemData!!.addAll(response.body()!!.data)
                    rv_subCatLst.adapter!!.notifyDataSetChanged()
                     swipe_refresh.isRefreshing = false
                    rv_subCatLst.visibility = View.VISIBLE
                    tvNoData.visibility = View.GONE

                } else {
                    tvNoData.visibility = View.VISIBLE
                    rv_subCatLst.visibility = View.GONE
                     swipe_refresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<AllApiResponse.SubCategoryRes>, t: Throwable) {
                t.printStackTrace()
                tvNoData.visibility = View.VISIBLE
                rv_subCatLst.visibility = View.GONE
                 swipe_refresh.isRefreshing = false
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_sub_cat, container, false)
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
        ((ctx!! as Activity).application as AppController).component.inject(this@SubCatFrag)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            SubCatFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}
