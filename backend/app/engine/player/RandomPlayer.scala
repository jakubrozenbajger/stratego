package engine.player

import engine.{Board, BoardPosition}

case class RandomPlayer(override val id: Int, board: Board) extends Player {

  override def move: BoardPosition = {
    val x = board.state.indexWhere(_.contains(0))
    val y = board.state(x).indexWhere(0.==)
    BoardPosition(x, y)
  }
}
