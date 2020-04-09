package com.github.optimizationwithmetaheuristics.combinatorialproblem.sa.QAP

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

/**
 * Quadratic Assignment Problem (SA-QAP)
 *
 * Minimize flow costs between the placed departments (8 in the top row and 8 in the bottom row).
 *    flow cost = flow * distance
 *
 * Optimal solution is 214
 *
 * In this case the optimal answer is 214.
 */

object QAP extends App {

  implicit val logger = LoggerFactory.getLogger(getClass.getName)
  implicit val config = ConfigFactory.load().getConfig("problems.combinatorial.sa.qap")

  protected def objectiveValue(matrix: Matrix): Int = {
    var sum: Int = 0
    for (i <- 0 to 7) {
      sum += matrix.getRow(i).sum
    }

    sum
  }

  def neighbourProbability(potential: Double, current: Double) =
    1 / scala.math.exp(( potential - current ) / T)

  protected var T = config.getDouble("T0")

  def getDistanceMatrix: Matrix = {
    // Create distance matrix between departments
    var distanceMatrix = new Matrix(8)
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

  // STEP 1. Init problem

  // Create flow matrix between departments
  protected val flowMatrix = new Matrix(8)
  flowMatrix.setRow(Array(0,5,2,4,1,0,0,6), 0)
  flowMatrix.setRow(Array(5,0,3,0,2,2,2,0), 1)
  flowMatrix.setRow(Array(2,3,0,0,0,0,0,5), 2)
  flowMatrix.setRow(Array(4,0,0,0,5,2,2,10), 3)
  flowMatrix.setRow(Array(1,2,0,5,0,10,0,0), 4)
  flowMatrix.setRow(Array(0,2,0,2,10,0,5,1), 5)
  flowMatrix.setRow(Array(0,2,0,2,0,5,0,10), 6)
  flowMatrix.setRow(Array(6,0,5,10,0,1,10,0), 7)

  // STEP 2. Evaluate initial solution
  protected var currentSolution = Array(1, 3, 0, 4, 2, 5, 6, 7)
  // Create reindexed matrix
  protected val currentReindexedDistanceMatrix = getDistanceMatrix
  currentReindexedDistanceMatrix.reindex(currentSolution)
  // Get cost matrix
  currentReindexedDistanceMatrix.multiplication(flowMatrix)
  // Get initial solution objective value
  protected var currentObjectiveValue = objectiveValue(currentReindexedDistanceMatrix)
  logger.info("Initial objective value (before SA): " + currentObjectiveValue.toString)

  // Store potential solutions objective value
  protected var potentialSolutionObjectiveValue: Int = 0

  def execute: Int = {
    for (i <- 0 to config.getInt("M")-1) {
      for (j <- 0 to config.getInt("N")-1) {
        // Initialize potential solution with current solution
        val potentialSolution = currentSolution

        // Swap 2 departments choosing 2 random integers
        val randomDepartment1 = scala.util.Random.nextInt(8)
        var randomDepartment2 = scala.util.Random.nextInt(8)

        while(randomDepartment1 == randomDepartment2) {
          randomDepartment2 = scala.util.Random.nextInt(8)
        }

        val department1 = potentialSolution(randomDepartment1)
        potentialSolution(randomDepartment1) = potentialSolution(randomDepartment2)
        potentialSolution(randomDepartment2) = department1

        // Get potential solution cost
        val potentialDistanceMatrix: Matrix = getDistanceMatrix
        // Reindex potential solution matrix
        potentialDistanceMatrix.reindex(potentialSolution)
        // Get potential solution cost matrix
        potentialDistanceMatrix.multiplication(flowMatrix)
        // Get potential solution objective cost
        val potentialObjectiveValue = objectiveValue(potentialDistanceMatrix)

        // Decide which solution
        val solutionDecisionRandom = scala.util.Random.nextDouble()
        if (potentialObjectiveValue <= currentObjectiveValue) {
          currentSolution = potentialSolution
          potentialSolutionObjectiveValue = potentialObjectiveValue
        } else if (solutionDecisionRandom <= neighbourProbability(potentialObjectiveValue, currentObjectiveValue)) {
          currentSolution = potentialSolution
          potentialSolutionObjectiveValue = potentialObjectiveValue
        } else {
          currentSolution = currentSolution
        }
      }

      // Update temperature
      T = T * config.getDouble("alpha")
    }

    potentialSolutionObjectiveValue
  }

  protected var solutionArray = new ArrayBuffer[Int](100)
  for(i <- 0 to 100) {
    solutionArray += execute
  }
  logger.info("Minumim objective value: " + solutionArray.min)

}
