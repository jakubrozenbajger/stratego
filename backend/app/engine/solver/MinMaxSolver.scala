package engine.solver

import engine.player.AiPlayer
import engine.{Board, BoardPosition}


object MinMaxSolver extends AiSolver {

  def solve(board: Board, player: AiPlayer): BoardPosition = {

    var bestBp: Option[BoardPosition] = None

    val depth = player.depth
    val remaining = player.movesChooseStrategy.getMoves(board)
    val pointsCalculator = player.calcStrategy.calc

    def inRoot(d: Int) = d == depth

    def max(value: Int, movesRemaining: Int, depth: Int): Int = {
      var result = Int.MinValue
      for (bp <- remaining if board.isFree(bp)) {
        val currentPoints = pointsCalculator(board, bp).getOrElse(0)
        board.place(bp, player.id)

        val accumulatedPoints =
          if (movesRemaining > 0 && depth > 1) {
            min(value + currentPoints, movesRemaining - 1, depth - 1)
          } else {
            value + currentPoints
          }

        if (accumulatedPoints > result) {
          result = accumulatedPoints
          if (inRoot(depth))
            bestBp = Some(bp)
        }

        board.clear(bp)
      }
      result
    }

    def min(value: Int, movRemaining: Int, depth: Int): Int = {
      var result = Int.MaxValue
      for (bp <- remaining if board.isFree(bp)) {
        val currentPts = -pointsCalculator(board, bp).getOrElse(0)
        board.place(bp, -player.id)

        val accumulatedPoints =
          if (movRemaining > 0 && depth > 1) {
            max(value + currentPts, movRemaining - 1, depth - 1)
          } else {
            value + currentPts
          }

        if (accumulatedPoints < result)
          result = accumulatedPoints

        board.clear(bp)
      }
      result
    }

    max(0, remaining.length, depth)
    bestBp.getOrElse(remaining.head)
  }
}