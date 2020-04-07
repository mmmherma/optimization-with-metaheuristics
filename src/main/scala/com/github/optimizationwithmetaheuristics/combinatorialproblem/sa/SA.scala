package com.github.optimizationwithmetaheuristics.combinatorialproblem.sa

/**
 * Quadratic Assignment Problem (SA-QAP)
 *
 * Minimize flow costs between the placed departments (8 in the top row and 8 in the bottom row).
 *    flow cost = flow * distance
 *
 * In this case the optimal answer is 214.
 */

object SA extends App {

  // STEP 1. Init problem
  // Create distance matrix between departments
  protected val distanceMatrix = Array(
    Array(0, 1, 2, 3, 1, 2, 3, 4),
    Array(1, 0, 1, 2, 2, 1, 2, 3),
    Array(2, 1, 0, 1, 3, 2, 1, 2),
    Array(3, 2, 1, 0, 4, 3, 2, 1),
    Array(1, 2, 3, 4, 0, 1, 2, 3),
    Array(2, 1, 2, 3, 1, 0, 1, 2),
    Array(3, 2, 1, 2, 2, 1, 0, 1),
    Array(4, 3, 2, 1, 3, 2, 1, 0)
  )
  // Create flow matrix between departments
  protected val flowMatrix = Array(
    Array(0,5,2,4,1,0,0,6),
    Array(5,0,3,0,2,2,2,0),
    Array(2,3,0,0,0,0,0,5),
    Array(4,0,0,0,5,2,2,10),
    Array(1,2,0,5,0,10,0,0),
    Array(0,2,0,2,10,0,5,1),
    Array(0,2,0,2,0,5,0,10),
    Array(6,0,5,10,0,1,10,0)
  )

  // SA parameters
  protected var T0 = 1500
  protected val M = 250
  protected val N = 20
  protected val alpha = 0.9

  // Initial solution
  protected var x0 = Array(1, 3, 0, 4, 2, 5, 6, 7)

}
