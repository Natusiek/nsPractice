package pl.natusiek.module.common.helper

import java.util.concurrent.ThreadLocalRandom

object RandomHelper {

    private val generator = ThreadLocalRandom.current()

    private fun getRandomDouble(min: Double, max: Double) = generator.nextDouble() * (max - min) + min

    fun getRandomInt(min: Int, max: Int) = this.generator.nextInt(max - min + 1) + min

    fun getChance(chance: Int) = chance >= 100 || chance >= getRandomDouble(0.0, 100.0)

}