package com.example.reactiontraining.shared.domain.engine

fun interface TrafficLightScheduler {
    fun reactionWindowDelayMs(): Long
}

object DefaultTrafficLightScheduler : TrafficLightScheduler {
    override fun reactionWindowDelayMs(): Long = (1000..3000).random().toLong()
}
