package engine.player

import engine.{Board, BoardPosition}

import scala.collection.mutable.ArrayBuffer

case class ExecutionTimeWatcher(aiPlayer: Player) extends Player {

  val times: ArrayBuffer[Long] = ArrayBuffer[Long]()

  def timeSum: Long = times.sum

  override val id: ID = aiPlayer.id

  override def move(board: Board): BoardPosition = {
    val start = System.currentTimeMillis()
    val result = aiPlayer.move(board)
    val stop = System.currentTimeMillis()
    times.append(stop - start)
    result
  }

  override def toString: String = s"${aiPlayer.toString} (with TW)"
}
