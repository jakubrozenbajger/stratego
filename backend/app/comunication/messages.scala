package comunication

import engine.BoardPosition._
import engine.{AiSettings, Board, BoardPosition}
import play.api.libs.json._
import play.api.mvc.WebSocket.MessageFlowTransformer

sealed trait InEvent

sealed trait OutEvent {
  self =>
  val name: String = self.getClass.getSimpleName
}

trait JSFormatter {
  def identifier = getClass.getSimpleName.dropRight(1)
}

object Messages {

  implicit object Writes extends Writes[OutEvent] {
    override def writes(o: OutEvent): JsValue = {
      o match {
        case i: IndividualInfo => IndividualInfo.format.writes(i)
        case s: Statistics => Statistics.format.writes(s)
        case st: Status => Status.format.writes(st)
        case nm: NewMove => NewMove.format.writes(nm)
        case p: Points => Points.format.writes(p)
        case _ => sys.error(s"Type not found: $o")
      }
    }
  }

  implicit object Reads extends Reads[InEvent] {
    override def reads(json: JsValue): JsResult[InEvent] = {
      val msgType = (json \ "msg").as[String]
      msgType match {
        case "close" => JsSuccess(Close())
        case "start" => Start.format.reads(json)
        case "nextMove" => NextMove.format.reads(json)
      }
    }
  }

  case class Close() extends InEvent

  case class Start(cycles: Int) extends InEvent

  object Start {
    implicit val format = Json.format[Start]
  }

  case class NextMove(position: BoardPosition, board: Board, aiSettings: AiSettings) extends InEvent

  case class IndividualInfo(generation: Int, image: String, msg: String = IndividualInfo.identifier, population: Int, info: String)
    extends OutEvent

  object IndividualInfo extends JSFormatter {
    implicit val format = Json.format[IndividualInfo]
  }

  case class Statistics(message: String, msg: String = Statistics.identifier) extends OutEvent

  case class Status(status: String) extends OutEvent

  case class NewMove(board: Board) extends OutEvent

  case class Points(count: Int, id: Int) extends OutEvent

  import play.api.libs.json._

  object NextMove {
    implicit val format = Json.format[NextMove]
  }

  object Points {
    implicit val format = Json.format[Points]

    def apply(count: Int, id: Int): Points = new Points(count, id)

    def apply(count: Option[Int], id: Int): Points = new Points(count.getOrElse(0), id)

  }

  object NewMove {
    implicit val format = Json.format[NewMove]
  }

  object Status {
    implicit val format = Json.format[Status]
  }

  object Statistics extends JSFormatter {
    implicit val format = Json.format[Statistics]
  }

  implicit val messageFlowTransformer =
    MessageFlowTransformer.jsonMessageFlowTransformer[InEvent, OutEvent]

}