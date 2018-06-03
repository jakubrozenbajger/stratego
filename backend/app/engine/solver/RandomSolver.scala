package engine.solver

import engine.player.AiPlayer
import engine.{Board, BoardPosition}


object RandomSolver extends AiSolver {

  override def solve(board: Board, player: AiPlayer): BoardPosition = {
    val x = board.state.indexWhere(_.contains(0))
    val y = board.state(x).indexWhere(0.==)
    BoardPosition(x, y)
  }
}