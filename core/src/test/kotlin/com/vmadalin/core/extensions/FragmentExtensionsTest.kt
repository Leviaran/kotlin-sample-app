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

package com.vmadalin.core.extensions

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.vmadalin.libraries.testutils.TestFragment
import com.vmadalin.libraries.testutils.robolectric.TestRobolectric
import org.hamcrest.Matchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

class FragmentExtensionsTest : TestRobolectric() {

    private open class TestViewModel(val state: Lifecycle.State? = null) : ViewModel()

    @Test
    fun providedViewModel_ShouldObtainWithStateSaved() {
        val fragmentScenario = launchFragmentInContainer<TestFragment>()
        val expectedState = Lifecycle.State.INITIALIZED

        fragmentScenario.onFragment {
            val createdViewModel = it.viewModel { TestViewModel(expectedState) }
            assertThat(createdViewModel, Matchers.instanceOf(TestViewModel::class.java))
            assertEquals(expectedState, createdViewModel.state)

            val providedViewModel = ViewModelProviders.of(it).get(TestViewModel::class.java)
            assertThat(providedViewModel, Matchers.instanceOf(TestViewModel::class.java))
            assertEquals(expectedState, providedViewModel.state)
        }
    }

    @Test
    fun providedViewModelByIdentifier_ShouldObtainWithStateSaved() {
        val fragmentScenario = launchFragmentInContainer<TestFragment>()
        val expectedState = Lifecycle.State.INITIALIZED
        val identifierViewModel = "TestViewModel"

        fragmentScenario.onFragment {
            val createdViewModel =
                it.viewModel(identifierViewModel) { TestViewModel(expectedState) }
            assertThat(createdViewModel, Matchers.instanceOf(TestViewModel::class.java))
            assertEquals(expectedState, createdViewModel.state)

            val providedViewModel =
                ViewModelProviders.of(it).get(identifierViewModel, TestViewModel::class.java)
            assertThat(providedViewModel, Matchers.instanceOf(TestViewModel::class.java))
            assertEquals(expectedState, providedViewModel.state)
        }
    }

    @Test(expected = RuntimeException::class)
    fun providedViewModelWithWrongIdentifier_ShouldNotObtain() {
        val fragmentScenario = launchFragmentInContainer<TestFragment>()
        val identifierViewModel = "TestViewModel"

        fragmentScenario.onFragment {
            it.viewModel(identifierViewModel) { TestViewModel() }
            ViewModelProviders.of(it).get("Wrong Identifier", TestViewModel::class.java)
        }
    }

    @Test(expected = RuntimeException::class)
    fun notProvidedViewModel_ShouldNotObtain() {
        val fragmentScenario = launchFragmentInContainer<TestFragment>()

        fragmentScenario.onFragment {
            ViewModelProviders.of(it).get(TestViewModel::class.java)
        }
    }
}
