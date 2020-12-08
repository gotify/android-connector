package com.github.gotify.connector

import java.time.OffsetDateTime

data class GotifyMessage(
    var appid: Long,
    var date: OffsetDateTime,
    var extras: Map<String, Object>,
    var id: Long,
    var message: String,
    var priority: Long,
    var title: String
)