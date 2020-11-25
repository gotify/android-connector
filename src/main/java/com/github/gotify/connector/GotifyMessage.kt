package com.github.gotify.connector

import java.time.OffsetDateTime

class GotifyMessage{
    var appid: Long? = null;
    var date: OffsetDateTime? = null;
    var extras: Map<String, Object>? = null
    var id: Long? = null
    var message: String? = null
    var priority: Long? = null
    var title: String? = null
}