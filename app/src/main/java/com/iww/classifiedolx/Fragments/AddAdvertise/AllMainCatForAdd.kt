package com.iww.classifiedolx.Fragments.AddAdvertise

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.iww.classifiedolx.Fragments.SubCateBaseAdd
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.LoginSignupScreen
import com.iww.classifiedolx.MainActivity

import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AppConstants
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import com.iww.classifiedolx.recyclerview.setUp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_all_main_cat_for_add.*
import kotlinx.android.synthetic.main.item_main_category.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AllMainCatForAdd : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fl_mainCatForAdd -> {

            }
        }

    }

    // TODO: Rename and change types of parameters
    @Inject
    internal lateinit var apiService: ApiService
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var mainCatItemData: MutableList<AllApiResponse.MainCategoryRes.MainCategoryItem>? = null
    var dialog: Dialog? = null
    var ctx: Context? = null
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
        return inflater.inflate(R.layout.fragment_all_main_cat_for_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fl_mainCatForAdd.setOnClickListener(this)
        mainCatItemData = mutableListOf()
        rv_allMainCat.setHasFixedSize(true)
dialog=Utility.dialog(ctx!!)
        rv_allMainCat.layoutManager = GridLayoutManager(context, 2)
        rv_allMainCat.setUp(mainCatItemData!!, R.layout.item_main_category, { it1 ->
            this.tv_categoryName.text = it1.category_name
            val circularProgressDrawable = CircularProgressDrawable(ctx as Activity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Picasso.with(context).load(Uri.parse(AppConstants.IMAGE_uploads_PATH + it1.image_upload))
                .placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(this.iv_categoryImg)

            ll_itemLinear.setBackgroundColor(Color.parseColor("" + it1.color))
            if (ll_itemLinear != null) {
                val display = (context as Activity).windowManager.defaultDisplay
                ll_itemLinear.getLayoutParams().width = Math.round((display.width / 2).toFloat())
            }
            this.ll_itemLinear.setOnClickListener {
                showHideDialog(false)

                Utility.enterNextReplaceFragment(
                    R.id.fl_mainCatForAdd,
                    SubCatFragForAdd.newInstance("", it1.category_Id),
                    (ctx as MainActivity).supportFragmentManager
                )
            }
        }, { view1: View, i: Int -> })
        fetchAllMainCat("get_category")
        rv_allMainCat.layoutManager = GridLayoutManager(context, 2)

    }

    private fun fetchAllMainCat(strApi: String) {
        showHideDialog(true)
        apiService!!.fetchMainCategory(strApi).enqueue(object : Callback<AllApiResponse.MainCategoryRes> {
            override fun onResponse(
                call: Call<AllApiResponse.MainCategoryRes>,
                response: Response<AllApiResponse.MainCategoryRes>
            ) {

                showHideDialog(false)
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    mainCatItemData!!.clear()
                    mainCatItemData!!.addAll(response.body()!!.data)
                    rv_allMainCat.adapter!!.notifyDataSetChanged()
                    //    swipe_refresh.isRefreshing = false
                } else {
                    tvNoData.visibility = View.VISIBLE
                    rv_allMainCat.visibility = View.GONE
                    //swipe_refresh.isRefreshing = false
                    Utility.snackBar(tvNoData, "" + response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<AllApiResponse.MainCategoryRes>, t: Throwable) {
                showHideDialog(false)
      t.printStackTrace()
                tvNoData.visibility = View.VISIBLE
                rv_allMainCat.visibility = View.GONE
                //swipe_refresh.isRefreshing = false
            }
        })
    }
    private fun showHideDialog(dialogStatus: Boolean) {
        if (dialogStatus == true && dialog != null) {
            dialog!!.hide()
            dialog!!.show()
        }
        if (dialogStatus == false && dialog != null) {
            dialog!!.hide()
            dialog!!.cancel()
            dialog!!.dismiss()
        }
    }
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
        ((ctx!! as Activity).application as AppController).component.inject(this@AllMainCatForAdd)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllMainCatForAdd().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
