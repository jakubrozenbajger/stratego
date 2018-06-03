package engine

import cats.kernel.Monoid
import engine.player.{AiPlayer, CalcStrategy, Player, SimpleCalcStrategy}

object AiFactory {

  def create(aiId: Int, ai: AiSettings): Player = {
    new AiPlayer(aiId, ai.depth, ai.aiType,
      movesChooseStrategy = ai.moveStrategy,
      calcStrategy = Monoid[CalcStrategy].combine(SimpleCalcStrategy, ai.positionStrategy)
    )
  }
}