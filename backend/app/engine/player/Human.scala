package engine.player

import engine.{Board, BoardPosition}

case class Human(override val id: Int, boardPosition: BoardPosition) extends Player {

  override def move(board: Board): BoardPosition = boardPosition

}
