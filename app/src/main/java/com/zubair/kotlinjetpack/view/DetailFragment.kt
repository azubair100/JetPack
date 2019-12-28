package com.zubair.kotlinjetpack.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.zubair.kotlinjetpack.R
import com.zubair.kotlinjetpack.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.list_fragment.*

class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var dogUuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.detail_fragment, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        observeViewModel()
        detailViewModel.fetch()
    }

    private fun observeViewModel() {
        detailViewModel.dog.observe(viewLifecycleOwner, Observer {
            it?.let {
                name.text = it.name
                lifespan.text = it.lifespan
                purpose.text = it.purpose
                group.text = it.group
                temperament.text = it.temperament
            }
        })
    }


}
