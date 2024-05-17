package com.custom.relish.font.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.FragmentActivity

/**
 *author: hss
 *date: 2024/5/15 18:23
 *class desc:
 **/
object SoftKeyBordUtil {

    fun isSoftShowing(activity: Activity): Boolean {
        val height = activity.window.decorView.height
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return height - rect.bottom != 0
    }

    /**
     * 弹出软键盘
     */
    fun showSoftKeyboard(activity: Activity, view: EditText) {
        val imm: InputMethodManager =
            activity.getSystemService(FragmentActivity.INPUT_METHOD_SERVICE) as InputMethodManager;
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    fun hideSoftKeyboard(activity: Activity, view: EditText) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isActive)
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}