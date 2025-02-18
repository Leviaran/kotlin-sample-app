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

package com.vmadalin.dynamicfeatures.characterslist.ui.detail.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.vmadalin.core.database.characterfavorite.CharacterFavoriteRepository
import com.vmadalin.core.di.scopes.FeatureScope
import com.vmadalin.core.extensions.viewModel
import com.vmadalin.core.network.repositiories.MarvelRepository
import com.vmadalin.core.ui.customviews.ProgressBarDialog
import com.vmadalin.dynamicfeatures.characterslist.ui.detail.CharacterDetailFragment
import com.vmadalin.dynamicfeatures.characterslist.ui.detail.CharacterDetailViewModel
import com.vmadalin.dynamicfeatures.characterslist.ui.detail.model.CharacterDetailMapper
import dagger.Module
import dagger.Provides

@Module
class CharacterDetailModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: CharacterDetailFragment
) {

    @FeatureScope
    @Provides
    fun providesCharacterDetailViewModel(
        marvelRepository: MarvelRepository,
        characterFavoriteRepository: CharacterFavoriteRepository,
        characterDetailMapper: CharacterDetailMapper
    ) = fragment.viewModel {
        CharacterDetailViewModel(
            marvelRepository = marvelRepository,
            characterFavoriteRepository = characterFavoriteRepository,
            characterDetailMapper = characterDetailMapper
        )
    }

    @FeatureScope
    @Provides
    fun providesCharacterDetailMapper() = CharacterDetailMapper()

    @FeatureScope
    @Provides
    fun providesProgressBarDialog() = ProgressBarDialog(fragment.requireContext())
}
