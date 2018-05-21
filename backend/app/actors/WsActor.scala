package actors

import akka.actor.{Actor, ActorRef}
import play.api.Logger

class WsActor(outputActor: ActorRef) extends Actor {

  val logger: Logger = Logger(getClass)

  override def receive: Receive = {
    case x: String =>
      logger.info(s"Received msg $x")
      outputActor ! "{\"status\":\"OK\"}"
    case _ => println("Received unhandled msg")
  }

  override def preStart(): Unit = {
    super.preStart()
  }

  override def postStop(): Unit = {
    super.postStop()
  }

}
