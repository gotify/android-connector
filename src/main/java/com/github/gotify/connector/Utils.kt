package com.github.gotify.connector

import android.content.Context
import android.util.Log

fun logi(msg: String){
    Log.i("Gotify Connector",msg)
}

fun logw(msg: String){
    Log.w("Gotify Connector",msg)
}

fun registerGotifyIdInSharedPref(context: Context,id: Int){
    // Trust only the app we registered to
    // We don't trust other apps
    context.getSharedPreferences(PREF_GOTIFY, Context.MODE_PRIVATE).edit().putInt(PREF_GOTIFY_KEY_ID, id).commit()
}

fun getGotifyIdInSharedPref(context: Context): Int {
    return context.getSharedPreferences(PREF_GOTIFY, Context.MODE_PRIVATE)?.getInt(
            PREF_GOTIFY_KEY_ID, 0
    )!!
}