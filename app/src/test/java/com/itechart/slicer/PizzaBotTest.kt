package com.itechart.slicer

import com.itechart.slicer.InputParser.Companion.ERROR_COMMAND
import com.itechart.slicer.InputParser.Companion.ERROR_CONFIGURATION
import org.junit.Assert.assertEquals
import org.junit.Test


class PizzaBotTest {

    private val basicTestInput = "5x5 (1, 3) (4, 4)"
    private var longTestInput = "5x5 (0, 0) (1, 3) (4, 4) (4, 2) (4, 2) (0, 1) (3, 2) (2, 3) (4, 1)"

    private val basicInstructionsExpectation = "ENNNDEEEND"
    private val longInstructionsExpectation = "DNDENNDEDESDEDDSDNNND"

    private val pizzaBot = PizzaBot()

    @Test
    fun given_correct_input_when_bot_proceed_by_greedy_algorithm_then_show_correct_instructions() {
        assertEquals(basicInstructionsExpectation, pizzaBot.readInputCommand(basicTestInput))
        assertEquals(longInstructionsExpectation, pizzaBot.readInputCommand(longTestInput))
    }

    @Test
    fun given_incorrect_input_when_bot_proceed_by_greedy_algorithm_then_show_error() {
        assertEquals(ERROR_COMMAND, pizzaBot.readInputCommand("Wrong command"))
    }

    @Test
    fun given_incorrect_configuration_when_bot_proceed_by_greedy_algorithm_then_show_error() {
        assertEquals(ERROR_CONFIGURATION, pizzaBot.readInputCommand("5x5 (1, 55) (4, 4)"))
    }

}