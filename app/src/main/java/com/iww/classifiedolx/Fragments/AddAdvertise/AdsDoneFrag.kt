package com.iww.classifiedolx.Fragments.AddAdvertise

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iww.classifiedolx.FrRefresh
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener

import com.iww.classifiedolx.R
import kotlinx.android.synthetic.main.fragment_ads_done.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdsDoneFrag : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btnHome -> {
               // onFrRefresh!!.onFrRefresh()
                fragmentManager!!.popBackStack()
                //  ((ctx as Activity) as NavigationHost).navigateTo(CategoryFragment.newInstance(0),true,false)
            }
            R.id.fl_adsDoneFrag-> {
             }
         }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var ctx: Context? = null
   // private var onFrRefresh : FrRefresh? = null

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
        return inflater.inflate(R.layout.fragment_ads_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.btnHome.setOnClickListener(this)
        fl_adsDoneFrag.setOnClickListener(this)
        if(arguments!!.getInt("section_number",0)==1)
        {}
        else  tvMsg.text = arguments!!.getString("msg","")

    }
    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.ctx = context
     //   onFrRefresh =  (context as Activity) as FrRefresh
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(sectionNumber: Int,msg : String): AdsDoneFrag {
            val fragment = AdsDoneFrag()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            args.putString("msg", msg)
            fragment.arguments = args
            return fragment
        }
    }
}
