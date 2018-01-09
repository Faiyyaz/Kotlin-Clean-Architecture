package com.dexter.kotlinbaseapp.utils

import android.app.AlertDialog
import android.content.Context

import dmax.dialog.SpotsDialog

/**
 * Created by Admin on 02-01-2018.
 */

object DialogUtils {

    fun getDialog(context: Context, message: String): AlertDialog {
        return SpotsDialog(context)
    }
}
