package com.itechart.slicer

import org.junit.Assert.assertEquals
import org.junit.Test


class TranslatorTest {

    private val testPath =
        Path(
            listOf(
                Point(1, 1), Point(4, 0), Point(4, 6),
                Point(6, 8), Point(6, 2)
            ),
            20
        )
    private val expectedNavigation = "ENDEEESDNNNNNNDEENNDSSSSSSD"

    private val translator = Translator()

    @Test
    fun given_test_path_when_processed_by_translator_then_return_correct_instruction() {
        assertEquals(expectedNavigation, translator.pathToBotInstruction(testPath))
    }

}