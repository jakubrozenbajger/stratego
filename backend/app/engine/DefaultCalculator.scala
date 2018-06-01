package engine

trait Calculator {
  def calculatePoints(board: Board, bp: BoardPosition): Option[Int]
}

object DefaultCalculator extends Calculator {

  def calculatePoints(board: Board, bp: BoardPosition): Option[Int] = {
    if (!board.isFree(bp.row)(bp.column))
      return None

    var points = 0
    if (board.state(bp.row).count(c => c == 0) == 1) {
      points += board.size
    }
    if (board.state.count(r => r(bp.column) == 0) == 1) {
      points += board.size
    }

    var diff1 = 1
    var diff2 = 1
    var freeFound = false
    while (!freeFound && bp.row + diff1 < board.size && bp.column - diff1 >= 0) {
      if (board.get(bp.row + diff1)(bp.column - diff1) == 0) {
        freeFound = true
      }
      diff1 += 1
    }
    while (!freeFound && bp.row - diff2 >= 0 && bp.column + diff2 < board.size) {
      if (board.get(bp.row - diff2)(bp.column + diff2) == 0) {
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
    while (!freeFound && bp.row - diff1 >= 0 && bp.column - diff1 >= 0) {
      if (board.get(bp.row - diff1)(bp.column - diff1) == 0) {
        freeFound = true
      }
      diff1 += 1
    }
    while (!freeFound && bp.row + diff2 < board.size && bp.column + diff2 < board.size) {
      if (board.get(bp.row + diff2)(bp.column + diff2) == 0) {
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
