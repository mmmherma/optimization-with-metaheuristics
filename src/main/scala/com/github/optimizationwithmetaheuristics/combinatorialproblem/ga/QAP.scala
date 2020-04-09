package com.github.optimizationwithmetaheuristics.combinatorialproblem.ga

import com.github.optimizationwithmetaheuristics.utils.matrix.Matrix

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

  protected def objectiveValue(matrix: Matrix): Int = {
    var sum: Int = 0
    for (i <- 0 to 7) {
      sum += matrix.getRow(i).sum
    }

    sum
  }

}
