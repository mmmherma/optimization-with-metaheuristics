package com.github.optimizationwithmetaheuristics.combinatorialproblem.ga

import com.github.optimizationwithmetaheuristics.utils.ga.integervector.{Chromosome, Population, Operators}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

/**
 * Quadratic Assignment Problem (GA-QAP)
 *
 * Minimize flow costs between the placed departments (8 in the top row and 8 in the bottom row).
 *    flow cost = flow * distance
 *
 * In this case the optimal answer is 214.
 */

object QAP extends App {

  implicit val logger = LoggerFactory.getLogger(getClass.getName)
  implicit val config = ConfigFactory.load().getConfig("problems.combinatorial.ga.qap")

  // Create operators
  protected val operators: Operators = new Operators

  // Create first population
  protected var population = new Population(config.getInt("N"), config.getInt("l"))
  population.generatePopulation

  // Store best individual of each population
  var bestOfEachPopulation: ArrayBuffer[Chromosome] = new ArrayBuffer[Chromosome]()

  for (i <- 0 to config.getInt("N") -1) {
//    logger.info("Geration " + i.toString)

    var newPopulation: ArrayBuffer[Chromosome] = new ArrayBuffer[Chromosome]()
    var family: Int = 0

    for (j <- 0 to (config.getInt("N")/2)-1) {
//      logger.info("\tFamily " + family.toString)

      // Get parents
      val parents: Seq[Chromosome] = population.findParents
      val parent1 = parents(0)
      val parent2 = parents(1)

      // Crossover parents to get child
      val children: Seq[Chromosome] = operators.crossover(parent1, parent2, config.getDouble("pc"))
      val child1 = children(0)
      val child2 = children(1)

      // Mutate children
      val muted1 = operators.mutation(child1, config.getDouble("pm"))
      val muted2 = operators.mutation(child2, config.getDouble("pm"))

      // Append muted children for next generation
      newPopulation += muted1
      newPopulation += muted2

      family += 1
    }

    // New population based on muted children
    population.setPopulation(newPopulation)
    // Store best individual of the population
    bestOfEachPopulation += population.getBestIndividual
  }

  // Get best of the bests
  protected val bestPopulation: Population = new Population(config.getInt("N"), config.getInt("l"))
  bestPopulation.setPopulation(bestOfEachPopulation)
  protected val bestChromosome = new Chromosome(bestPopulation.getBestIndividual.getGenotype)
  logger.info("Best individual genotype: " + bestPopulation.getBestIndividual.toString)
  logger.info("Best individual objective value: " + bestPopulation.getBestIndividual.getObjectiveValue.toString)

  /*bestPopulation.getBestIndividual.getGenotype.foreach({
    print(" ")
    print(_)
  })*/

}
