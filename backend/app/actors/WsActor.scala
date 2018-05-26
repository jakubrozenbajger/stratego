package actors

import actors.Messages._
import akka.actor.{Actor, ActorRef}
import engine.{Board, BoardPosition}
import play.api.Logger

class WsActor(outputActor: ActorRef) extends Actor {

  val logger: Logger = Logger(getClass)

  override def receive: Receive = {

    case nm: NextMove =>
      val position = placeRandom(nm)
      val board = Board(nm.state)
      val points = board.givingPoints(position)
      board.setCell(position, -1)

      outputActor ! Points(points.getOrElse(0))
      outputActor ! NewMove(board)

    case x: InEvent =>
      logger.info(s"Received msg ${x.toString}")
      outputActor ! Status("OK")

    case _ => println("Received unhandled msg")
  }

  private def placeRandom(nm: NextMove) = {
    val x = nm.state.indexWhere(_.contains(0))
    val y = nm.state(x).indexWhere(0.==)
    BoardPosition(x, y)
  }

  override def preStart(): Unit = {
    super.preStart()
  }

  override def postStop(): Unit = {
    super.postStop()
  }

}
