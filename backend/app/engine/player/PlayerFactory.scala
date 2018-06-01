package engine.player

import cats.kernel.Monoid
import engine.{AiSettings, Board}

object PlayerFactory {

  def create(aiId: Int, ai: AiSettings): Board => Player = {
    b => new AiPlayer(aiId, b, ai.depth, ai.aiType, calcStrategy = Monoid[CalcStrategy].combine(SimpleCalcStrategy, ai.positionStrategy))
  }
}