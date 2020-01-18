package com.iww.classifiedolx.Fragments

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.MainActivity
import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AppConstants
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import com.iww.classifiedolx.recyclerview.setUp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_main_category.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    @Inject
    internal lateinit var apiService: ApiService
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var mainCatItemData : MutableList<AllApiResponse.MainCategoryRes.MainCategoryItem>? = null

var ctx:Context?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainCatItemData = mutableListOf()
           rv_mainCat.setHasFixedSize(true);
    rv_mainCat.layoutManager = GridLayoutManager(context, 2)
        rv_mainCat.setUp(mainCatItemData!!, R.layout.item_main_category, { it1 ->
            this.tv_categoryName.text = it1.category_name
            val circularProgressDrawable = CircularProgressDrawable(ctx as Activity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Picasso.with(context).load(Uri.parse(AppConstants.IMAGE_uploads_PATH+it1.image_upload)).placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(this.iv_categoryImg)


            ll_itemLinear.setBackgroundColor(Color.parseColor(""+it1.color));
            if ( ll_itemLinear != null) {
                val display = (context as Activity).windowManager.defaultDisplay
                     ll_itemLinear.getLayoutParams().width= Math.round((display.width / 3).toFloat())
            }
            this.ll_itemLinear.setOnClickListener {
             Utility.enterNextReplaceFragment(R.id.ll_homeFragment,SubCatFrag.newInstance(it1.category_Id ),(ctx as MainActivity).supportFragmentManager)
   }
        }, { view1: View, i: Int -> })
        if(!Utility.isConnected(ctx!!))
            Utility.snackBar(rv_mainCat,"Please check internet ")

        fetchAllMainCat("get_category")
         rv_mainCat.layoutManager = GridLayoutManager(context, 3)

    }

    private fun fetchAllMainCat(strApi: String) {

        apiService!!.fetchMainCategory(strApi ).
            enqueue(object : Callback<AllApiResponse.MainCategoryRes> {
                override fun onResponse(call: Call<AllApiResponse.MainCategoryRes>, response: Response<AllApiResponse.MainCategoryRes>) {
                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                                 mainCatItemData!!.clear()
                        mainCatItemData!!.addAll(response.body()!!.data)
                                rv_mainCat.adapter!!.notifyDataSetChanged()
                            //    swipe_refresh.isRefreshing = false
       } else {
                        tvNoData.visibility = View.VISIBLE
                        rv_mainCat.visibility = View.GONE
                        //swipe_refresh.isRefreshing = false
                    }
                }
                override fun onFailure(call: Call<AllApiResponse.MainCategoryRes>, t: Throwable) {
                    t.printStackTrace()
                    tvNoData.visibility = View.VISIBLE
                    rv_mainCat.visibility = View.GONE
                    //swipe_refresh.isRefreshing = false
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx=context
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        ((ctx!! as Activity).application as AppController).component.inject(this@HomeFragment)

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
