package com.zubair.kotlinjetpack.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.zubair.kotlinjetpack.R
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.list_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val action was created in during the res/navigation/dog_navigation step
        check notes*/
        button_details.setOnClickListener {
            val action = ListFragmentDirections.navigateToDetailFragment()
            action.dogUuid = 5
            Navigation.findNavController(it).navigate(action)
        }
    }
}
