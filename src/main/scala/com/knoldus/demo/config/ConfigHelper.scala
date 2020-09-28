package com.knoldus.demo.config

import com.typesafe.config.ConfigFactory

object ConfigHelper {
  val QUEUE_NAME = ConfigFactory.load().getString("QUEUE_NAME")
 
  val HOST = ConfigFactory.load().getString("host")
  
  val PORT = ConfigFactory.load().getString("port").toInt
  
  val PORT2 = ConfigFactory.load().getString("port2").toInt
  
}
