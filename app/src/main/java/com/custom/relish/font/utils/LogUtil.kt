package com.custom.relish.font.utils

import android.util.Log

/**
 *author: fanbingbing
 *date:
 *class desc:
 **/
object LogUtil {
    private const val tag = "FontA_LogUtil"
//    private val isDebug = BuildConfig.DEBUG
    private val isDebug = true  //TODO

    fun i(key: String?, value: String?) {
        if (isDebug) {
            Log.i(key, value!!)
        }
    }

    fun i(value: String?) {
        if (isDebug) {
            Log.i(tag, value!!)
        }
    }

    fun d(key: String?, value: String?) {
        if (isDebug) {
            Log.d(key, value!!)
        }
    }

    fun d(value: String?) {
        if (isDebug) {
            Log.d(tag, value!!)
        }
    }

    fun e(key: String?, value: String?) {
        if (isDebug) {
            Log.e(key, value!!)
        }
    }

    fun e(value: String?) {
        if (isDebug) {
            Log.e(tag, value!!)
        }
    }
}