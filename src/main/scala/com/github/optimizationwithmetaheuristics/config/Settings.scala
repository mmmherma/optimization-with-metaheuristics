package com.github.optimizationwithmetaheuristics.config

import com.typesafe.config.Config

class Settings(config: Config) extends Configuration {

  override def M: Int = config.getInt("ga.M")

  override def N: Int = config.getInt("ga.N")

  override def l: Int = config.getInt("ga.l")

  override def pc: Float = config.getInt("ga.pc")

  override def pm: Float = config.getInt("ga.pm")

  override def k: Int = config.getInt("ga.k")

}
