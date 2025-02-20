// Copyright (C) 2011 Chan Wai Shing
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.wakaztahir.codeeditor.prettify.lang

import com.wakaztahir.codeeditor.prettify.lang.Lang
import com.wakaztahir.codeeditor.prettify.parser.StylePattern

/**
 * Lang class for Java Prettify.
 * Note that the method [.getFileExtensions] should be overridden.
 *
 * @author Chan Wai Shing <cws1989></cws1989>@gmail.com>
 */
abstract class Lang {
    /**
     * Similar to those in JavaScript prettify.js.
     */
    internal var shortcutStylePatterns: List<StylePattern>

    /**
     * Similar to those in JavaScript prettify.js.
     */
    internal var fallthroughStylePatterns: List<StylePattern>

    /**
     * See [LangCss] for example.
     */
    internal var extendedLangs: List<Lang>

    fun setShortcutStylePatterns(shortcutStylePatterns: List<StylePattern>) {
        this.shortcutStylePatterns = shortcutStylePatterns.toMutableList()
    }

    fun setFallthroughStylePatterns(fallthroughStylePatterns: List<StylePattern>) {
        this.fallthroughStylePatterns = fallthroughStylePatterns.toMutableList()
    }

    /**
     * Get the extended languages list.
     * @return the list
     */
    fun getExtendedLangs(): List<Lang> {
        return ArrayList(extendedLangs)
    }

    /**
     * Set extended languages. Because we cannot register multiple languages
     * within one [prettify.lang.Lang], so it is used as an solution. See
     * [prettify.lang.LangCss] for example.
     *
     * @param extendedLangs the list of [prettify.lang.Lang]s
     */
    fun setExtendedLangs(extendedLangs: List<Lang>) {
        this.extendedLangs = extendedLangs
    }

    companion object {
        /**
         * This method should be overridden by the child class.
         * This provide the file extensions list to help the parser to determine which
         * [Lang] to use. See JavaScript prettify.js.
         *
         * @return the list of file extensions
         */
        val fileExtensions: List<String>
            get() = ArrayList()
    }

    abstract fun getFileExtensions(): List<String>

    /**
     * Constructor.
     */
    init {
        shortcutStylePatterns = ArrayList()
        fallthroughStylePatterns = ArrayList()
        extendedLangs = ArrayList()
    }
}