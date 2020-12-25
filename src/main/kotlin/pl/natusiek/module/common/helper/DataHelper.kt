package pl.natusiek.module.common.helper

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

object DataHelper {

    private val dateFormatSimple = SimpleDateFormat("dd-MM-yyyy, HH:mm:ss")

    fun formatData(time: Long) = this.dateFormatSimple.format(Date(time)).toString()

    fun formatTime(millis: Long): String {
        val sec = (millis / 1000 % 60).toInt()
        val min = (millis / 60000 % 60).toInt()
        val hr = (millis / 3600000 % 24).toInt()
        return (if (hr > 0) String.format("%02d:", hr) else "") + String.format("%02d:%02d", min, sec)
    }

    fun parseLong(milliseconds: Long, abbreviate: Boolean): String? {
        val units = ArrayList<String>()
        var amount = milliseconds / (7 * 24 * 60 * 60 * 1000)
        units.add(amount.toString() + "w")
        amount = milliseconds / (24 * 60 * 60 * 1000) % 7
        units.add(amount.toString() + "d")
        amount = milliseconds / (60 * 60 * 1000) % 24
        units.add(amount.toString() + "h")
        amount = milliseconds / (60 * 1000) % 60
        units.add(amount.toString() + "m")
        amount = milliseconds / 1000 % 60
        units.add(amount.toString() + "s")
        val array = arrayOfNulls<String>(5)
        var end: Char
        units.forEach {
            end = it[it.length - 1]
            when (end) {
                'w' -> {
                    array[0] = it
                    array[1] = it
                    array[2] = it
                    array[3] = it
                    array[4] = it
                }
                'd' -> {
                    array[1] = it
                    array[2] = it
                    array[3] = it
                    array[4] = it
                }
                'h' -> {
                    array[2] = it
                    array[3] = it
                    array[4] = it
                }
                'm' -> {
                    array[3] = it
                    array[4] = it
                }
                's' -> array[4] = it
            }
        }
        units.clear()
        array.filter { !it!!.startsWith("0") }.forEach { units.add(it!!) }
        val sb = StringBuilder()
        var count: String
        var and: String
        var c: Char
        for (s in units) {
            if (abbreviate) {
                c = s[s.length - 1]
                count = s.substring(0, s.length - 1)
                and = if (s == units[units.size - 1]) "" else if (s == units[units.size - 2]) " i " else ", "
                sb.append(count).append(" ").append(c).append(and)
            } else {
                sb.append("&f$s")
                if (s != units[units.size - 1])
                    sb.append("&8:")
            }
        }
        return if (sb.toString().trim { it <= ' ' }.isEmpty()) null else sb.toString().trim { it <= ' ' }
    }
}