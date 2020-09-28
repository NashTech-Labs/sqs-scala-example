package com.knoldus.demo.router

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.knoldus.demo.config.ConfigHelper
import com.knoldus.demo.entity.{Employee, Status}
import com.knoldus.demo.model.SendMessageToSQS
import com.knoldus.demo.view.JsonSupport

object PostUser extends JsonSupport {
  val route: Route = {
    post {
      path("user") {
        entity(as[Employee]) {
          user => {
            completeWith(instanceOf[Status])
            {
              completer => insert(user , completer)
            }
          }
        }
      }
    }
  }

  private def insert(user: Employee , complete : Status => Unit) =
  {
    val obj = new SendMessageToSQS();
    complete(obj.sendMessage(user, ConfigHelper.QUEUE_NAME))
  }
}
