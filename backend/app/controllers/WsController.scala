package controllers

import comunication.{InEvent, OutEvent, WsActor}
import akka.actor.{ActorSystem, Props}
import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import play.api.libs.streams.ActorFlow
import play.api.mvc.{AbstractController, ControllerComponents, WebSocket}

@Singleton
class WsController @Inject()(cc: ControllerComponents)
                            (implicit actorSystem: ActorSystem,
                             mat: Materializer
                            ) extends AbstractController(cc) {

  import comunication.Messages._

  def socket: WebSocket = WebSocket.accept[InEvent, OutEvent] {
    _ => ActorFlow.actorRef(output => Props(new WsActor(output)))
  }

}
