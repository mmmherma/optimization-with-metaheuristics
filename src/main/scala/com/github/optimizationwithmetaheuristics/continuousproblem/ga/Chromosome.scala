package com.github.optimizationwithmetaheuristics.continuousproblem.ga

trait Operations {

  def getAllele(position: Int): Int
  def getFenotype(): Float
  def getPrecision(): Float
  def getXFenotype(): Float
  def getYFenotype(): Float
  def getGenotype(): String

}

class Chromosome(value: String, upperBound: Int, lowerBound: Int) extends Operations {

  override def getAllele(position: Int): Int =
    value.charAt(position).asDigit

  override def getGenotype(): String =
    value

  override def getFenotype(): Float = {
    var fenotype = 0
    for (i <- 0 to value.size-1) {
      fenotype += value.charAt(i).asDigit * scala.math.pow(2, value.size - i - 1).toInt
    }

    fenotype * getPrecision() + lowerBound
  }

  override def getPrecision(): Float =
    (upperBound - lowerBound) / (scala.math.pow(2, value.size/2) - 1).toFloat

  override def getXFenotype(): Float = {
    val x = value.substring(value.length/2, value.length)

    var xFenotype = 0
    for(i <- 0 to x.length-1) {
      xFenotype += x.charAt(i).asDigit * scala.math.pow(2, x.size - i - 1).toInt
    }

    xFenotype * getPrecision() + lowerBound
  }

  override def getYFenotype(): Float = {
    val y = value.substring(0, value.length/2)

    var yFenotype = 0
    for(i <- 0 to y.length-1) {
      yFenotype += y.charAt(i).asDigit * scala.math.pow(2, y.size - i - 1).toInt
    }

    yFenotype * getPrecision() + lowerBound
  }

}
