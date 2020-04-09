package com.github.optimizationwithmetaheuristics.utils.config

trait Configuration {

  def M: Int
  def N: Int
  def l: Int
  def pc: Double
  def pm: Double
  def k: Int
  def upperBound: Int
  def lowerBound: Int

}
