// Copyright (C) 2008 Google Inc.
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

import com.wakaztahir.codeeditor.prettify.parser.Prettify
import com.wakaztahir.codeeditor.prettify.parser.StylePattern
import com.wakaztahir.codeeditor.utils.new

/**
 * This is similar to the lang-lua.js in JavaScript Prettify.
 *
 * All comments are adapted from the JavaScript Prettify.
 *
 *
 *
 * Registers a language handler for Lua.
 *
 *
 * To use, include prettify.js and this file in your HTML page.
 * Then put your code in an HTML tag like
 * <pre class="prettyprint lang-lua">(my Lua code)</pre>
 *
 *
 * I used http://www.lua.org/manual/5.1/manual.html#2.1
 * Because of the long-bracket concept used in strings and comments, Lua does
 * not have a regular lexical grammar, but luckily it fits within the space
 * of irregular grammars supported by javascript regular expressions.
 *
 * @author mikesamuel@gmail.com
 */
class LangLua : Lang() {
    companion object {
        val fileExtensions: List<String>
            get() = listOf("lua")
    }

    init {
        val _shortcutStylePatterns: MutableList<StylePattern> = ArrayList()
        val _fallthroughStylePatterns: MutableList<StylePattern> = ArrayList()

        // Whitespace
        _shortcutStylePatterns.new(
            Prettify.PR_PLAIN, Regex("^[\t\n\r \\xA0]+"), null, "\t\n\r " + 0xA0.toChar().toString()
        )
        // A double or single quoted, possibly multi-line, string.
        _shortcutStylePatterns.new(
            Prettify.PR_STRING,
            Regex("^(?:\\\"(?:[^\\\"\\\\]|\\\\[\\s\\S])*(?:\\\"|$)|\\'(?:[^\\'\\\\]|\\\\[\\s\\S])*(?:\\'|$))"),
            null,
            "\"'"
        )
        // A comment is either a line comment that starts with two dashes, or
        // two dashes preceding a long bracketed block.
        _fallthroughStylePatterns.new(
            Prettify.PR_COMMENT, Regex("^--(?:\\[(=*)\\[[\\s\\S]*?(?:\\]\\1\\]|$)|[^\\r\\n]*)")
        )
        // A long bracketed block not preceded by -- is a string.
        _fallthroughStylePatterns.new(
            Prettify.PR_STRING,
            Regex("^\\[(=*)\\[[\\s\\S]*?(?:\\]\\1\\]|$)")
        )
        _fallthroughStylePatterns.new(
            Prettify.PR_KEYWORD,
            Regex("^(?:and|break|do|else|elseif|end|false|for|function|if|in|local|nil|not|or|repeat|return|then|true|until|while)\\b"),
            null
        )
        // A number is a hex integer literal, a decimal real literal, or in
        // scientific notation.
        _fallthroughStylePatterns.new(
            Prettify.PR_LITERAL,
            Regex(
                "^[+-]?(?:0x[\\da-f]+|(?:(?:\\.\\d+|\\d+(?:\\.\\d*)?)(?:e[+\\-]?\\d+)?))",
                RegexOption.IGNORE_CASE
            )
        )
        // An identifier
        _fallthroughStylePatterns.new(
            Prettify.PR_PLAIN,
            Regex("^[a-z_]\\w*", RegexOption.IGNORE_CASE)
        )
        // A run of punctuation
        _fallthroughStylePatterns.new(
            Prettify.PR_PUNCTUATION,
            Regex("^[^\\w\\t\\n\\r \\xA0][^\\w\\n\\r \\xA0\\\"\\'\\-\\+=]*")
        )
        setShortcutStylePatterns(_shortcutStylePatterns)
        setFallthroughStylePatterns(_fallthroughStylePatterns)
    }

    override fun getFileExtensions(): List<String> {
        return fileExtensions
    }
}