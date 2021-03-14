/*
 * Copyright 2021 Wajahat Karim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wajahatkarim3.imagine.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wajahatkarim3.imagine.R
import com.wajahatkarim3.imagine.adapters.PhotosAdapter
import com.wajahatkarim3.imagine.adapters.TagsAdapter
import com.wajahatkarim3.imagine.base.BaseFragment
import com.wajahatkarim3.imagine.databinding.HomeFragmentBinding
import com.wajahatkarim3.imagine.model.TagModel
import com.wajahatkarim3.imagine.utils.dismissKeyboard
import com.wajahatkarim3.imagine.utils.gone
import com.wajahatkarim3.imagine.utils.showSnack
import com.wajahatkarim3.imagine.utils.showToast
import com.wajahatkarim3.imagine.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding
        get() = HomeFragmentBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    lateinit var tagsAdapter: TagsAdapter
    lateinit var photosAdapter: PhotosAdapter

    var snackbar: Snackbar? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
        initTags()
        initObservations()
    }

    fun setupViews() {
        context?.let { ctx ->
            // Tags RecyclerView
            tagsAdapter = TagsAdapter { tag, _ ->
                performSearch(tag.tagName)
            }
            val flexboxLayoutManager = FlexboxLayoutManager(ctx).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                alignItems = AlignItems.STRETCH
            }
            bi.recyclerTags.layoutManager = flexboxLayoutManager
            bi.recyclerTags.adapter = tagsAdapter

            // Photos RecyclerView
            photosAdapter = PhotosAdapter() { photo, _ ->
                var bundle = bundleOf("photo" to photo)
                findNavController().navigate(R.id.action_homeFragment_to_photoDetailsFragment, bundle)
            }
            photosAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            bi.recyclerPopularPhotos.adapter = photosAdapter

            // NestedScrollView
            bi.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    viewModel.loadMorePhotos()
                }
            }

            // Input Text Search
            bi.inputSearchPhotos.setEndIconOnClickListener {
                bi.txtSearchPhotos.setText("")
                bi.lblPopular.setText(getString(R.string.label_popular_text_str))
                viewModel.fetchPhotos(1)
            }

            bi.txtSearchPhotos.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    bi.txtSearchPhotos.dismissKeyboard()
                    performSearch(bi.txtSearchPhotos.text.toString())
                    true
                }
                false
            }
        }
    }

    private fun performSearch(query: String) {
        bi.txtSearchPhotos.setText(query)
        bi.lblPopular.setText(getString(R.string.message_search_results_for_str, query))
        viewModel.searchPhotos(query)
    }

    fun initObservations() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    bi.recyclerPopularPhotos.gone()
                    bi.progressPhotos.visible()
                }

                is LoadingNextPageState -> {
                    showToast(getString(R.string.message_load_photos_str))
                }

                is ContentState -> {
                    bi.recyclerPopularPhotos.visible()
                    bi.progressPhotos.gone()
                }

                is ErrorState -> {
                    bi.progressPhotos.gone()
                    bi.nestedScrollView.showSnack(state.message, getString(R.string.action_retry_str)) {
                        viewModel.retry()
                    }
                }

                is ErrorNextPageState -> {
                    bi.nestedScrollView.showSnack(state.message, getString(R.string.action_retry_str)) {
                        viewModel.retry()
                    }
                }
            }
        }

        viewModel.photosListLiveData.observe(viewLifecycleOwner) { photos ->
            photosAdapter.updateItems(photos)
        }
    }

    fun initTags() {
        var tags = arrayListOf(
            TagModel(
                tagName = "Food",
                imageUrl = "https://www.helpguide.org/wp-content/uploads/fast-foods-candy-cookies-pastries-768.jpg"
            ),
            TagModel(
                tagName = "Cars",
                imageUrl = "https://i.dawn.com/primary/2019/03/5c8da9fc3e386.jpg"
            ),
            TagModel(
                tagName = "Cities",
                imageUrl = "https://news.mit.edu/sites/default/files/styles/news_article__image_gallery/public/images/201306/20130603150017-0_0.jpg?itok=fU2rLfB6"
            ),
            TagModel(
                tagName = "Mountains",
                imageUrl = "https://www.dw.com/image/48396304_101.jpg"
            ),
            TagModel(
                tagName = "People",
                imageUrl = "https://cdn.lifehack.org/wp-content/uploads/2015/02/what-makes-people-happy.jpeg"
            ),
            TagModel(
                tagName = "Work",
                imageUrl = "https://www.plays-in-business.com/wp-content/uploads/2015/05/successful-business-meeting.jpg"
            ),
            TagModel(
                tagName = "Fashion",
                imageUrl = "https://www.remixmagazine.com/assets/Prada-SS21-1__ResizedImageWzg2Niw1NzZd.jpg"
            ),
            TagModel(
                tagName = "Animals",
                imageUrl = "https://kids.nationalgeographic.com/content/dam/kids/photos/animals/Mammals/A-G/cheetah-mom-cubs.adapt.470.1.jpg"
            ),
            TagModel(
                tagName = "Interior",
                imageUrl = "https://images.homify.com/c_fill,f_auto,q_0,w_740/v1495001963/p/photo/image/2013905/CAM_2_OPTION_1.jpg"
            )
        )
        tagsAdapter.updateItems(tags)
    }
}
