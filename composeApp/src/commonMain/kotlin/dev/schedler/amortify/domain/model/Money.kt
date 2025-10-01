package dev.schedler.amortify.domain.model

import kotlin.jvm.JvmInline
import kotlin.math.absoluteValue

@JvmInline
value class Money(val cents: Long) {

    constructor(units: Int, cents: Int) : this(units * 100L + cents)

    operator fun plus(other: Money): Money {
        return Money(cents + other.cents)
    }

    operator fun minus(other: Money): Money {
        return Money(cents - other.cents)
    }

    override fun toString(): String {
        val units = cents.absoluteValue / 100
        val cents = cents.absoluteValue % 100
        val sign = if (cents < 0) "-" else ""

        return "$sign$units.${cents.toString().padStart(2, '0')}"
    }
}