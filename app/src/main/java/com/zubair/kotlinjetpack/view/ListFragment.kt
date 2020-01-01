package com.zubair.kotlinjetpack.view


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
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
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        dogAdapter = DogListAdapter(arrayListOf())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSwipeRefresh()
        setUpRecycler()
        observeViewModel()
        listViewModel.refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigate_settings ->{
                view?.let{
                    Navigation.findNavController(it).
                        navigate(ListFragmentDirections.navigateToSettingsFragment())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecycler(){
        dog_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogAdapter
        }
    }

    private fun setUpSwipeRefresh(){
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
