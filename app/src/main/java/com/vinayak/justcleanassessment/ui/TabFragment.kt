package com.vinayak.justcleanassessment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.vinayak.justcleanassessment.R

class TabFragment : Fragment() {

    private lateinit var collectionPagerAdapter: CollectionPagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.collection_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectionPagerAdapter = CollectionPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = collectionPagerAdapter
    }
}


class CollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val titles = arrayOf("Posts","Favorites")
    override fun getCount(): Int  = 2

    override fun getItem(i: Int): Fragment {

        if(i==0) {
            return PostsFragment()
        }
        return FavoritesFragment()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}