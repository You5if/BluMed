package com.component.pharma.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.text.AlteredCharSequence.make
import android.text.BoringLayout.make
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.component.pharma.data.Resource
import com.component.pharma.ui.auth.AuthFragment
import com.component.pharma.ui.auth.OTPFragment
import com.component.pharma.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make

fun<A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

}

fun View.visible(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun View.toast(message: String) {
    val toast = Toast.makeText(this.context, message, Toast.LENGTH_SHORT)

    toast.show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when{
//        failure.isNetworkError -> requireView().snackbar("Please check your Internet connection", retry)
        failure.isNetworkError -> requireView().toast("Please check your Internet connection")
        failure.errorCode == 400 -> {
            when (this) {
                is AuthFragment -> {
                    requireView().snackbar("You've entered incorrect number")
                }
                is OTPFragment -> {
                    requireView().snackbar("Invalid OTP")
                }
                else -> {
                    requireView().snackbar("logout here")
                }
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}

fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    var cursor = query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name
}