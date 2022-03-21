package com.component.pharma.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.component.pharma.R
import com.component.pharma.databinding.FragmentViewPagerBinding
import com.component.pharma.ui.onboarding.screens.FirstScreenFragment
import com.component.pharma.ui.onboarding.screens.SecondScreenFragment
import com.component.pharma.ui.onboarding.screens.ThirdScreenFragment


class ViewPagerFragment: Fragment() {

    lateinit var binding: FragmentViewPagerBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        binding = FragmentViewPagerBinding.bind(view)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
        return view
    }






}