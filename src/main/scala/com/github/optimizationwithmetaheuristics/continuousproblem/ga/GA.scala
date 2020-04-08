package com.github.optimizationwithmetaheuristics.continuousproblem.ga

/**
 * Minimize the Himmelblau Function
 *    (x^2 + y - 11)^2 + (x + y^2 - 7)^2
 *
 * Local (in this case also global) minimums:
 *    z(3.0, 2.0) = 0.0
 *    z(-2.805118, 3.131312) = 0.0
 *    z(-3.779310, -3.283186) = 0.0
 *    z(3.584428, -1.848126) = 0.0
 *
 * Chromosome longitude will be 8 bits: first half to 'y' and second half to 'x'
 *
 *    M  - Number of generations
 *    N  - Population size
 *    l  - Chromosome length
 *    pc - Crossover probability
 *    pm - Mutation probability
 *    k  - Number of contestants when tournament selection is perform
 */

import com.github.optimizationwithmetaheuristics.config.{Configuration, Settings}

import scala.collection.mutable.ArrayBuffer
import scala.sys.addShutdownHook
import com.typesafe.config.ConfigFactory
import org.slf4j.{LoggerFactory}

object GA extends App {

  implicit val config: Configuration = new Settings(ConfigFactory.load())
  implicit val logger = LoggerFactory.getLogger(getClass.getName)

  addShutdownHook {
    logger.info("Received close signal")
  }

  // Create operators
  protected val operators: Operators = new Operators

  // Create first population
  protected var population = new Population(config.N, config.l)
  population.generatePopulation

  // Store best individual of each population
  var bestOfEachPopulation: ArrayBuffer[Chromosome] = new ArrayBuffer[Chromosome]()

  // Generation loop
  for(i <- 0 to config.M-1) {
    logger.info("Generation " + i)

    // Family loop
    var newPopulation: ArrayBuffer[Chromosome] = new ArrayBuffer[Chromosome]()
    var familyCounter: Int = 1
    for(j <- 0 to (config.N/2)-1) {
      logger.info("\tFamily " + familyCounter)

      // Get parents
      val parents: Seq[Chromosome] = population.findParents
      val parent1 = parents(0)
      val parent2 = parents(1)

      // Crossover parents to get child
      val children: Seq[Chromosome] = operators.crossover(parent1, parent2, config.pc)
      val child1 = children(0)
      val child2 = children(1)

      // Mutate children
      val muted1 = operators.mutation(child1, config.pm)
      val muted2 = operators.mutation(child2, config.pm)

      // Append muted children for next generation
      newPopulation += muted1
      newPopulation += muted2

      familyCounter += 1
    }

    // New population based on muted children
    population.setPopulation(newPopulation)
    // Store best individual of the population
    bestOfEachPopulation += population.getBestIndividual
  }

  // Get best of the bests
  protected val bestPopulation: Population = new Population(config.N, config.l)
  bestPopulation.setPopulation(bestOfEachPopulation)
  protected val bestChromosome = new Chromosome(bestPopulation.getBestIndividual.getGenotype)
  logger.info("Best individual genotype: " + bestPopulation.getBestIndividual.getGenotype.toString)
  logger.info("Best individual objective value: " + bestPopulation.getBestIndividual.getObjectiveValue.toString)
  logger.info("Best individual decoded x: " + bestChromosome.getXFenotype.toString)
  logger.info("Best individual decoded y: " + bestChromosome.getYFenotype.toString)

}
