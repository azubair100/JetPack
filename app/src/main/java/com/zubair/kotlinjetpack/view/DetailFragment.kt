package com.zubair.kotlinjetpack.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso

import com.zubair.kotlinjetpack.R
import com.zubair.kotlinjetpack.databinding.DetailFragmentBinding
import com.zubair.kotlinjetpack.util.getProgressDrawable
import com.zubair.kotlinjetpack.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.dog_row.view.*
import kotlinx.android.synthetic.main.list_fragment.*

class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var dogUuid = 0

    private lateinit var dataBinding: DetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid }
        observeViewModel()
        detailViewModel.fetch(dogUuid)
    }

    private fun observeViewModel() {
        detailViewModel.dogLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { dataBinding.dog = it }
        })
    }


}
