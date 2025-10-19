package dev.schedler.amortify.presentation.navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import kotlin.uuid.Uuid

class UuidNavType : NavType<Uuid>(isNullableAllowed = false) {
    override val name: String
        get() = "uuid"

    override fun put(bundle: SavedState, key: String, value: Uuid) {
        bundle.write { putString(key, value.toHexString()) }
    }

    override fun get(bundle: SavedState, key: String): Uuid? {
        val value = bundle.read { getString(key) }
        return Uuid.Companion.parseHex(value)
    }

    override fun parseValue(value: String): Uuid = Uuid.Companion.parseHex(value)

    override fun serializeAsValue(value: Uuid): String = value.toHexString()
}