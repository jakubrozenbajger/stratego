package engine.solver

import engine.player.AiPlayer
import engine.{Board, BoardPosition}

trait AiSolver {
  def solve(board: Board, player: AiPlayer): BoardPosition

  def name: String = getClass.getSimpleName.dropRight(1)
}
