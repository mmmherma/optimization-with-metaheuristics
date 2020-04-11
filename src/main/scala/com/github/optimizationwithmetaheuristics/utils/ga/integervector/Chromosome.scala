package com.github.optimizationwithmetaheuristics.utils.ga.integervector

import com.github.optimizationwithmetaheuristics.utils.config.{Configuration, Settings}
import com.github.optimizationwithmetaheuristics.utils.matrix.Matrix
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

trait ChromosomeOperations {

  def getAllele(position: Int): Int
  def getGenotype: Array[Int]
  def objectiveValue(matrix: Matrix): Int
  def getObjectiveValue: Int
  def getDistanceMatrix: Matrix
  def getFlowMatrix: Matrix

}

class Chromosome(value: Array[Int]) extends ChromosomeOperations {

  implicit val config: Configuration = new Settings(ConfigFactory.load())
  implicit val logger = LoggerFactory.getLogger(getClass.getName)

  override def getAllele(position: Int): Int =
    value(position)

  override def getGenotype: Array[Int] =
    value

  def objectiveValue(matrix: Matrix): Int = {
    var sum: Int = 0
    for (i <- 0 to 7) {
      sum += matrix.getRow(i).sum
    }

    sum
  }

  override def toString: String = {
    var stringBuilder = new StringBuilder

    value.foreach({
      item => stringBuilder.append(item.toString + " ")
    })

    stringBuilder.toString
  }

  def getObjectiveValue(): Int = {
    // Create reindexed matrix
    val currentReindexedDistanceMatrix = getDistanceMatrix
    currentReindexedDistanceMatrix.reindex(getGenotype)
    // Get cost matrix
    currentReindexedDistanceMatrix.multiplication(getFlowMatrix)
    // Get initial solution objective value
    objectiveValue(currentReindexedDistanceMatrix)
  }


  def getDistanceMatrix: Matrix = {
    // Create distance matrix between departments
    val distanceMatrix = new Matrix(8)
    distanceMatrix.setRow(Array(0, 1, 2, 3, 1, 2, 3, 4), 0)
    distanceMatrix.setRow(Array(1, 0, 1, 2, 2, 1, 2, 3), 1)
    distanceMatrix.setRow(Array(2, 1, 0, 1, 3, 2, 1, 2), 2)
    distanceMatrix.setRow(Array(3, 2, 1, 0, 4, 3, 2, 1), 3)
    distanceMatrix.setRow(Array(1, 2, 3, 4, 0, 1, 2, 3), 4)
    distanceMatrix.setRow(Array(2, 1, 2, 3, 1, 0, 1, 2), 5)
    distanceMatrix.setRow(Array(3, 2, 1, 2, 2, 1, 0, 1), 6)
    distanceMatrix.setRow(Array(4, 3, 2, 1, 3, 2, 1, 0), 7)

    distanceMatrix
  }

  def getFlowMatrix: Matrix = {
    // Create flow matrix between departments
    val flowMatrix = new Matrix(8)
    flowMatrix.setRow(Array(0,5,2,4,1,0,0,6), 0)
    flowMatrix.setRow(Array(5,0,3,0,2,2,2,0), 1)
    flowMatrix.setRow(Array(2,3,0,0,0,0,0,5), 2)
    flowMatrix.setRow(Array(4,0,0,0,5,2,2,10), 3)
    flowMatrix.setRow(Array(1,2,0,5,0,10,0,0), 4)
    flowMatrix.setRow(Array(0,2,0,2,10,0,5,1), 5)
    flowMatrix.setRow(Array(0,2,0,2,0,5,0,10), 6)
    flowMatrix.setRow(Array(6,0,5,10,0,1,10,0), 7)

    flowMatrix
  }

}
