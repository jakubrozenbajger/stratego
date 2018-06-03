package engine

import play.api.libs.json.Json
import utils.implicits._

import scala.collection.mutable

case class Board(state: Array[Array[Int]]) {

  private val EMPTY = 0

  def size: Int = state.length

  def get(row: Int)(column: Int): Int = {
    this.state(row)(column)
  }

  def setCell(row: Int, column: Int, value: Int): Unit = this.state(row)(column) = value

  def getCellByPosition(position: BoardPosition): Int = {
    this.state(position.row)(position.column)
  }

  def setCellByPosition(position: BoardPosition, value: Int): Unit = {
    this.state(position.row)(position.column) = value
  }

  def isFree(row: Int)(column: Int): Boolean = get(row)(column) == 0

  def isFree(bp: BoardPosition): Boolean = get(bp.row)(bp.column) == 0

  def isFilled: Boolean = !this.state.exists(_.containsZero)

  def canDoMove: Boolean = !isFilled

  def place(bp: BoardPosition, value: Int) = setCell(bp.row, bp.column, value)

  def clear(bp: BoardPosition) = setCell(bp.row, bp.column, EMPTY)

  private def availableMoves: mutable.LinkedHashSet[BoardPosition] = {
    for (i <- 0 until size * size if get(i / size)(i % size) == EMPTY)
      yield BoardPosition(i / size, i % size)
  }.to[mutable.LinkedHashSet]

  def remaining: List[BoardPosition] = availableMoves.toList

}

object Board {
  def apply(size: Int): Board = new Board(emptyArray(size))

  private def emptyArray(size: Int) = {
    Array.fill[Array[Int]](size)(Array.fill[Int](size)(0))
  }

  implicit val boardWrites = Json.format[Board]
}


case class BoardPosition(row: Int, column: Int)

object BoardPosition {
  implicit val boardWrites = Json.format[BoardPosition]
}

