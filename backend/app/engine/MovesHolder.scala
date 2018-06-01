package engine

import scala.collection.mutable
import scala.util.Random

class MovesHolder(size: Int) {

  private val availableMoves: mutable.LinkedHashSet[BoardPosition] = {
    for (i <- 0 to size * size)
      yield BoardPosition(i / size, i % size)
  }.to[mutable.LinkedHashSet]

  def remove(bp: BoardPosition): Boolean = availableMoves.remove(bp)

  def remaining: List[BoardPosition] = availableMoves.toList

  def shuffledRemaining: mutable.MutableList[BoardPosition] = Random.shuffle(mutable.MutableList() ++ remaining)

}
