package com.github.optimizationwithmetaheuristics.utils.ga.integervector

import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

class Operators {

  implicit val logger = LoggerFactory.getLogger(getClass.getName)

  def crossover(parent1: Chromosome, parent2: Chromosome, crossoverProbability: Double): Array[Chromosome] = {
    // TWO POINT CROSSOVER AVOIDING REPETITIONS

    var crossoverIndividuals = ArrayBuffer[Chromosome]()
    if(crossoverProbability > scala.util.Random.nextDouble()) {
      // 2 different random numbers
      var crossoverPosition = scala.util.Random.shuffle(
        Seq.fill(20)(scala.util.Random.nextInt(parent1.getGenotype.size)).distinct
      ).take(2).sorted

      // Create crossover swapping parents
      // Child 1
      val parent2OfParent1 = parent2.getGenotype.slice(crossoverPosition(0), crossoverPosition(1))
      val parent1OfParent1 = new ArrayBuffer[Int](8)
      for (number <- parent1.getGenotype) {
        if(parent2OfParent1.indexOf(number) == -1) {
          parent1OfParent1 += number
        }
      }
      val child1: Array[Int] =
        parent1OfParent1.slice(0, crossoverPosition(0)).toArray ++
          parent2OfParent1 ++
          parent1OfParent1.slice(crossoverPosition(0), parent1.getGenotype.size).toArray
      crossoverIndividuals :+= new Chromosome(child1)

      // Child 2
      val parent1OfParent2 = parent1.getGenotype.slice(crossoverPosition(0), crossoverPosition(1))
      val parent2OfParent2 = new ArrayBuffer[Int](8)
      for (number <- parent2.getGenotype) {
        if(parent1OfParent2.indexOf(number) == -1) {
          parent2OfParent2 += number
        }
      }
      val child2: Array[Int] =
        parent2OfParent2.slice(0, crossoverPosition(0)).toArray ++
        parent1OfParent2 ++
        parent2OfParent2.slice(crossoverPosition(0), parent2.getGenotype.size).toArray
      crossoverIndividuals :+= new Chromosome(child2)
    } else {
      crossoverIndividuals :+= parent1
      crossoverIndividuals :+= parent2
    }

    crossoverIndividuals.toArray
  }

  def mutation(child: Chromosome, pm: Double): Chromosome = {
    // BIT-FLIPPING

    // Generate 2 rantom position to perform mutation
    val mutationPosition1 = scala.util.Random.nextInt(8)
    var mutationPosition2 = scala.util.Random.nextInt(8)

    while(mutationPosition1 == mutationPosition2) {
      mutationPosition2 = scala.util.Random.nextInt(8)
    }

    // Check if mutations must be performed
    var mutedChild: ArrayBuffer[Int] = new ArrayBuffer[Int](8)
    if(pm > scala.util.Random.nextDouble()) {
      mutedChild = child.getGenotype.to(ArrayBuffer)
      mutedChild(mutationPosition1) = child.getGenotype(mutationPosition2)
      mutedChild(mutationPosition2) = child.getGenotype(mutationPosition1)

      new Chromosome(mutedChild.toArray)
    } else {
      child
    }
  }

}
