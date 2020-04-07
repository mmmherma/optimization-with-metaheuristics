package com.github.optimizationwithmetaheuristics.continuousproblem.ga

import scala.collection.mutable.ArrayBuffer

class Operators {

  def crossover(parent1: String, parent2: String, crossoverProbability: Double): Array[String] = {
    // TWO POINT CROSSOVER

    var crossoverIndividuals = Array[String]()
    if(crossoverProbability > scala.util.Random.nextDouble()) {
      // 2 different random numbers
      var crossoverPosition = scala.util.Random.shuffle(
        Seq.fill(20)(scala.util.Random.nextInt(24)).distinct
      ).take(2).sorted

      // Create crossover swapping parents
      crossoverIndividuals :+=
        parent1.substring(0, crossoverPosition(0)) +
        parent2.substring(crossoverPosition(0), crossoverPosition(1)) +
        parent1.substring(crossoverPosition(1), parent1.size-1)
      crossoverIndividuals :+=
        parent2.substring(0, crossoverPosition(0)) +
        parent1.substring(crossoverPosition(0), crossoverPosition(1)) +
        parent2.substring(crossoverPosition(1), parent1.size-1)
    } else {
      crossoverIndividuals :+= parent1
      crossoverIndividuals :+= parent2
    }

    crossoverIndividuals
  }

  def mutation(child: Array[String], pm: Double): Array[String] = {
    // BIT-FLIPPING

    var mutants: ArrayBuffer[String] = new ArrayBuffer[String]()

    for(i <- 0 to 1) {
      var mutedChild: StringBuilder = new StringBuilder
      for(j <- 0 to child(i).size-1) {
        if(pm > scala.util.Random.nextDouble()) {
          if(child(i).charAt(j) == '0') {
            mutedChild.append('1')
          } else {
            mutedChild.append('0')
          }
        } else {
          mutedChild += child(i).charAt(j)
        }
      }

      mutants += mutedChild.toString()
    }

    mutants.toArray
  }

}
