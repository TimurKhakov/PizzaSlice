package com.itechart.slicer

import com.itechart.slicer.InputParser.Companion.ERROR_CONFIGURATION
import com.itechart.slicer.InputParser.Companion.ERROR_PARSE
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test


class InputParserTest {

    private val expectedConfiguration = Configuration(
        Pair(5, 5), listOf(
            Point(1, 3), Point(4, 4)
        )
    )
    private val correctedState = StartRoute(expectedConfiguration)
    private val errorState = CommandError(ERROR_PARSE)

    private val inputParser = InputParser()

    @Test
    fun given_correct_test_input_when_processed_parser_then_return_correct_configuration() {
        assertEquals(correctedState, inputParser.parseInputCommand("5x5 (1, 3) (4, 4)"))
        assertEquals(correctedState, inputParser.parseInputCommand("5x5 (1,3) (4, 4)"))
        assertEquals(correctedState, inputParser.parseInputCommand("5x5 (1, 3)(4, 4)"))
        assertEquals(correctedState, inputParser.parseInputCommand("5x5(1, 3)(4,4)"))
    }

    @Test
    fun given_incorrect_test_input_when_processed_parser_then_return_command_error() {
        assertEquals(
            CommandError(ERROR_CONFIGURATION),
            inputParser.parseInputCommand("5x5 (1, 15) (4, 4)")
        )
    }

    @Test
    fun given_test_input_when_extract_grid_then_return_correct_grid() {
        assertEquals(Pair(5, 5), inputParser.extractGrid("5x5 (1, 3) (4, 4)"))
    }

    @Test
    fun given_incorrect_test_input_when_extract_grid_then_throw_error() {

        try {
            inputParser.extractGrid("56 (1, 3) (4, 4)")
            fail()
        } catch (e: ParseException) {

        }
    }

    @Test
    fun given_test_input_when_extract_points_then_return_correct_list_of_points() {
        assertEquals(
            listOf(Point(1, 3), Point(4, 4)),
            inputParser.extractPoints("5x5 (1, 3) (4, 4)")
        )
    }

    @Test
    fun given_incorrect_test_input_when_extract_points_then_throw_error() {

        try {
            inputParser.extractGrid("5:7 (1) (4, 4)")
            fail()
        } catch (e: ParseException) {

        }
    }

    @Test
    fun given_test_input_when_extract_values_then_return_correct_pair() {
        assertEquals(Pair(1, 3), inputParser.extractValues("(1, 3)"))
    }

    @Test
    fun given_incorrect_test_input_when_extract_values_then_throw_error() {

        try {
            inputParser.extractValues("(4, 4, 1)")
            fail()
        } catch (e: ParseException) {

        }
    }


}