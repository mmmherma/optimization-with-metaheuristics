package com.github.optimizationwithmetaheuristics.utils.ga.binary

import com.github.optimizationwithmetaheuristics.utils.config.{Configuration, Settings}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

class Population(size: Int, individualSize: Int) {

  implicit val config: Configuration = new Settings(ConfigFactory.load())
  implicit val logger = LoggerFactory.getLogger(getClass.getName)

  protected var population: ArrayBuffer[Chromosome] = new ArrayBuffer[Chromosome]()

  def printIndividualsSize: Unit ={
    for(individual <- population) {
      println(individual.getGenotype.size)
    }
  }

  def getPopulation: ArrayBuffer[Chromosome] =
    population

  def setPopulation(newPopulation: ArrayBuffer[Chromosome]): Unit = {
    population.clear()
    newPopulation.foreach(
      population += _
    )
  }

  def getSize: Int =
    population.size

  def printPopulation: Unit =
    for (individual <- population) {
      println(individual.getGenotype)
    }

  def getIndividual(position: Int): Chromosome =
    population(position)

  def getBestIndividual: Chromosome = {
    var objectiveValues = new ArrayBuffer[Double]()
    for (i <- 0 to population.size-1) {
      objectiveValues += getIndividual(i).getObjectiveValue
    }

    getIndividual(objectiveValues.indexOf(objectiveValues.min))
  }

  def setIndividual(individual: Chromosome): Unit =
    population.append(individual)

  def generatePopulation: Unit = {
    population.clear

    for(i <- 0 to size-1) {
      population += generateIndividual
    }
  }

  def generateIndividual: Chromosome = {
    var individual = ""
    for(i <- 0 to individualSize-1) {
      if(scala.util.Random.nextDouble() > 0.5) {
        individual += "0"
      } else {
        individual += "1"
      }
    }

    new Chromosome(individual)
  }

  // POPULATION PARENT SELECTION
  /**
   * Randomly choose individuals for tournament
   * @param number  Number of individuals
   * @return        Sequence with de number of the individuals
   */
  def chooseTournamentContestants(number: Int): Seq[Int] =
    scala.util.Random.shuffle(
      Seq.fill(20)(scala.util.Random.nextInt(20)).distinct
    ).take(number)

  /**
   * Choose parents with best objective value
   * @return  Parents
   */
  def findParents: Seq[Chromosome] = {
    // Tournament Selection

    // Choose 3 random individuals from population (generating 3 unique random numbers between 0 and population.size)
    var contestants: Seq[Int] = chooseTournamentContestants(3)
    while (contestants.size != 3) {
      contestants = chooseTournamentContestants(3)
    }

    // Evaluate parents objective function
    var parentAndFitness: Seq[(Chromosome, Double)] = Seq.tabulate(3)(n => {
      (getIndividual(contestants(n)), getIndividual(contestants(n)).getObjectiveValue)
    })

    // Get 2 best parents
    parentAndFitness = parentAndFitness.sortBy(_._2).take(2)

    // Return best fitness parent tuple
    parentAndFitness.map(_._1)
  }

}
