package com.itechart.slicer

import com.itechart.slicer.PathFinder.Companion.INITIAL_POINT
import kotlin.math.abs

/**
 * Class is responsible for output generation
 */
class Translator {

    /** Translates [path] to bot's instructions
     * @param path the Path for translation
     * @return converted String
     */
    fun pathToBotInstruction(path: Path): String {
        val instructions: StringBuilder = StringBuilder()
        var activePoint = INITIAL_POINT
        path.points.forEach {

            if (activePoint.x > it.x) {
                instructions.append(rightDirectionCommand.repeat(abs(activePoint.x - it.x)))
            }
            if (activePoint.x < it.x) {
                instructions.append(leftDirectionCommand.repeat(abs(activePoint.x - it.x)))
            }
            if (activePoint.y > it.y) {
                instructions.append(bottomDirectionCommand.repeat(abs(activePoint.y - it.y)))
            }
            if (activePoint.y < it.y) {
                instructions.append(topDirectionCommand.repeat(abs(activePoint.y - it.y)))
            }
            instructions.append(putPizzaCommand)

            activePoint = it
        }
        return instructions.toString()
    }

    companion object {
        const val leftDirectionCommand = "E"
        const val rightDirectionCommand = "W"
        const val topDirectionCommand = "N"
        const val bottomDirectionCommand = "S"

        const val putPizzaCommand = "D"

    }
}