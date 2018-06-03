package engine

import cats.kernel.Monoid
import engine.player._
import engine.solver.AlphaBetaSolver

import scala.annotation.tailrec
import scala.collection.mutable

object Runner extends App {

  def simulate(board: Board, p1: Player, p2: Player) = {
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
    pointsHolder
  }

  val ETW = ExecutionTimeWatcher

  def simulateWithTimeLogging(size: Int, p1: Player, p2: Player) = {
    val pt1 = ETW(p1)
    val pt2 = ETW(p2)
    simulate(Board(size), pt1, pt2)
    simulate(Board(size), pt2, pt1)
    (pt1 -> pt1.timeSum, pt2 -> pt2.timeSum)
  }

  def simulateWithPointsCounting(size: Int, p1: Player, p2: Player) = {
    val fstRes = simulate(Board(size), p1, p2)
    val sndRes = simulate(Board(size), p2, p1)
    val res = fstRes.map({ case (pl, po) => (pl, po + sndRes.getOrElse(pl, 0)) })
    ((p1, res.getOrElse(p1, 0)), (p2, res.getOrElse(p2, 0)))
  }

  //  val res: immutable.Seq[(Int, ((ExecutionTimeWatcher, Long), (ExecutionTimeWatcher, Long)))] = {
  //    for (boardSize <- 4 to 12)
  //      yield (boardSize, simulateWithTimeLogging(boardSize, AiPlayer(1, 3, AlphaBetaSolver, Monoid[CalcStrategy].combine(SimpleCalcStrategy, MiddleGratificationCalc)), AiPlayer(-1, 3, AlphaBetaSolver, Monoid[CalcStrategy].combine(SimpleCalcStrategy, BordersGratificationCalc))))
  //  }

  //  val res: immutable.Seq[(Int, ((Player, Long), (Player, Long)))] = {
  //    for (boardSize <- 4 to 12)
  //      yield (boardSize, simulateWithPointsCounting(boardSize, AiPlayer(1, 3, AlphaBetaSolver, Monoid[CalcStrategy].combine(SimpleCalcStrategy, MiddleGratificationCalc)), AiPlayer(-1, 3, AlphaBetaSolver, Monoid[CalcStrategy].combine(SimpleCalcStrategy, BordersGratificationCalc))))
  //  }

  case class LoggableCalcStrategy(c: CalcStrategy) extends CalcStrategy {
    override def calc: (Board, BoardPosition) => Option[Int] = Monoid[CalcStrategy].combine(SimpleCalcStrategy, c).calc

    override def toString: String = c.getClass.getSimpleName.dropRight(1)
  }


  for (calc <- Seq(MiddleGratificationCalc, BordersGratificationCalc, EmptyCalc); st <- Seq(SimpleMovesChooseStrategy, RandomMovesChooseStrategy, CenterLastMovesChooseStrategy)) {
    val res = for (boardSize <- 5 to 8)
      yield (boardSize, simulateWithPointsCounting(boardSize, AiPlayer(1, 4, AlphaBetaSolver, LoggableCalcStrategy(calc), st), AiPlayer(-1, 4, AlphaBetaSolver, LoggableCalcStrategy(calc), st)))

    (s"boardSize,${res.head._2._1._1},${res.head._2._2._1}" :: res.map(e => s"${e._1},${e._2._1._2},${e._2._2._2}").toList)
      .foreach(println)
  }


}
