package engine.player

import engine.solver.AiSolver
import engine.{Board, BoardPosition}

class AiPlayer(
                override val id: Int,
                board: Board,
                val depth: Int,
                val aiSolver: AiSolver,
                val calcStrategy: CalcStrategy = SimpleCalcStrategy,
                val movesChooseStrategy: MovesChooseStrategy = SimpleMovesChooseStrategy
              ) extends Player {

  override def move: BoardPosition = aiSolver.solve(board, this)

}
