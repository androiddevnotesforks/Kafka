package com.kafka.logger

interface Logger { fun log(eventInfo: EventInfo) }

interface Event

typealias EventInfo = Pair<String, Map<String, String>>

operator fun EventInfo.invoke(pair: Pair<String, Map<String, String>>) = pair

