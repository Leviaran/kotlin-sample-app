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

package com.vmadalin.dynamicfeatures.home.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.vmadalin.android.SampleApp
import com.vmadalin.core.extensions.setupWithNavController
import com.vmadalin.core.ui.base.BaseFragment
import com.vmadalin.dynamicfeatures.home.R
import com.vmadalin.dynamicfeatures.home.databinding.FragmentHomeBinding
import com.vmadalin.dynamicfeatures.home.ui.di.DaggerHomeComponent
import com.vmadalin.dynamicfeatures.home.ui.di.HomeModule

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    layoutId = R.layout.fragment_home
) {

    private val navGraphIds = listOf(
        R.navigation.navigation_characters_list_graph,
        R.navigation.navigation_characters_favorites_graph
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onInitDependencyInjection() {
        DaggerHomeComponent
            .builder()
            .coreComponent(SampleApp.coreComponent(requireContext()))
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    // ============================================================================================
    //  Private methods
    // ============================================================================================

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        requireCompatActivity().setSupportActionBar(viewBinding.toolbar)
    }

    private fun setupBottomNavigationBar() {
        val navController = viewBinding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.nav_host_container,
            intent = requireActivity().intent
        )

        navController.observe(viewLifecycleOwner, Observer {
            viewModel.navigationControllerChanged(it)
            setupActionBarWithNavController(requireCompatActivity(), it)
        })
    }
}
