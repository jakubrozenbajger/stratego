package comunication

import akka.actor.{Actor, ActorRef, PoisonPill}
import comunication.Messages._
import engine.player.{Human, Player, PlayerFactory}
import engine.{Board, DefaultCalculator}
import play.api.Logger

class WsActor(output: ActorRef) extends Actor {

  private val aiId = -1
  private val humanId = 1

  val logger: Logger = Logger(getClass)

  override def receive: Receive = {
    case _: Close =>
      logger.info("Closing connection WS connection")
      self ! PoisonPill

    case nm: NextMove =>
      val board = nm.board

      val human = Human(humanId, nm.position)
      doMove(human, board)

      if (board.canDoMove) {
        val ai = PlayerFactory.create(aiId, nm.aiSettings)(board)
        doMove(ai, board)
      }

      output ! NewMove(board)

    case x: InEvent =>
      logger.info(s"Received msg ${x.toString}")
      output ! Status("OK")

    case _ => println("Received unhandled msg")
  }

  def doMove(p: Player, board: Board) = {
    val pos = p.move
    val points = DefaultCalculator.calculatePoints(board, pos)
    board.place(pos, p.id)
    output ! Points(points, p.id)
  }

}
