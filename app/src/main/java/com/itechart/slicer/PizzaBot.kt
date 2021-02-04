package com.itechart.slicer

import com.itechart.slicer.PathFinder.Companion.INITIAL_POINT

/**
 * Class is responsible for all system functionality
 */
class PizzaBot {
    private val pathFinder = PathFinder()
    private val inputParser = InputParser()
    private val translator = Translator()

    /** Process user [inputCommand] and convert it to bot's instructions
     *  @param inputCommand the String representation of user command
     *  @return the String representation of bot instructions or errors
     */
    fun readInputCommand(inputCommand: String): String {
        return when (val command = inputParser.parseInputCommand(inputCommand)) {
            is StartRoute -> translator.pathToBotInstruction(
                pathFinder.greedy(
                    INITIAL_POINT,
                    command.configuration.points,
                    Path(mutableListOf(), 0)
                )
            )
            is CommandError -> { //requires any correct user notification
                command.message
            }
        }
    }
}