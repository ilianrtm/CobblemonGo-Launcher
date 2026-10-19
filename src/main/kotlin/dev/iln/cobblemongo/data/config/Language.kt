package dev.iln.cobblemongo.data.config

import dev.iln.cobblemongo.data.config.ConfigStore.config
import java.util.*

enum class Language(val tag: String?) {
    Default(null),
    French("fr"),
    English("en"),
    German("de"),
    Spanish("es"),
    Brazilian("pt");


    companion object {
        val applied = config.value.language

        fun apply() {
            applied.tag?.let { Locale.setDefault(Locale.forLanguageTag(it)) }
        }
    }
}