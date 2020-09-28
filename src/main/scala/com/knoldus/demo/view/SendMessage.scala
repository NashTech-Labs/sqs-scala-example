package com.knoldus.demo.view

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.knoldus.demo.config.ConfigHelper
import com.knoldus.demo.router.PostUser

import scala.io.StdIn

object SendMessage extends App
{
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    
    val route: Route = PostUser.route
    
    // `route` will be implicitly converted to `Flow` using `RouteResult.route2HandlerFlow`
    val ip = ConfigHelper.HOST
    val port = ConfigHelper.PORT
    val bindingFuture = Http().bindAndHandle(route, ip, port)
    //    val bindingFuture = Http().bindAndHandle(route, "localhost", 8100)
    StdIn.readLine() // let it run until user presses return
    bindingFuture
            .flatMap(_.unbind()) // trigger unbinding from the port
            .onComplete(_ => system.terminate()) // and shutdown when done
}
