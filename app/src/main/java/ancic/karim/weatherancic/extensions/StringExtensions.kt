package ancic.karim.weatherancic.extensions

import java.util.*

fun String.capitalize(): String = replaceFirstChar { dateText ->
    if (dateText.isLowerCase()) {
        dateText.titlecase(Locale("hr"))
    } else {
        dateText.toString()
    }
}

