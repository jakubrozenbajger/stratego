package engine

import engine.player.{AiPlayer, ExecutionTimeWatcher, Player}
import engine.solver.{MinMaxSolver, RandomSolver}

import scala.annotation.tailrec
import scala.collection.mutable

object Runner extends App {


  def simulate(board: Board, p1: Player, p2: Player): Unit = {

    val pointsHolder: mutable.Map[Player, Int] = mutable.HashMap[Player, Int]() ++ Seq(p1, p2).map(p => (p, 0)).toMap

    @tailrec
    def run(curr: Player, next: Player): Unit = {
      if (board.canDoMove) {
        val pos = curr.move(board)
        val points = DefaultCalculator.calculatePoints(board, pos).getOrElse(0)
        board.place(pos, curr.id)
        pointsHolder.update(curr, pointsHolder.getOrElse(curr, 0) + points)
        run(next, curr)
      }
    }

    run(p1, p2)
    println(pointsHolder)
  }

  val ETW = ExecutionTimeWatcher

  simulate(Board(5), AiPlayer(1, 1, RandomSolver), AiPlayer(-1, 3, MinMaxSolver))
  simulate(Board(5), AiPlayer(-1, 1, MinMaxSolver), AiPlayer(1, 3, RandomSolver))

}
