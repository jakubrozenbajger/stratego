package engine.player

import engine.BoardPosition

case class Human(override val id: Int, boardPosition: BoardPosition) extends Player {

  override def move: BoardPosition = boardPosition

}
