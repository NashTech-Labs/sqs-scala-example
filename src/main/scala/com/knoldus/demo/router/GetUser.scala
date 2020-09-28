package com.knoldus.demo.router

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.knoldus.demo.config.ConfigHelper
import com.knoldus.demo.entity.Status
import com.knoldus.demo.model.FetchMessageFromSQS
import com.knoldus.demo.view.JsonSupport

object GetUser extends JsonSupport {
  val route: Route = {
    path("user") {
      get
      {
        completeWith(instanceOf[List[Status]])
        {
          completer =>
          {
            getById(completer)
          }
        }
      }
    }
  }

  private def getById(completer: List[Status] => Unit) =
  {
    val obj = new FetchMessageFromSQS()
    completer(obj.fetchMessage(ConfigHelper.QUEUE_NAME))
  }
}
