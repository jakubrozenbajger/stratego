package utils

object implicits {

  implicit class RichArray[T](arr: Array[T]) {

    implicit def containsZero: Boolean = arr.contains(0)

  }

}