package com.example.reactiontraining.shared.domain.engine

/**
 * Задержка после включения всех трёх сигналов до старта окна реакции.
 * Вынесено в интерфейс для подмены в тестах (DIP).
 */
fun interface TrafficLightScheduler {
    fun reactionWindowDelayMs(): Long
}

object DefaultTrafficLightScheduler : TrafficLightScheduler {
    override fun reactionWindowDelayMs(): Long = (1000..3000).random().toLong()
}
