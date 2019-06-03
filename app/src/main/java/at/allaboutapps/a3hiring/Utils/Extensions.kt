package at.allaboutapps.a3hiring.Utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Muthukumar Neelamegam on 4/12/2019.
 * android-hiring
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes,this,attachToRoot)
}