package com.github.optimizationwithmetaheuristics.utils.ga

import com.github.optimizationwithmetaheuristics.utils.config.{Configuration, Settings}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.math.pow

trait ChromosomeOperations {

  def getAllele(position: Int): Int
  def getPrecision: Float
  def getXFenotype: Float
  def getYFenotype: Float
  def getGenotype: String
  def z(x: Double, y: Double): Double
  def getObjectiveValue: Double

}

class Chromosome(value: String) extends ChromosomeOperations {

  implicit val config: Configuration = new Settings(ConfigFactory.load())
  implicit val logger = LoggerFactory.getLogger(getClass.getName)

  override def getAllele(position: Int): Int =
    value.charAt(position).asDigit

  override def getGenotype: String =
    value

  override def getPrecision: Float =
    (config.upperBound - config.lowerBound) / (scala.math.pow(2, value.size/2) - 1).toFloat

  override def getXFenotype: Float = {
    val x = value.substring(value.length/2, value.length)

    var xFenotype = 0
    for(i <- 0 to x.length-1) {
      xFenotype += x.charAt(i).asDigit * scala.math.pow(2, x.size - i - 1).toInt
    }

    xFenotype * getPrecision + config.lowerBound
  }

  override def getYFenotype: Float = {
    val y = value.substring(0, value.length/2)

    var yFenotype = 0
    for(i <- 0 to y.length-1) {
      yFenotype += y.charAt(i).asDigit * scala.math.pow(2, y.size - i - 1).toInt
    }

    yFenotype * getPrecision + config.lowerBound
  }

  // Function to optimize (minimize)
  def z(x: Double, y: Double): Double =
    pow(pow(x, 2) + y - 11, 2) + pow(x + pow(y, 2) - 7, 2)

  def getObjectiveValue: Double =
    z(getXFenotype, getYFenotype)

}
