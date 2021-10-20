package com.vano.myrestaurant.controller.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.vano.myrestaurant.R

class HomeFragment : Fragment() {

    private lateinit var textAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textAnimation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.text_anim)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<TextView>(R.id.text1)?.startAnimation(textAnimation)

        with(activity?.findViewById<Button>(R.id.animate)) {

            this?.setOnClickListener {

                activity?.findViewById<TextView>(R.id.text1)?.startAnimation(textAnimation)

            }
        }
    }

    /*private fun configureObjectAnimator(view: View) {
        val animator = ObjectAnimator.ofFloat(
            view,
            View.TRANSLATION_X,
            view.translationX,
            view.translationX + 200f
        )

        animator.duration = 1000
        animator.interpolator = BounceInterpolator()
        animator.start()
    }*/

}
