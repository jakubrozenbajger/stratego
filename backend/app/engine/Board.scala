package engine

import play.api.libs.json.Json

import scala.language.postfixOps

case class BoardPosition(row: Int, column: Int) {
}

object BoardPosition {
  implicit val boardWrites = Json.format[Board]
}

case class Board(state: Array[Array[Int]]) {


  private def size = state.length

  def get(row: Int)(column: Int): Int = {
    this.state(row)(column)
  }

  def setCell(bp: BoardPosition, value: Int): Unit = {
    setCell(bp.row, bp.column, value)
  }

  def setCell(row: Int, column: Int, value: Int): Unit = {
    this.state(row)(column) = value
  }

  def getCellByPosition(position: BoardPosition): Int = {
    this.state(position._1)(position._2)
  }

  def setCellByPosition(position: BoardPosition, value: Int): Unit = {
    this.state(position.row)(position.column) = value
  }

  def isFree(row: Int)(column: Int): Boolean = {
    get(row)(column) == 0
  }

  def isFreeByPosition(position: BoardPosition): Boolean = {
    this.getCellByPosition(position) == -1
  }

  def isFilled: Boolean = {
    !this.state.exists(r => r.contains(-1))
  }

  private val equalZero: Int => Boolean = 0 ==

  private def hasOneEmpty(arr: Array[Int]): Boolean = arr.count(equalZero) == 1

  private def getRow(i: Int): Array[Int] = state(i)

  private def getColumn(i: Int): Array[Int] = state.map(a => a(i))


  def givingPoints(boardPosition: BoardPosition): Option[Int] = {
    givingPoints(boardPosition.row, boardPosition.column)
  }

  def givingPoints(row: Int, column: Int): Option[Int] = {
    if (!isFree(row)(column))
      return None

    var points = 0

    if (hasOneEmpty(getRow(row))) {
      points += this.size
    }

    if (hasOneEmpty(getColumn(column))) {
      points += this.size
    }

    var diff1 = 1
    var diff2 = 1
    var freeFound = false
    while (!freeFound && row + diff1 < this.size && column - diff1 >= 0) {
      if (this.state(row + diff1)(column - diff1) == -1) {
        freeFound = true
      }
      diff1 += 1
    }
    while (!freeFound && row - diff2 >= 0 && column + diff2 < this.size) {
      if (this.state(row - diff2)(column + diff2) == -1) {
        freeFound = true
      }
      diff2 += 1
    }
    if (!freeFound) {
      val diagonal = diff1 + diff2 - 1
      points = points + (if (diagonal >= 2) diagonal else 0)
    }
    // decreasing diagonal
    diff1 = 1
    diff2 = 1
    freeFound = false
    while (!freeFound && row - diff1 >= 0 && column - diff1 >= 0) {
      if (this.state(row - diff1)(column - diff1) == -1) {
        freeFound = true
      }
      diff1 += 1
    }
    while (!freeFound && row + diff2 < this.size && column + diff2 < this.size) {
      if (this.state(row + diff2)(column + diff2) == -1) {
        freeFound = true
      }
      diff2 += 1
    }
    if (!freeFound) {
      val diagonal = diff1 + diff2 - 1
      points = points + (if (diagonal >= 2) diagonal else 0)
    }
    Option(points)
  }
}

object Board {
  implicit val boardWrites = Json.format[Board]
}

