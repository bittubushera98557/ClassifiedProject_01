package com.iww.classifiedolx

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_ads_done.*
import kotlinx.android.synthetic.main.header.*
import java.lang.Exception

class AdsDoneActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {
                onBackPressed()
                }
            R.id.btnHome-> {
                onBackPressed()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads_done)
        tv_title.text="Ads Posted"
        img_back.setOnClickListener(this)
        btnHome.setOnClickListener(this)
      try{
          getOldIntentData()
      }
      catch (exp:Exception)
      {

      }

    }
    private fun getOldIntentData() {
        if (getIntent().hasExtra("message")) {
            var setMsg = getIntent().getStringExtra("message");
            tvAdsId.text=setMsg
        } else {
            throw  IllegalArgumentException("Activity cannot find  extras " + "tab");
        }

    }

    override fun onBackPressed() {
        var intentMain = Intent(this@AdsDoneActivity, MainActivity::class.java)
        intentMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentMain.putExtra("tab", "3")
        startActivity(intentMain)
        finish()

    }
}
