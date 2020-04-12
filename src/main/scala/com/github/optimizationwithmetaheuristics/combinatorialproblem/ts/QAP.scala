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
  protected var tabuList: TabuList = new TabuList(10)

  /*// Compute objective value of each neighbor
  for (item <- orderedSolutions) {
    logger.info("ITEM")
    logger.info(item.toString)
    logger.info(item.getObjectiveValue().toString)
  }
  logger.info(orderedSolutions.size.toString)*/



  // Set current solution
  var currentSolution = new Chromosome(Array(3, 0, 2, 1, 6, 4, 5, 7))
  var potentialSolutions = new ArrayBuffer[Chromosome]()

  // Iterations loop
  for(i <- 0 to config.getInt("M")) {

    // Create new neighborhood based on current solution
    val neighborhood: Neighborhood = new Neighborhood
    val currentNeighborhood: Array[Chromosome] = neighborhood.getNeighborhood(currentSolution)

    // Sort solutions from best to worst
    val orderedSolutions: Array[Chromosome] = currentNeighborhood.sortWith(
      (item1, item2) => item1.getObjectiveValue() < item2.getObjectiveValue()
    )
    currentSolution = tabuList.getCurrentSolution(orderedSolutions)

    // Store potential best solution of the neighborhood
    potentialSolutions += currentSolution

    // Aspiration criteria
    // Kick-start search when stuck in a local minimum (diversification)
    // Looking in new neighborhoods
    if (i%10 == 0) {
      // Get reindexed solution by 3 points
      val random1 = scala.util.Random.nextInt(8)
      val random2 = scala.util.Random.nextInt(8)
      val random3 = scala.util.Random.nextInt(8)

      val reindexedSolution = currentSolution.getGenotype
      reindexedSolution(random1) = currentSolution.getGenotype(random2)
      reindexedSolution(random2) = currentSolution.getGenotype(random1)
      reindexedSolution(random1) = currentSolution.getGenotype(random3)
      reindexedSolution(random3) = currentSolution.getGenotype(random1)
      val reindexedCurrentSolution = new Chromosome(reindexedSolution)

      currentSolution = reindexedCurrentSolution
    }
  }

  // Sort best solutions and pick the best
  potentialSolutions = potentialSolutions.sortWith(
    (item1, item2) => item1.getObjectiveValue() < item2.getObjectiveValue()
  )

  for (solution <- potentialSolutions) {
    logger.info("Solution")
    logger.info(solution.toString)
    logger.info(solution.getObjectiveValue().toString)
  }

  logger.info("BEST SOLUTION")
  logger.info(potentialSolutions(0).toString)
  logger.info(potentialSolutions(0).getObjectiveValue().toString)

}
