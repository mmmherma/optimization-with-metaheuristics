package com.github.optimizationwithmetaheuristics.continuousproblem.ga.HummelblauFunction

import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

class Operators {

  implicit val logger = LoggerFactory.getLogger(getClass.getName)

  def crossover(parent1: Chromosome, parent2: Chromosome, crossoverProbability: Double): Array[Chromosome] = {
    // TWO POINT CROSSOVER

    var crossoverIndividuals = ArrayBuffer[Chromosome]()
    if(crossoverProbability > scala.util.Random.nextDouble()) {
      // 2 different random numbers
      var crossoverPosition = scala.util.Random.shuffle(
        Seq.fill(20)(scala.util.Random.nextInt(parent1.getGenotype.size)).distinct
      ).take(2).sorted

      // Create crossover swapping parents
      crossoverIndividuals :+= new Chromosome(
        parent1.getGenotype.substring(0, crossoverPosition(0)) +
        parent2.getGenotype.substring(crossoverPosition(0), crossoverPosition(1)) +
        parent1.getGenotype.substring(crossoverPosition(1), parent1.getGenotype.size)
      )
      crossoverIndividuals :+= new Chromosome(
        parent2.getGenotype.substring(0, crossoverPosition(0)) +
        parent1.getGenotype.substring(crossoverPosition(0), crossoverPosition(1)) +
        parent2.getGenotype.substring(crossoverPosition(1), parent2.getGenotype.size)
      )
    } else {
      crossoverIndividuals :+= parent1
      crossoverIndividuals :+= parent2
    }

    crossoverIndividuals.toArray
  }

  def mutation(child: Chromosome, pm: Double): Chromosome = {
    // BIT-FLIPPING

    var mutedChild: StringBuilder = new StringBuilder
    for(i <- 0 to child.getGenotype.size-1) {
      if(pm > scala.util.Random.nextDouble()) {
        if(child.getGenotype.charAt(i) == '0') {
          mutedChild.append('1')
        } else {
          mutedChild.append('0')
        }
      } else {
        mutedChild.append(child.getGenotype.charAt(i))
      }
    }

    new Chromosome(mutedChild.toString)
  }

}
