package com.itechart.slicer

import kotlin.math.abs

/**
 * Class is responsible for path generation
 */
class PathFinder {

    /** Implementations of recursive greedy algorithm based on movement to nearest neighbor
     *@param position the Point from which the path will be calculated
     *@param points list of unvisited points
     *@param path already generated Path
     *@return accumulated [path] with Path for this iteration
     */
    fun greedy(position: Point, points: List<Point>, path: Path): Path {
        if (points.isEmpty()) {
            return path
        }

        val pointAndDistance: PointAndDistance? =
            points.map { PointAndDistance(it, position.distanceTo(it)) }
                .minByOrNull { it.distance }

        pointAndDistance?.let {
            val newPosition = pointAndDistance.point
            val newPoints = points.filter { it !== newPosition }

            val newPath = path.points.plus(newPosition)
            val newDistance = path.distance + pointAndDistance.distance

            return greedy(
                newPosition,
                newPoints,
                Path(newPath, newDistance)
            )
        } ?: kotlin.run { return path }
    }

    /** Implementations of algorithm based on movement along grouped and sorted vertical slices
     *@param initialPosition the Point from which the path will be calculated
     *@param points list of unvisited points
     *@return generated by algorithm Path
     */
    fun verticalSlicer(initialPosition: Point, points: List<Point>): Path {
        val path = mutableListOf<Point>()
        var distance = 0
        val groupedByX = points.groupBy { it.x }
        groupedByX.toSortedMap().forEach {
            val sorted = it.value.sortedBy { point -> point.y }
            val from = if (path.isEmpty()) initialPosition else path.last()
            val subPath = goToNextSlice(from, sorted)
            distance += subPath.distance
            path.addAll(subPath.points)
        }
        return Path(path, distance)
    }

    /** Process order and calculates distance for Points from [slice]
     *  @param lastPoint the Point from which the path is calculated
     *  @param slice list of points for which the path is calculated
     *  @return the Path generated for current [slice]
     */
    internal fun goToNextSlice(lastPoint: Point, slice: List<Point>): Path {
        var distance = 0
        var directionalSlice = slice
        if (slice.size > 1) {
            if (lastPoint.distanceTo(slice.first()) > lastPoint.distanceTo(slice.last())) {
                directionalSlice = slice.reversed()
            }
            distance = directionalSlice.first().distanceTo(directionalSlice.last())
        }
        distance += lastPoint.distanceTo(directionalSlice.first())
        return Path(directionalSlice, distance)
    }

    companion object {
        val INITIAL_POINT = Point(0, 0)
    }

}

/**
 * Helper class for domain area instead of Pair
 */
data class Point(val x: Int, val y: Int) {
    /** Calculate distance between two Points
     * @param target the Point to which the distance is calculated
     * @return distance to [target] point
     */
    fun distanceTo(target: Point) = abs(this.x - target.x) + abs(this.y - target.y)
}

/**
 *  Helper class for distance comparison
 */
data class PointAndDistance(val point: Point, val distance: Int)

/**
 *  Helper class contains result [points] and [distance]
 */
data class Path(val points: List<Point>, val distance: Int)