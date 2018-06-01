package engine.player

import cats.kernel.Monoid
import engine.{Board, BoardPosition, DefaultCalculator}

sealed trait CalcStrategy {
  def calc: (Board, BoardPosition) => Option[Int]
}

object CalcStrategy {
  implicit val monoid = new Monoid[CalcStrategy] {
    override def combine(x: CalcStrategy, y: CalcStrategy): CalcStrategy = {
      new CalcStrategy {
        override def calc: (Board, BoardPosition) => Option[Int] =
          (b, bp) => x.calc(b, bp).flatMap(i => y.calc(b, bp).map(_ + i))
      }
    }

    override def empty: CalcStrategy = EmptyCalc
  }
}

object SimpleCalcStrategy extends CalcStrategy {
  def calc: (Board, BoardPosition) => Option[Int] = DefaultCalculator.calculatePoints
}

object BordersGratificationCalc extends CalcStrategy {
  def calc: (Board, BoardPosition) => Option[Int] = (b, bp) => {
    Some(math.abs(b.size / 2 - bp.row) + math.abs(b.size / 2 - bp.column))
  }
}

object MiddleGratificationCalc extends CalcStrategy {
  def calc: (Board, BoardPosition) => Option[Int] = (b, bp) => {
    Some(b.size - (math.abs(b.size / 2 - bp.row) + math.abs(b.size / 2 - bp.column)))
  }
}

object EmptyCalc extends CalcStrategy {
  def calc: (Board, BoardPosition) => Option[Int] = (_, _) => None
}

//
//object NotLastButOneGratificationCalc extends CalcStrategy {
//  def calc: (Board, BoardPosition) => Option[Int] = NotLastButOneCalculator.calculatePoints
//}


sealed trait MovesChooseStrategy {
  def getMoves: (Board) => Seq[BoardPosition]
}

object SimpleMovesChooseStrategy extends MovesChooseStrategy {
  override def getMoves: Board => Seq[BoardPosition] = _.remaining
}

object RandomMovesChooseStrategy extends MovesChooseStrategy {
  override def getMoves: Board => Seq[BoardPosition] = _.shuffledRemaining
}

