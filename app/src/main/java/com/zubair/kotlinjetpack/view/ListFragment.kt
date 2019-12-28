package com.zubair.kotlinjetpack.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.zubair.kotlinjetpack.R
import com.zubair.kotlinjetpack.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private lateinit var dogAdapter: DogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.list_fragment, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        dogAdapter = DogListAdapter(arrayListOf())
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpSwipeRefreshLayout()
        observeViewModel()
        listViewModel.refresh()
    }

    private fun setUpSwipeRefreshLayout(){
        dog_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogAdapter
        }
    }

    private fun setUpRecyclerView(){
        refresh_layout.setOnRefreshListener {
            dog_list.visibility = View.GONE
            error_text.visibility = View.GONE
            progress_bar.visibility = View.VISIBLE
            listViewModel.refreshByPassCache()
            refresh_layout.isRefreshing = false
        }
    }

    private fun observeViewModel(){
        listViewModel.dogList.observe(viewLifecycleOwner, Observer { dogs ->
            dogs?.let {
                dog_list.visibility = View.VISIBLE
                dogAdapter.updateDogList(it)
            }
        })

        listViewModel.listLLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let { error_text.visibility = if(it) View.VISIBLE else View.GONE }
        })

        listViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let { progress_bar.visibility = if(it) View.VISIBLE else View.GONE
                if (it){
                    dog_list.visibility = View.GONE
                    error_text.visibility = View.GONE
                }
            }
        })

    }

}
