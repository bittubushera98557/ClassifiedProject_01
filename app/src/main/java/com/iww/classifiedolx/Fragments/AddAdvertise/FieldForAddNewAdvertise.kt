package com.iww.classifiedolx.Fragments.AddAdvertise

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.gson.Gson
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.MainActivity

import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import kotlinx.android.synthetic.main.fragment_field_for_add_new_advertise.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FieldForAddNewAdvertise : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_next -> {
                checkEditFieldValues()
            }
            R.id.fl_fieldsFrag -> {

            }
        }
    }

    private fun checkEditFieldValues() {
        var fiedlValueChk = true

        var strField1 = et_field1.text.toString()
        var strField2 = et_field2.text.toString()
        var strField3 = et_field3.text.toString()
        var strField4 = et_field4.text.toString()
        var strField5 = et_field5.text.toString()
        var strField6 = et_field6.text.toString()
        var strField7 = et_field7.text.toString()
        var strField8 = et_field8.text.toString()
        var strField9 = et_field9.text.toString()
        var strField10 = et_field10.text.toString()
        var strFieldTitle = et_fieldTitle.text.toString()
        var strFieldDesc = et_fieldDesc.text.toString()

        if (txtInptLyt_field1.visibility == View.VISIBLE && (TextUtils.isEmpty(strField1.replace(" ".toRegex(), "")))) {

            focusOnEditTxtView(et_field1, "Enter Field value")
            fiedlValueChk = false
        } else if (txtInptLyt_field2.visibility == View.VISIBLE && (TextUtils.isEmpty(
                strField2.replace(
                    " ".toRegex(),
                    ""
                )
            ))
        ) {
            focusOnEditTxtView(et_field2, "Enter Field value")
            fiedlValueChk = false
        } else if (txtInptLyt_field3.visibility == View.VISIBLE && (TextUtils.isEmpty(
                strField3.replace(
                    " ".toRegex(),
                    ""
                )
            ))
        ) {
            focusOnEditTxtView(et_field3, "Enter Field value")

            fiedlValueChk = false
        } else if (txtInptLyt_field4.visibility == View.VISIBLE && (TextUtils.isEmpty(
                strField4.replace(
                    " ".toRegex(),
                    ""
                )
            ))
        ) {
            focusOnEditTxtView(et_field4, "Enter Field value")
            fiedlValueChk = false
        } else if (txtInptLyt_field5.visibility == View.VISIBLE && (TextUtils.isEmpty(
                strField5.replace(
                    " ".toRegex(),
                    ""
                )
            ))
        ) {
            focusOnEditTxtView(et_field5, "Enter Field value")
            fiedlValueChk = false
        } else if (txtInptLyt_field6.visibility == View.VISIBLE && (TextUtils.isEmpty(
                strField6.replace(
                    " ".toRegex(),
                    ""
                )
            ))
        ) {
            focusOnEditTxtView(et_field6, "Enter Field value")
            fiedlValueChk = false
        } else if (txtInptLyt_field7.visibility == View.VISIBLE && (TextUtils.isEmpty(
                strField7.replace(
                    " ".toRegex(),
                    ""
                )
            ))
        ) {
            focusOnEditTxtView(et_field8, "Enter Field value")
            fiedlValueChk = false
        } else if (txtInptLyt_field8.visibility == View.VISIBLE && (TextUtils.isEmpty(
                strField8.replace(
                    " ".toRegex(),
                    ""
                )
            ))
        ) {
            focusOnEditTxtView(et_field8, "Enter Field value")

            fiedlValueChk = false
        } else if (txtInptLyt_field9.visibility == View.VISIBLE && (TextUtils.isEmpty(
                strField9.replace(
                    " ".toRegex(),
                    ""
                )
            ))
        ) {
            focusOnEditTxtView(et_field9, "Enter Field value")

            fiedlValueChk = false
        } else if (txtInptLyt_field10.visibility == View.VISIBLE && (TextUtils.isEmpty(
                strField10.replace(
                    " ".toRegex(),
                    ""
                )
            ))
        ) {
            focusOnEditTxtView(et_field10, "Enter Field value")
            fiedlValueChk = false
        } else if (TextUtils.isEmpty(et_fieldTitle.text.toString().replace(" ".toRegex(), ""))) {
            focusOnEditTxtView(et_fieldTitle, "Enter Title")
            fiedlValueChk = false
        } else if (TextUtils.isEmpty(et_fieldDesc.text.toString().replace(" ".toRegex(), ""))) {
            fiedlValueChk = false
            focusOnEditTxtView(et_fieldDesc, "Enter Description")
        } else {

            addNewAdvertiseRequestParam!!.field1=strField1
            addNewAdvertiseRequestParam!!.field2=strField2
            addNewAdvertiseRequestParam!!.field3=strField3
            addNewAdvertiseRequestParam!!.field4=strField4
            addNewAdvertiseRequestParam!!.field5=strField5
            addNewAdvertiseRequestParam!!.field6=strField6
            addNewAdvertiseRequestParam!!.field7=strField7
            addNewAdvertiseRequestParam!!.field8=strField8
            addNewAdvertiseRequestParam!!.field9=strField9
            addNewAdvertiseRequestParam!!.field10=strField10

        /*    addNewAdvertiseRequestParam!!.field_second1=""
            addNewAdvertiseRequestParam!!.field_second2=""
            addNewAdvertiseRequestParam!!.field_second3=""
            addNewAdvertiseRequestParam!!.field_second4=""
            addNewAdvertiseRequestParam!!.field_second5=""
            addNewAdvertiseRequestParam!!.field_second6=""
            addNewAdvertiseRequestParam!!.field_second7=""
            addNewAdvertiseRequestParam!!.field_second8=""
            addNewAdvertiseRequestParam!!.field_second9=""
            addNewAdvertiseRequestParam!!.field_second10=""
*/
            addNewAdvertiseRequestParam!!.Ad_title=strFieldTitle
            addNewAdvertiseRequestParam!!.Ad_Description=strFieldDesc


            Utility.enterNextReplaceFragment(
                R.id.fl_fieldsFrag,
                ChooseLocationForAdver.newInstance(addNewAdvertiseRequestParam!!,""),
                (ctx as MainActivity).supportFragmentManager
            )
        }


    }

    private fun focusOnEditTxtView(etView: EditText, strMsg: String) {
        scrVw_fields.post(Runnable {
            scrVw_fields.scrollTo(0, etView.top)
            etView.isFocusable = true
            etView.error = "" + strMsg

            //  txtInptLyt_email.error=" txtInptLyt Field Required"

        })
    }

    // TODO: Rename and change types of parameters

    @Inject
    internal lateinit var apiService: ApiService
    private var mainCatId: String? = null
    private var subCatId: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var ctx: Context? = null
    var TAG = "FieldForAddNewAdvertise "
    var dialog: Dialog? = null
    var addNewAdvertiseRequestParam: AllApiResponse.AddAdvertiseRequest?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mainCatId = addNewAdvertiseRequestParam!!.CatId
            subCatId = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = Utility.dialog(ctx!!)
        fetchAllSubCat("get_subcategory")
        tv_next.setOnClickListener(this)
        fl_fieldsFrag.setOnClickListener(this)
        tv_catName.text=addNewAdvertiseRequestParam!!.CatName+"    >    "+addNewAdvertiseRequestParam!!.SubCatName
    }

    private fun fetchAllSubCat(strApi: String) {
        showHideDialog(true)
        apiService!!.fetchSubCategory(strApi, mainCatId).enqueue(object : Callback<AllApiResponse.SubCategoryRes> {
            override fun onResponse(
                call: Call<AllApiResponse.SubCategoryRes>,
                response: Response<AllApiResponse.SubCategoryRes>
            ) {
                dialog!!.hide()
                Log.e(TAG + "fetchAllSubCat res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    var subCatData = response.body()!!.data
                    for (i in 0..(subCatData.size - 1)) {
                        if (subCatData[i].id == subCatId) {
                            var field1 = subCatData[i].field1
                            if (!field1.equals("")) {
                                txtInptLyt_field1.visibility = View.VISIBLE
                                txtInptLyt_field1.hint = field1
                            }

                            var field2 = subCatData[i].field2
                            if (!field2.equals("")) {
                                txtInptLyt_field2.visibility = View.VISIBLE
                                txtInptLyt_field2.hint = field2
                            }

                            var field3 = subCatData[i].field3
                            if (!field3.equals("")) {
                                txtInptLyt_field3.visibility = View.VISIBLE
                                txtInptLyt_field3.hint = field3
                            }

                            var field4 = subCatData[i].field4
                            if (!field4.equals("")) {
                                txtInptLyt_field4.visibility = View.VISIBLE
                                txtInptLyt_field4.hint = field4
                            }

                            var field5 = subCatData[i].field5
                            if (!field5.equals("")) {
                                txtInptLyt_field5.visibility = View.VISIBLE
                                txtInptLyt_field5.hint = field5
                            }

                            var field6 = subCatData[i].field6
                            if (!field6.equals("")) {
                                txtInptLyt_field6.visibility = View.VISIBLE
                                txtInptLyt_field6.hint = field6
                            }

                            var field7 = subCatData[i].field7
                            if (!field7.equals("")) {
                                txtInptLyt_field7.visibility = View.VISIBLE
                                txtInptLyt_field7.hint = field7
                            }

                            var field8 = subCatData[i].field8
                            if (!field8.equals("")) {
                                txtInptLyt_field8.visibility = View.VISIBLE
                                txtInptLyt_field8.hint = field8
                            }

                            var field9 = subCatData[i].field9
                            if (!field9.equals("")) {
                                txtInptLyt_field9.visibility = View.VISIBLE
                                txtInptLyt_field9.hint = field9
                            }

                            var field10 = subCatData[i].field10
                            if (!field10.equals("")) {
                                txtInptLyt_field10.visibility = View.VISIBLE
                                txtInptLyt_field10.hint = field10
                            }
                        }
                    }
                } else {
                    Utility.snackBar(tv_next, "" + response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<AllApiResponse.SubCategoryRes>, t: Throwable) {
                t.printStackTrace()
                dialog!!.hide()

            }
        })
    }

    private fun showHideDialog(dialogStatus: Boolean) {
        if (dialogStatus == true && dialog != null) {
            dialog!!.hide()
            dialog!!.cancel()
            dialog!!.dismiss()

            dialog!!.show()

        }
        if (dialogStatus == false && dialog != null) {
            dialog!!.hide()
            dialog!!.cancel()
            dialog!!.dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_field_for_add_new_advertise, container, false)


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
        ((ctx!! as Activity).application as AppController).component.inject(this@FieldForAddNewAdvertise)

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: AllApiResponse.AddAdvertiseRequest, param2: String) =
            FieldForAddNewAdvertise().apply {
                arguments = Bundle().apply {
                    addNewAdvertiseRequestParam= param1;
                //    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
