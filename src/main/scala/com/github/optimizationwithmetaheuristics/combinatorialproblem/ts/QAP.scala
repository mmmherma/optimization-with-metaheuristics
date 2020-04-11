package com.github.optimizationwithmetaheuristics.combinatorialproblem.ts

import com.github.optimizationwithmetaheuristics.utils.ga.integervector.Chromosome
import org.slf4j.LoggerFactory
import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ArrayBuffer

/**
 * Quadratic Assignment Problem (TS-QAP)
 *    With dynamic tabu list ans aspiration criteria (avoid stuck in local minimum)
 *
 * Tabu Search parameters:
 *    M - Number of runs/iterations
 *    N - Size of neighborhood
 *    l - Length of tabu list
 *    gamma - Move operator
 *
 * Minimize flow costs between the placed departments (8 in the top row and 8 in the bottom row).
 *    flow cost = flow * distance
 *
 * In this case the optimal answer is 214.
 */

object QAP extends App {

  implicit val logger = LoggerFactory.getLogger(getClass.getName)
  implicit val config = ConfigFactory.load().getConfig("problems.combinatorial.ts.qap")

  // Create the tabu list
  protected var tabuList: ArrayBuffer[Chromosome] = new ArrayBuffer[Chromosome]()

  /*// Compute objective value of each neighbor
  for (item <- orderedSolutions) {
    logger.info("ITEM")
    logger.info(item.toString)
    logger.info(item.getObjectiveValue().toString)
  }
  logger.info(orderedSolutions.size.toString)*/


  // Iterations loop
  for(i <- 0 to config.getInt("M")) {
    // Set current solution
    val currentSolution = new Chromosome(Array(3, 0, 2, 1, 6, 4, 5, 7))

    // Create new neighborhood based on current solution
    val neighborhood: Neighborhood = new Neighborhood
    val currentNeighborhood: Array[Chromosome] = neighborhood.getNeighborhood(currentSolution)

    // Sort solutions from best to worst
    val orderedSolutions: Array[Chromosome] = currentNeighborhood.sortWith((item1, item2) => item1.getObjectiveValue() < item2.getObjectiveValue())
    // Put an element into the tabu list
  }

}
