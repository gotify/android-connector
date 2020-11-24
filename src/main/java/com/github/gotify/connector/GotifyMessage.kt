package com.github.gotify.connector

import android.os.Bundle
import java.time.OffsetDateTime


class GotifyMessage{
    var appid: Long? = null;
    var date: OffsetDateTime? = null;
    var extras: Map<String, Object>? = null
    var id: Long? = null
    var message: String? = null
    var priority: Long? = null
    var title: String? = null

    fun initFromBundle(data: Bundle): GotifyMessage {
        appid = data.getLong("appid")
        id = data.getLong("id")
        message = data.getString("message")
        priority = data.getLong("priority")
        title = data.getString("title")
        return this
    }

}