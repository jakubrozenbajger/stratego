package engine

import engine.player._
import engine.solver.{AiSolver, AlphaBetaSolver, MinMaxSolver, RandomSolver}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, _}


case class AiSettings(
                       depth: Int,
                       aiType: AiSolver,
                       moveStrategy: MovesChooseStrategy,
                       positionStrategy: CalcStrategy
                     )

object AiSettings {


  implicit val format = new Format[AiSettings] {
    override def writes(o: AiSettings): JsValue = JsNull

    override def reads(json: JsValue): JsResult[AiSettings] = AiSettings.reads.reads(json)
  }

  implicit val reads: Reads[AiSettings] = (
    (__ \ "depth").read[Int] and
      (__ \ "aiType").read[String].map({
        case "MinMax" => MinMaxSolver
        case "AlphaBeta" => AlphaBetaSolver
        case "Random" => RandomSolver
      }) and
      (__ \ "moveStrategy").read[String].map({
        case "Random" => RandomMovesChooseStrategy
        case "Normal" => SimpleMovesChooseStrategy
      }) and
      (__ \ "positionStrategy").read[String].map({
        case "Middle" => MiddleGratificationCalc
        case "Border" => BordersGratificationCalc
        case "None" => EmptyCalc
      })
    ) (AiSettings.apply _)

}