package com.github.optimizationwithmetaheuristics.combinatorialproblem.ts

import com.github.optimizationwithmetaheuristics.utils.ga.integervector.Chromosome
import org.slf4j.LoggerFactory

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class Neighborhood {

  implicit val logger = LoggerFactory.getLogger(getClass.getName)

  def getNeighborhoodCombinations(chromosome: Chromosome): Array[(Int, Int)] = {
    val combinations = new ArrayBuffer[(Int, Int)]

    for (i <- 0 to chromosome.getGenotype.size-1) {
      for (j <- i to chromosome.getGenotype.size-1) {
        if (i != j) {
          combinations.append(
            (chromosome.getGenotype(i), chromosome.getGenotype(j))
          )
        }
      }
    }

    combinations.toArray
  }

  def getNeighborhood(chromosome: Chromosome): Array[Chromosome] = {
    // Get all combinations
    val combinations = getNeighborhoodCombinations(chromosome)
    val allChromosomes = new ArrayBuffer[Chromosome]()

    for (combination <- combinations) {
      var newChromosome: ArrayBuffer[Int] = chromosome.getGenotype.to(ArrayBuffer)
      newChromosome(chromosome.getGenotype.indexOf(combination._1)) = combination._2
      newChromosome(chromosome.getGenotype.indexOf(combination._2)) = combination._1
      allChromosomes.append(new Chromosome(newChromosome.toArray))
    }

    allChromosomes.toArray
  }

}
