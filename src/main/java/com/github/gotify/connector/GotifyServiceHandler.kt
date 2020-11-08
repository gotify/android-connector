package com.github.gotify.connector

import android.content.Context
import android.os.*

/**
 * This handler is used to receive notifications
 * from gotify (once registered)
 */

open class GotifyServiceHandler(private val context: Context?) : Handler() {

    override fun handleMessage(msg: Message) {
        if (!this.isTrusted(msg.sendingUid)) {
            logw("Message received from untrusted ID (${msg.sendingUid})")
            return;
        }
        when (msg.what) {
            TYPE_CHANGED_URL -> onUrlChange(msg.data.getString("changedUrl").orEmpty())
            TYPE_MESSAGE -> onMessage(msg)
            else -> super.handleMessage(msg)
        }
    }

    open fun onMessage(message: Message){}

    open fun onUrlChange(url: String) {}

    open fun isTrusted(uid: Int): Boolean {
        /** If you don't want to use a context, you'll have
         * to override this function
         */
        if (context == null){
            logw("No context with GotifyServiceHandler, No Id is trusted")
            return false
        }
        return uid == getGotifyIdInSharedPref(context)
    }
}