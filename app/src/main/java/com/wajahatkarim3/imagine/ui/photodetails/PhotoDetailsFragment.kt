package com.wajahatkarim3.imagine.ui.photodetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.wajahatkarim3.imagine.base.BaseFragment
import com.wajahatkarim3.imagine.databinding.PhotoDetailsFragmentBinding
import com.wajahatkarim3.imagine.model.PhotoModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailsFragment : BaseFragment<PhotoDetailsFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PhotoDetailsFragmentBinding
        get() = PhotoDetailsFragmentBinding::inflate

    private lateinit var viewModel: PhotoDetailsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotoDetailsViewModel::class.java)

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
            binding.photoView.load(photo.urls.full)
        }
    }

}