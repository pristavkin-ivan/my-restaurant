package com.vano.myrestaurant.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.fragment.app.Fragment
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var textAnimation: Animation? = null

    private var binding: FragmentHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textAnimation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.text_anim)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root as View
    }

    override fun onStart() {
        super.onStart()
        val text1 = binding?.text1

        text1?.startAnimation(textAnimation)

        binding?.animate?.setOnClickListener {
            text1?.startAnimation(textAnimation)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
