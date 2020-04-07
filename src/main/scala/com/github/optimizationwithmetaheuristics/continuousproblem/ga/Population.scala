package com.github.optimizationwithmetaheuristics.continuousproblem.ga

import com.github.optimizationwithmetaheuristics.config.{Configuration, Settings}

import scala.collection.mutable.ArrayBuffer
import com.typesafe.config.ConfigFactory
import scala.math.pow

class Population(size: Int, individualSize: Int) {

  implicit val config: Configuration = new Settings(ConfigFactory.load())

  protected var population: ArrayBuffer[String] = new ArrayBuffer[String]()

  // Function to optimize (minimize)
  def z(x: Double, y: Double): Double =
    pow(pow(x, 2) + y - 11, 2) + pow(x + pow(y, 2) - 7, 2)

  def generatePopulation(): Unit = {
    population.clear()

    for(i <- 0 to size) {
      population += generateIndividual()
    }
  }

  def generateIndividual(): String = {
    var individual = ""
    for(i <- 0 to individualSize-1) {
      if(scala.util.Random.nextDouble() > 0.5) {
        individual += "0"
      } else {
        individual += "1"
      }
    }

    individual
  }

  def chooseTournamentContestants(number: Int): Seq[Int] =
    scala.util.Random.shuffle(
      Seq.fill(20)(scala.util.Random.nextInt(20)).distinct
    ).take(number)

  def findParents(): (String, Double) = {
    // Tournament Selection

    // Choose 3 random individuals from population (generating 3 unique random numbers between 0 and population.size)
    var contestants: Seq[Int] = chooseTournamentContestants(3)
    while (contestants.size != 3) {
      contestants = chooseTournamentContestants(3)
    }

    // Evaluate parents objective function
    var parentAndFitness: Seq[(String, Double)] = Seq.tabulate(3)(n => {
      val parent: Chromosome = new Chromosome(getIndividual(contestants(n)), 6, -6)
      (getIndividual(contestants(n)), z(parent.getXFenotype(), parent.getYFenotype()))
    })

    // Get 2 best parents
    parentAndFitness = parentAndFitness.sortBy(_._2).take(1)

    // Return best fitness parent tuple
    parentAndFitness match {
      case (Seq((chromosome, fitness))) => (chromosome, fitness)
    }
  }

  def printPopulation(): Unit =
    population.foreach(println(_))

  def getIndividual(position: Int): String =
    population(position)

}
