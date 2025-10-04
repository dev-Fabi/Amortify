package dev.schedler.amortify.domain.model

import kotlin.jvm.JvmInline
import kotlin.math.absoluteValue

@JvmInline
value class Money(val cents: Long) {

    constructor(units: Int, cents: Int) : this(units * 100L + cents.absoluteValue)

    operator fun plus(other: Money): Money {
        return Money(cents + other.cents)
    }

    operator fun minus(other: Money): Money {
        return Money(cents - other.cents)
    }

    override fun toString(): String = toString(decimalSeparator = '.')

    fun toString(decimalSeparator: Char): String {
        val units = cents.absoluteValue / 100
        val cents = cents.absoluteValue % 100
        val sign = if (cents < 0) "-" else ""

        return "$sign $units$decimalSeparator${cents.toString().padStart(2, '0')}".trim()
    }

    companion object {
        fun fromString(value: String): Money? {
            val moneyRegex = Regex("^(?:- ?)?(\\d*)(?:[.,](\\d{0,2}))?$")
            val match = moneyRegex.find(value) ?: return null

            val units = match.groupValues[1].ifEmpty { "0" }.toInt()
            val cents = match.groupValues[2].padEnd(2, '0').toInt()

            return if (value.startsWith("-")) Money(-units, cents) else Money(units, cents)
        }
    }
}