package controllers

import actors.WsActor
import akka.actor.{ActorSystem, Props}
import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import play.api.libs.streams.ActorFlow
import play.api.mvc.{AbstractController, ControllerComponents, WebSocket}

import scala.concurrent.ExecutionContext

@Singleton
class WsController @Inject()(cc: ControllerComponents)
                            (implicit actorSystem: ActorSystem,
                             exec: ExecutionContext,
                             mat: Materializer
                            ) extends AbstractController(cc) {

  def socket: WebSocket = WebSocket.accept[String, String] {
    _ => ActorFlow.actorRef(output => Props(new WsActor(output)))
  }

}
