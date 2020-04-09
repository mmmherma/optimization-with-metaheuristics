package com.github.optimizationwithmetaheuristics.combinatorialproblem.sa.QAP

import com.github.optimizationwithmetaheuristics.continuousproblem.sa.HummelblauFunction.Matrix
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

/**
 * Quadratic Assignment Problem (SA-QAP)
 *
 * Minimize flow costs between the placed departments (8 in the top row and 8 in the bottom row).
 *    flow cost = flow * distance
 *
 * In this case the optimal answer is 214.
 */

object QAP extends App {

  implicit val logger = LoggerFactory.getLogger(getClass.getName)
  implicit val config = ConfigFactory.load().getConfig("problems.combinatorial.sa.qap")

  implicit val distance = Array.ofDim[Int](8)

  // STEP 1. Init problem
  // Create distance matrix between departments
  protected var distanceMatrix = new Matrix(8)
  distanceMatrix.setRow(Array(0, 1, 2, 3, 1, 2, 3, 4), 0)
  distanceMatrix.setRow(Array(1, 0, 1, 2, 2, 1, 2, 3), 1)
  distanceMatrix.setRow(Array(2, 1, 0, 1, 3, 2, 1, 2), 2)
  distanceMatrix.setRow(Array(3, 2, 1, 0, 4, 3, 2, 1), 3)
  distanceMatrix.setRow(Array(1, 2, 3, 4, 0, 1, 2, 3), 4)
  distanceMatrix.setRow(Array(2, 1, 2, 3, 1, 0, 1, 2), 5)
  distanceMatrix.setRow(Array(3, 2, 1, 2, 2, 1, 0, 1), 6)
  distanceMatrix.setRow(Array(4, 3, 2, 1, 3, 2, 1, 0), 7)
//  println("Distance matrix")
//  distanceMatrix.printMatrix
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
//  println(flowMatrix.printMatrix)

  // Initial solution
  protected var x0 = Array(1, 3, 0, 4, 2, 5, 6, 7)

  // Reindex distance matrix
  protected val reindexedDistanceMatrix = distanceMatrix
  reindexedDistanceMatrix.reindex(x0)
  /*// Reindex cost matrix
  protected val reindexedFlowMatrix = flowMatrix
  reindexedFlowMatrix.reindex(x0)
  reindexedFlowMatrix.printMatrix*/

  protected val test = reindexedDistanceMatrix
  test.multiplication(flowMatrix)
  test.printMatrix

}
