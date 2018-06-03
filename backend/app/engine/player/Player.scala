package engine.player

import engine.{Board, BoardPosition, Calculator, DefaultCalculator}

trait Player {

  type ID = Int

  val id: ID

  def move(board: Board): BoardPosition

  def calculator: Calculator = DefaultCalculator

}
