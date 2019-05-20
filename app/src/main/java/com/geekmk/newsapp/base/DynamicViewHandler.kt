package com.geekmk.newsapp.base

import android.app.Activity
import android.support.annotation.ColorRes
import android.support.annotation.IntDef
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.LinearLayout
import android.widget.ScrollView
import com.geekmk.newsapp.R

class DynamicViewHandler(view: View) {

    var user: ErrorViewUser? = null
    private val root: ViewGroup
    private var errorView: View? = null
    private var prevState = 6

    init {
        val v = view as ViewGroup
        /*root = if (v is ScrollView || v is RecyclerView) {
            v.parent as ViewGroup
        } else {
            v
        }*/

        root = if (v is ScrollView || v is RecyclerView
                || v is AbsListView || v is NestedScrollView
        ) {
            if (v.parent == null) {
                val a = v.context as Activity
                a.window.decorView as ViewGroup
            } else {
                v.parent as ViewGroup
            }
        } else {
            v
        }
    }

    /***********************************************************/
    companion object {
        const val INTERNET: Int = 1
        const val CLIENT: Int = 2
        const val SERVER: Int = 3
        const val EMPTY: Int = 4
        const val TIMEOUT: Int = 5
        const val PROGRESS: Int = 6
    }

    @IntDef(
        INTERNET,
        CLIENT,
        SERVER,
        EMPTY,
        TIMEOUT,
        PROGRESS
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class ErrorView

    /***********************************************************/

    fun show(@ErrorView code: Int, @ColorRes backgroundColor: Int = R.color.white) {
        prevState = code
        var layoutId = user?.getErrorLayout(code)

        if (layoutId == null || layoutId == 0) {
            layoutId = getDefaultLayoutId(code)
        }
        root.removeView(errorView)
        errorView = LayoutInflater.from(root.context).inflate(layoutId, root, false)
        if (backgroundColor != R.color.white) {
            errorView?.setBackgroundResource(backgroundColor)
        }
        root.addView(errorView, root.childCount)
        if (root !is LinearLayout) {
            root.bringChildToFront(errorView)
            errorView?.visibility = View.VISIBLE
        }
        user?.onDynamicViewCreated(errorView!!, code)
    }

    @LayoutRes
    private fun getDefaultLayoutId(@ErrorView errorCode: Int): Int {
        when (errorCode) {
            INTERNET -> return R.layout.partial_error_no_internet
            CLIENT, SERVER -> return R.layout.partial_error_api
            PROGRESS -> return R.layout.progress_bar_layout

        }
        return R.layout.partial_error_api
    }

    fun hide() {
        removeView()
    }

    private fun removeView() {
        root.removeView(errorView)
    }

    /**
     * Maps http error and custom network error code to
     *
     */
    @ErrorView
    fun mapHttpErrorToViewCode(errorCode: Int): Int {
        var code: Int = SERVER
        when (errorCode) {
            in 400..499 -> code = CLIENT
            NetworkErrorCode.ERROR_NO_INTERNET -> code =
                INTERNET
        }
        return code
    }

    interface ErrorViewUser {
        fun onDynamicViewCreated(@NonNull errorView: View, @ErrorView errorCode: Int)
        fun getErrorLayout(@ErrorView errorCode: Int): Int {
            return 0
        }
    }
}


