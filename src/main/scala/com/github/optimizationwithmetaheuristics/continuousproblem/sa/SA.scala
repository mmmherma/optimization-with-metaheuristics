package com.github.optimizationwithmetaheuristics.continuousproblem.sa

import scala.collection.mutable.ListBuffer
import scala.math.pow

// Simulated Annealing
// Himmelblau Function
//    z = (x^2 + y - 11)^2 + (x + y^2 - 7)^2

// Search space
//    x0      Initial solution
//    xi      Solution i
//    xF      Final solution
//    f(xi)   Objective funtion evaluated at xi

// Parameters
//    To      Initial temperature
//    Tt      Temperature at stage t
//    TF      Final Temperature
//    alpha   Cooling parameter
//    M       Number of temperatures
//    N(t)    Number of moves at temperature t
//    theta   Move operator

object SA extends App {

  // Function to optimize (minimize)
  def z(x: Double, y: Double): Double =
    pow(pow(x, 2) + y - 11, 2) + pow(x + pow(y, 2) - 7, 2)

  // Function to compute probability
  def neighbourProbability(potential: Double, current: Double) =
    1 / scala.math.exp(( potential - current ) / T0)

  // STEP 0. Init problem
  // Initial position
  protected var x: Double = 2
  protected var y: Double = 1
  // Initial parameters
  protected var T0: Double = 1000
  protected val alpha: Double = 0.85
  protected val M: Int = 300
  protected val N: Int = 15
  protected val k: Double = 0.1
  // Store partial result in order to plot
  protected var temperatureList = new ListBuffer[Double]
  protected var objectiveValueList = new ListBuffer[Double]

  for (i <- 0 until M) {
    for (j <- 0 until N) {
      // STEP 1. Get new potential solution
      // Change x axis
      val xRandom1 = scala.util.Random.nextDouble()
      val xRandom2 = scala.util.Random.nextDouble()
      var xStep: Double = 0.0

      if(xRandom1 > 0.5) {
        xStep = k * xRandom2
      } else {
        xStep = -k * xRandom2
      }

      // Change y axis
      val yRandom1 = scala.util.Random.nextDouble()
      val yRandom2 = scala.util.Random.nextDouble()
      var yStep: Double = 0.0

      if(yRandom1 > 0.5) {
        yStep = k * yRandom2
      } else {
        yStep = -k * yRandom2
      }

      // Potential solution is
      val xPotential = x + xStep
      val yPotential = y + yStep

      // Potential objective value
      val zTemp = z(xPotential, yPotential)
      // Current objective value
      val zCurrent = z(x, y)

      // STEP 2. Decide to choose potential neighbour or not
      // Compute probability to take potential as current
      val tempProbability = neighbourProbability(zTemp, zCurrent)
      // Get probability to compare in case current objective value is worst
      val worstProbability = scala.util.Random.nextDouble()

      if (zTemp < zCurrent) {
        // Better solution then potential becomes current
        x = xPotential
        y = yPotential
      } else if (worstProbability < tempProbability) {
        // Worse objective function but we take it
        // Worse solutions may conduct us to a better solution in the future
        x = xPotential
        y = yPotential
      } else {
        // Worse objective function and we do not take it
        // We stay where we currently are
        x = x
        y = y
      }
    }

    // Store values to plot
    temperatureList += T0
    objectiveValueList += z(x, y)

    // STEP 3. Increase temperature
    T0 = alpha * T0
  }

  // Print solutions
  println("X Y")
  for(i <- 0 to temperatureList.size - 1) {
    println(temperatureList(i) + " " + objectiveValueList(i))
  }

  println(x)
  println(y)
  println(z(x, y))

}
