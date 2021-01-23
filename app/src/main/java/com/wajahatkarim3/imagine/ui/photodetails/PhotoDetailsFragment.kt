package com.wajahatkarim3.imagine.ui.photodetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.wajahatkarim3.imagine.R
import com.wajahatkarim3.imagine.base.BaseFragment
import com.wajahatkarim3.imagine.databinding.PhotoDetailsFragmentBinding
import com.wajahatkarim3.imagine.model.PhotoModel

class PhotoDetailsFragment : BaseFragment() {

    private lateinit var viewModel: PhotoDetailsViewModel
    private lateinit var bi: PhotoDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bi = PhotoDetailsFragmentBinding.inflate(inflater, container, false)
        return bi.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelProvider).get(PhotoDetailsViewModel::class.java)

        var photo = arguments?.getParcelable<PhotoModel>("photo")
        if (photo == null) {
            findNavController().popBackStack()
            return
        }

        setupViews()
        initObservations()

        viewModel.initPhotoModel(photo)
    }

    fun setupViews() {

    }

    fun initObservations() {
        viewModel.photoModelLiveData.observe(viewLifecycleOwner) { photo ->
            bi.photoView.load(photo.urls.full)
        }
    }

}