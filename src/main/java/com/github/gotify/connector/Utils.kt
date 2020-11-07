package com.github.gotify.connector

import android.content.Context
import android.util.Log

fun logi(msg: String){
    Log.i("GotifyServiceBind",msg)
}

fun logw(msg: String){
    Log.w("GotifyServiceBind",msg)
}

fun registerGotifyIdInSharedPref(context: Context,id: Int){
    // Trust only the app we registered to
    // We don't trust other apps
    context.getSharedPreferences(PREF_GOTIFY, Context.MODE_PRIVATE).edit().putInt(PREF_GOTIFY_KEY_ID, id).commit()
}