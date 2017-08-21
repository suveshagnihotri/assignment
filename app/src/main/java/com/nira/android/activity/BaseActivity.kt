package com.suvesh.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import android.view.WindowManager
import com.manaschaudhari.android_mvvm.ViewModel
import com.manaschaudhari.android_mvvm.adapters.ViewModelBinder
import com.manaschaudhari.android_mvvm.utils.BindingUtils
import com.manaschaudhari.android_mvvm.utils.Preconditions
import com.readystatesoftware.systembartint.SystemBarTintManager
import java.io.Serializable
import java.util.*

/**
 * Created by Suvesh on 07/08/2017 AD.
 */
abstract class BaseActivity : Activity() {
    private var binding: ViewDataBinding? = null

    enum class WindowState {
        NORMAL, FULLSCREEN, FULLSCREEN_WITH_STATUS_BAR, NORMAL_WITH_BOTTOM_BAR
    }

    enum class ToolbarState {
        BACK_NAV
    }

    companion object {
        @JvmStatic
        fun newIntent(context: Context, extras : HashMap<String, Serializable?>?, cls : Class<*>): Intent {
            val i = Intent(context, cls)
            if (extras != null) {
                for((key, value) in extras)
                    i.putExtra(key, value)
            }
            return i
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ViewDataBinding>(this, layoutId)
        defaultBinder.bind(binding, createViewModel())
    }

    override fun onDestroy() {
        defaultBinder.bind(binding, null)
        binding!!.executePendingBindings()
        super.onDestroy()
    }

    private val defaultBinder: ViewModelBinder
        get() {
            val defaultBinder = BindingUtils.getDefaultBinder()
            Preconditions.checkNotNull(defaultBinder!!, "Default Binder")
            return defaultBinder
        }

    protected abstract fun createViewModel(): ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int


    protected fun initWindow(windowState: WindowState, statusBarColor: Int, layoutResId: Int) {
        when(windowState) {
            WindowState.FULLSCREEN -> this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            WindowState.FULLSCREEN_WITH_STATUS_BAR -> {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
                    window.statusBarColor = statusBarColor
                else if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    val tintManager = SystemBarTintManager(this)
                    tintManager.isStatusBarTintEnabled = true
                    tintManager.setStatusBarTintColor(statusBarColor)
                }
            }
            WindowState.NORMAL -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    val tintManager = SystemBarTintManager(this)
                    tintManager.isStatusBarTintEnabled = true
                    tintManager.setStatusBarTintColor(statusBarColor)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = statusBarColor
                }
            }

        }
    }

}