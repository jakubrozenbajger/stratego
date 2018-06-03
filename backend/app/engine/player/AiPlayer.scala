package engine.player

import engine.solver.AiSolver
import engine.{Board, BoardPosition}

case class AiPlayer(
                     override val id: Int,
                     depth: Int,
                     aiSolver: AiSolver,
                     calcStrategy: CalcStrategy = SimpleCalcStrategy,
                     movesChooseStrategy: MovesChooseStrategy = SimpleMovesChooseStrategy
                   ) extends Player {

  override def move(board: Board): BoardPosition = aiSolver.solve(board, this)

  override def toString: String = s"AiPlayer(id=$id, depth=$depth, solver=${aiSolver.name})"
}
