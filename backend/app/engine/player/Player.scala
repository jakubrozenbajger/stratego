package engine.player

import engine.{BoardPosition, Calculator, DefaultCalculator}

trait Player {

  type ID = Int

  val id: ID

  def move: BoardPosition

  def calculator: Calculator = DefaultCalculator

}
