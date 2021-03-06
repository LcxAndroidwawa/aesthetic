/*
 * Licensed under Apache-2.0
 *
 * Designed and developed by Aidan Follestad (@afollestad)
 */
package com.afollestad.aestheticsample.appcompat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_secondary.view.drawer_layout
import kotlinx.android.synthetic.main.fragment_secondary.view.recycler_view
import kotlinx.android.synthetic.main.fragment_secondary.view.swipe_refresh

/** @author Aidan Follestad (afollestad) */
class SecondaryFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_secondary, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    view.drawer_layout.setOnClickListener {
      startActivity(
          Intent(activity, DrawerActivity::class.java)
      )
    }
    view.recycler_view.setOnClickListener {
      startActivity(
          Intent(activity, RecyclerViewActivity::class.java)
      )
    }
    view.swipe_refresh.setOnClickListener {
      startActivity(
          Intent(activity, SwipeRefreshActivity::class.java)
      )
    }
  }
}
