package com.github.gotify.connector

import android.os.*

/**
 * This handler is used to receive notifications
 * from gotify (once registered)
 */

open class GotifyServiceHandler() : Handler(Looper.getMainLooper()) {

    override fun handleMessage(msg: Message) {
        if (!this.isTrusted(msg.sendingUid)) {
            logw("Message received from untrusted ID (${msg.sendingUid})")
            return;
        }
        when (msg.what) {
            TYPE_CHANGED_URL -> onUrlChange(msg.data.getString("changedUrl").orEmpty())
            TYPE_MESSAGE -> onMessage(GotifyMessage().initFromBundle(msg.data))
            else -> super.handleMessage(msg)
        }
    }

    //open fun onMessage(message: Message){}
    open fun onMessage(message: GotifyMessage){}

    open fun onUrlChange(url: String) {}

    open fun isTrusted(uid: Int): Boolean {
        /** You  need to override this function
         * to control gotify uid
         */
        return false
    }
}