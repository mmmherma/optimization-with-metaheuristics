package com.github.optimizationwithmetaheuristics.continuousproblem.sa.HummelblauFunction

import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

class Matrix(dimension: Int) {

  implicit val logger = LoggerFactory.getLogger(getClass.getName)

  protected var matrix = Array.ofDim[Int](dimension, dimension)

  def setRow(row: Array[Int], index: Int): Unit = {
    for (i <- 0 to dimension-1) {
      matrix(index)(i) = row(i)
    }
  }

  def getElement(row: Int, col: Int): Int =
    matrix(row)(col)

  def reindex(newOrder: Array[Int]): Unit = {
    var flipColsMatrix = ArrayBuffer.fill(dimension, dimension)(0)
    var flipRowsMatrix = ArrayBuffer.fill(dimension, dimension)(0)

    // Flip columns
    for (j <- 0 to (dimension-1)) {   // Row for
      for (k <- 0 to (dimension-1)) { // Column for
        flipColsMatrix(k)(j) = matrix(k)(newOrder(j))
      }
    }

    // Flip rows
    for (j <- 0 to (dimension-1)) {   // Row for
      for (k <- 0 to (dimension-1)) { // Column for
        flipRowsMatrix(j)(k) = flipColsMatrix(newOrder(j))(k)
      }
    }

    for (i <- 0 to (dimension-1)) {
      setRow(flipRowsMatrix(i).toArray, i)
    }
  }

  def multiplication(matrix2: Matrix): Unit = {
    for (j <- 0 to (dimension-1)) {   // Row for
      for (k <- 0 to (dimension-1)) { // Column for
        matrix(j)(k) = matrix(j)(k) * matrix2.getElement(j, k)
      }
    }
  }

  def printMatrix: Unit =
    for (i <- 0 to dimension-1) {
      for (j <- 0 to dimension-1) {
        print(matrix(i)(j) + " ")
      }
      println()
    }
}
