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

include(
    BuildModules.APP,
    BuildModules.CORE,
    BuildModules.Features.HOME,
    BuildModules.Features.CHARACTERS_LIST,
    BuildModules.Features.CHARACTERS_FAVORITES,
    BuildModules.Libraries.TEST_UTILS
)

rootProject.buildFileName = "build.gradle.kts"
