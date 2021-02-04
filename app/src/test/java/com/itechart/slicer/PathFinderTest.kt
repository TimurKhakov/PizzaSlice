package com.itechart.slicer

import com.itechart.slicer.PathFinder.Companion.INITIAL_POINT
import org.junit.Assert.assertEquals
import org.junit.Test


class PathFinderTest {

    private val testPointsData =
        listOf(Point(1, 1), Point(4, 0), Point(4, 6), Point(6, 9), Point(6, 2))
    private val expectedPointsDataForGreedAlgorithm =
        listOf(Point(1, 1), Point(4, 0), Point(6, 2), Point(4, 6), Point(6, 9))

    private val pathFinder = PathFinder()

    @Test
    fun given_test_route_when_processed_by_greed_algorithm_then_show_possible_path() {
        val path =
            pathFinder.greedy(INITIAL_POINT, testPointsData.shuffled(), Path(mutableListOf(), 0))

        assertEquals(expectedPointsDataForGreedAlgorithm, path.points)
        assertEquals(21, path.distance)
    }

    @Test
    fun given_test_route_when_processed_by_slice_finder_proceed_then_show_possible_path() {
        val path = pathFinder.verticalSlicer(INITIAL_POINT, testPointsData.shuffled())

        assertEquals(testPointsData, path.points)
        assertEquals(24, path.distance)
    }

    @Test
    fun given_start_point_when_calculate_distance_to_other_point_then_show_correct_value() {
        val startPoint = Point(2, 0)
        assertEquals(2, startPoint.distanceTo(Point(0, 0)))
        assertEquals(7, startPoint.distanceTo(Point(0, 5)))
        assertEquals(7, startPoint.distanceTo(Point(7, 2)))
    }

    @Test
    fun given_slice_of_points_when_calculate_path_then_inverse_order() {
        val slice = listOf(Point(2, 3), Point(2, 10))
        val path = pathFinder.goToNextSlice(Point(0, 12), slice)
        assertEquals(slice.asReversed(), path.points)
    }

    @Test
    fun given_slice_of_points_when_calculate_path_then_save_order() {
        val slice = listOf(Point(2, 3), Point(2, 10))
        val path = pathFinder.goToNextSlice(Point(0, 5), slice)
        assertEquals(slice, path.points)
    }

}