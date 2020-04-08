package com.github.optimizationwithmetaheuristics.config

import com.typesafe.config.Config

class Settings(config: Config) extends Configuration {

  override def M: Int = config.getInt("problems.continuousproblem.ga.himmelblau.M")

  override def N: Int = config.getInt("problems.continuousproblem.ga.himmelblau.N")

  override def l: Int = config.getInt("problems.continuousproblem.ga.himmelblau.l")

  override def pc: Double = config.getDouble("problems.continuousproblem.ga.himmelblau.pc")

  override def pm: Double = config.getDouble("problems.continuousproblem.ga.himmelblau.pm")

  override def k: Int = config.getInt("problems.continuousproblem.ga.himmelblau.k")

  override def upperBound: Int = config.getInt("problems.continuousproblem.ga.himmelblau.upperBound")

  override def lowerBound: Int = config.getInt("problems.continuousproblem.ga.himmelblau.lowerBound")

}
