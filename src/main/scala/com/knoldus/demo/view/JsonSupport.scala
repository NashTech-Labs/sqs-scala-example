package com.knoldus.demo.view

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.demo.entity.{Employee, Status}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val employeeFormat = jsonFormat2(Employee)
  implicit val getresponseFormat = jsonFormat1(Status)
}
