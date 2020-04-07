package com.github.optimizationwithmetaheuristics.continuousproblem.ga

/**
 * Minimize the Himmelblau Function
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
import com.github.optimizationwithmetaheuristics.continuousproblem.ga.Operations

import com.typesafe.config.ConfigFactory
import scala.sys.addShutdownHook

object GA extends App {

  implicit val config: Configuration = new Settings(ConfigFactory.load())

  protected val M: Int = 100
  protected val N: Int = 20
  protected val l: Int = 24
  protected val pc: Double = 0.9
  protected val pm: Double = 1
  protected val k: Int = 3

  addShutdownHook {
    println("Received close signal")
  }

  // Generate population
  protected val population = new Population(N, l)
  population.generatePopulation()

  // Perform crossover and mutation
  var operator: Operators = new Operators
  val child = operator.crossover(population.findParents()._1, population.findParents()._1, pc)
  val mutedChild = operator.mutation(child, pm)

  println("Child crossover")
  child.foreach(println(_))
  println("Child mutation")
  mutedChild.foreach(println(_))

}
