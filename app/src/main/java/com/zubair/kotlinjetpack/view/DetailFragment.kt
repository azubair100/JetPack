package com.zubair.kotlinjetpack.view


import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telephony.SmsManager
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.squareup.picasso.Picasso

import com.zubair.kotlinjetpack.R
import com.zubair.kotlinjetpack.databinding.DetailFragmentBinding
import com.zubair.kotlinjetpack.databinding.SendTextDialogBinding
import com.zubair.kotlinjetpack.model.DogBreed
import com.zubair.kotlinjetpack.model.DogPalette
import com.zubair.kotlinjetpack.model.TextInfo
import com.zubair.kotlinjetpack.util.getProgressDrawable
import com.zubair.kotlinjetpack.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.dog_row.view.*
import kotlinx.android.synthetic.main.list_fragment.*

class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var dogUuid = 0
    private lateinit var dataBinding: DetailFragmentBinding
    private var sendTextStarted = false
    private var currentDog: DogBreed? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_send_text ->{
                sendTextStarted = true
                (activity as MainActivity).checkTextPermission()
            }

            R.id.action_share ->{
                view?.let{
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Check the dog")
                    intent.putExtra(Intent.EXTRA_TEXT, "${currentDog?.name} bred for ${currentDog?.purpose}")
                    intent.putExtra(Intent.EXTRA_STREAM, currentDog?.url)
                    startActivity(Intent.createChooser(intent, "Share With"))
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun onPermissionResult(permissionGranted: Boolean){
        if(sendTextStarted && permissionGranted){
            context?.let{
                val textInfo = TextInfo(
                    "",
                    "${currentDog?.name} bred for ${currentDog?.purpose}",
                    currentDog?.url)

                val dialogBinding = DataBindingUtil.inflate<SendTextDialogBinding>(
                    LayoutInflater.from(it),
                    R.layout.send_text_dialog,
                    null,
                    false
                )

                AlertDialog.Builder(it)
                    .setView(dialogBinding.root)
                    .setPositiveButton("Send Text") {dialog, which ->
                        if(!dialogBinding.textDestination.text.isNullOrEmpty()){
                            textInfo.to = dialogBinding.textDestination.text.toString()
                            sendText(textInfo)
                        }
                    }
                    .setNegativeButton("Cancel"){dialog, which ->  dialog.dismiss()}
                    .show()

                dialogBinding.textInfo = textInfo
            }
        }
    }

    private fun sendText(textInfo: TextInfo) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val text = SmsManager.getDefault()
        text.sendTextMessage(textInfo.to, "", textInfo.text, pendingIntent, null)
    }


    private fun observeViewModel() {
        detailViewModel.dogLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { dog ->
                currentDog = dog
                dataBinding.dog = dog
                dog.url?.let{ setUpBackgroundColorPalette(dog.url) }
            }
        })
    }

    private fun setUpBackgroundColorPalette(url: String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate{palette ->
                            val intColor = palette?.darkVibrantSwatch?.rgb ?: 0
                            //you can play around with vibrantSwatch
                            val myPalette = DogPalette(intColor)
                            dataBinding.palette = myPalette
                        }
                }
            })
    }
}
