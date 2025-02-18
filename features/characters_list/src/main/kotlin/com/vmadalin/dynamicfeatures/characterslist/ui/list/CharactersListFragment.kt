/*
 * Copyright 2019 vmadalin.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vmadalin.dynamicfeatures.characterslist.ui.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.vmadalin.android.SampleApp.Companion.coreComponent
import com.vmadalin.core.extensions.gridLayoutManager
import com.vmadalin.core.extensions.observe
import com.vmadalin.core.ui.base.BaseFragment
import com.vmadalin.dynamicfeatures.characterslist.R
import com.vmadalin.dynamicfeatures.characterslist.databinding.FragmentCharactersListBinding
import com.vmadalin.dynamicfeatures.characterslist.ui.list.adapter.CharactersListAdapter
import com.vmadalin.dynamicfeatures.characterslist.ui.list.adapter.CharactersListAdapterState
import com.vmadalin.dynamicfeatures.characterslist.ui.list.di.CharactersListModule
import com.vmadalin.dynamicfeatures.characterslist.ui.list.di.DaggerCharactersListComponent
import com.vmadalin.dynamicfeatures.characterslist.ui.list.model.CharacterItem
import javax.inject.Inject

class CharactersListFragment :
    BaseFragment<FragmentCharactersListBinding, CharactersListViewModel>(
        layoutId = R.layout.fragment_characters_list
    ) {

    @Inject
    lateinit var viewAdapter: CharactersListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
    }

    override fun onInitDependencyInjection() {
        DaggerCharactersListComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .charactersListModule(CharactersListModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeList.charactersList.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    private fun onViewDataChange(viewData: PagedList<CharacterItem>) {
        viewAdapter.submitList(viewData)
    }

    private fun onViewStateChange(viewState: CharactersListViewState) {
        when (viewState) {
            is CharactersListViewState.Loaded ->
                viewAdapter.submitState(CharactersListAdapterState.Added)
            is CharactersListViewState.AddLoading ->
                viewAdapter.submitState(CharactersListAdapterState.AddLoading)
            is CharactersListViewState.AddError ->
                viewAdapter.submitState(CharactersListAdapterState.AddError)
            is CharactersListViewState.NoMoreElements ->
                viewAdapter.submitState(CharactersListAdapterState.NoMore)
        }
    }

    private fun onViewEvent(viewEvent: CharactersListViewEvent) {
        when (viewEvent) {
            is CharactersListViewEvent.OpenCharacterDetail ->
                findNavController().navigate(
                    CharactersListFragmentDirections
                        .actionCharactersListFragmentToCharacterDetailFragment(viewEvent.id))
        }
    }
}
