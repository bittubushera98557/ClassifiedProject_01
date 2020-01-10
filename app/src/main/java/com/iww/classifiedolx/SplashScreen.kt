package com.iww.classifiedolx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.iww.classifiedolx.Utilities.SharedPref
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startAnimations()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun startAnimations() {
        var anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        anim.reset()

        flSplash.clearAnimation()
        flSplash.startAnimation(anim)

        anim = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        anim.reset()

        ivSplash.clearAnimation()
        ivSplash.startAnimation(anim)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                try {

                         startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })

    }
}
