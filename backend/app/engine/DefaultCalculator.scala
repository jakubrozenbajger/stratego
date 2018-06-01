package engine

trait Calculator {
  def calculatePoints(board: Board, bp: BoardPosition): Option[Int]
}

object DefaultCalculator extends Calculator {

  def calculatePoints(board: Board, bp: BoardPosition): Option[Int] = calculatePoints(board, bp.row, bp.column)

  def calculatePoints(board: Board, row: Int, column: Int): Option[Int] = {
    if (!board.isFree(row)(column))
      return None

    var points = 0
    if (board.state(row).count(c => c == 0) == 1) {
      points += board.size
    }
    if (board.state.count(r => r(column) == 0) == 1) {
      points += board.size
    }

    var diff1 = 1
    var diff2 = 1
    var freeFound = false
    while (!freeFound && row + diff1 < board.size && column - diff1 >= 0) {
      if (board.get(row + diff1)(column - diff1) == 0) {
        freeFound = true
      }
      diff1 += 1
    }
    while (!freeFound && row - diff2 >= 0 && column + diff2 < board.size) {
      if (board.get(row - diff2)(column + diff2) == 0) {
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
      if (board.get(row - diff1)(column - diff1) == 0) {
        freeFound = true
      }
      diff1 += 1
    }
    while (!freeFound && row + diff2 < board.size && column + diff2 < board.size) {
      if (board.get(row + diff2)(column + diff2) == 0) {
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
