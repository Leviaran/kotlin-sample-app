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

package com.vmadalin.core.ui.bindings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.api.load
import com.vmadalin.core.R
import kotlin.random.Random

@BindingAdapter("imageUrl", "imagePlaceholder", requireAll = false)
fun ImageView.imageUrl(url: String?, @DrawableRes placeholderId: Int?) {
    load(url) {
        crossfade(true)
        placeholder(placeholderId?.let {
            ContextCompat.getDrawable(context, it)
        } ?: run {
            val placeholdersColors = resources.getStringArray(R.array.placeholders)
            val placeholderColor = placeholdersColors[Random.nextInt(placeholdersColors.size)]
            ColorDrawable(Color.parseColor(placeholderColor))
        })
    }
}
