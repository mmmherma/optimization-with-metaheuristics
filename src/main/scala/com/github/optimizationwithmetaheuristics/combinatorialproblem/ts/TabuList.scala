package com.github.optimizationwithmetaheuristics.combinatorialproblem.ts

import com.github.optimizationwithmetaheuristics.utils.ga.integervector.Chromosome
import org.slf4j.LoggerFactory
import scala.collection.mutable.ArrayBuffer

class TabuList(size: Int) {

  implicit val logger = LoggerFactory.getLogger(getClass.getName)

  protected var tabuList: ArrayBuffer[Chromosome] = new ArrayBuffer[Chromosome](10)

  def addElement(chromosome: Chromosome): Unit = {
    if(tabuList.size < size) {

    }
  }

  def getCurrentSolution(chromosomes: Array[Chromosome]): Chromosome = {
    var currentSolution = chromosomes(0)

    if(tabuList.size == 0) {
      tabuList += chromosomes(0)
    } else {
      // If tabu list full then remove oldest element
      if(tabuList.size == size-1) {
        tabuList.remove(0)
      }

      // Append new solution
      var seek = true
      var i = 0
      while (seek && (i < tabuList.size)) {
        if(currentSolution.getGenotype.sameElements(tabuList(i).getGenotype)) {
          i += 1
        } else {
          currentSolution = chromosomes(i)
          tabuList += currentSolution
          seek = false
        }
      }
    }

    currentSolution
  }

}
