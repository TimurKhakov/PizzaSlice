package com.itechart.slicer

import java.util.regex.Pattern

/**
 * Class is responsible for input recognition
 */
class InputParser {

    /** Converts input String to command with state and data
     *  @param inputCommand the String representation of user input
     *  @return the state and parsed data in UserCommand
     */
    fun parseInputCommand(inputCommand: String): UserCommand {
        val fullCommandPattern = Pattern.compile("$PATTERN_GRID($PATTERN_POINT)+")
        return if (fullCommandPattern.matcher(inputCommand).matches()) {
            translateToCommand(inputCommand)
        } else {
            CommandError(ERROR_COMMAND)
        }
    }

    /** Collects parsed data and translate it to UserCommand
     *  @param command the full String representation of user input
     *  @return the state and parsed data in UserCommand
     */
    private fun translateToCommand(command: String): UserCommand {
        return try {
            val grid = extractGrid(command)
            val points = extractPoints(command)
            validateCommand(grid, points)
        } catch (e: ParseException) {
            CommandError(e.message ?: ERROR_PARSE)
        }
    }

    /** Validates parsed configuration
     * @param grid the Pair with dimensions
     * @param points the list of Points for validation
     * @return StartRoute state if configuration valid, CommandError otherwise
     */
    private fun validateCommand(grid: Pair<Int, Int>, points: List<Point>): UserCommand {

        val configuration = Configuration(grid, points)
        return if (configuration.isValid()) {
            StartRoute(configuration)
        } else {
            CommandError(ERROR_CONFIGURATION)
        }
    }

    /** Parses input into segments and convert to list of Points
     * @param fullInput the full String representation of user input
     * @return extracted list of Points
     */
    internal fun extractPoints(fullInput: String): List<Point> {

        val matcher = Pattern.compile(PATTERN_POINT).matcher(fullInput)
        val result = mutableListOf<Point>()
        while (matcher.find()) {
            val coordinates = extractValues(matcher.group())
            result.add(Point(coordinates.first, coordinates.second))
        }
        if (result.isNotEmpty()) {
            return result
        } else {
            throw ParseException(ERROR_PARSE)
        }
    }

    /** Parses input into segments and converts it to list of Points
     * @param fullInput the full String representation of user input
     * @return extracted grid dimension
     */
    internal fun extractGrid(fullInput: String): Pair<Int, Int> {
        val matcher = Pattern.compile(PATTERN_GRID).matcher(fullInput)
        return if (matcher.find()) {
            extractValues(matcher.group())
        } else {
            throw ParseException(ERROR_PARSE)
        }
    }

    /** Parses selected segment and converts it to Pair of integers
     * @param commandString segment of user input
     * @return extracted Pair with integer values
     */
    internal fun extractValues(commandString: String): Pair<Int, Int> {

        val matcher = Pattern.compile(PATTERN_DECIMAL).matcher(commandString)
        val list = mutableListOf<Int>()
        while (matcher.find()) {
            list.add(matcher.group().toInt())
        }
        if (list.size == 2) {
            return Pair(list[0], list[1])
        } else {
            throw ParseException(ERROR_PARSE)
        }
    }

    companion object {
        const val ERROR_PARSE = "Parse error"
        const val ERROR_CONFIGURATION = "Configuration error"
        const val ERROR_COMMAND = "Command error"
        private const val PATTERN_DECIMAL = "\\d+"
        private const val PATTERN_GRID = "\\d+x\\d+"
        private const val PATTERN_POINT = "\\s?\\(\\d+,\\s?\\d+\\)"
    }
}

/**
 * Custom exception to handle
 */
class ParseException(message: String) : Exception(message)

/** Group of state classes.
 * Contains additional data
 */
sealed class UserCommand
data class StartRoute(val configuration: Configuration) : UserCommand()
data class CommandError(val message: String) : UserCommand()

/**
 * Helper class store parser result
 */
data class Configuration(val grid: Pair<Int, Int>, val points: List<Point>) {
    private val bottomBorderByX = 0
    private val bottomBorderByY = 0

    /** Validate correctness of points with grid config
     *  @return `true` if all points inside grid, `false` otherwise.
     */
    fun isValid(): Boolean {
        return points.firstOrNull {
            it.x < bottomBorderByX || it.y < bottomBorderByY
                    || it.x > grid.first || it.y > grid.second
        } == null
    }
}