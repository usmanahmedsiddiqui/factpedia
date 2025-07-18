package com.sample.factpedia.pages

import android.content.Context

abstract class Page(protected val context: Context) {
    abstract fun at()
}