package com.knoldus.demo.model

import java.io.IOException

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.{AmazonSQSException, CreateQueueRequest, SendMessageRequest}
import com.fasterxml.jackson.databind.ObjectMapper
import com.knoldus.demo.entity.{Employee, Status}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import spray.json.DefaultJsonProtocol


class SendMessageToSQS extends SprayJsonSupport with DefaultJsonProtocol
{
    implicit val formats = DefaultFormats
    def sendMessage(user: Employee, QUEUE_NAME: String): Status = {
        val sqs = AmazonSQSClientBuilder.defaultClient
        try
        {
            sqs.createQueue(QUEUE_NAME)
        }
        catch
        {
            case e: AmazonSQSException =>
                if (!(e.getErrorCode == "QueueAlreadyExists")) throw e
        }
        val queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl
        val objectMapper = new ObjectMapper
        try {
//            val jsonStr = objectMapper.writeValueAsString(user)
            val jsonString = write(user)
            val send_msg_request = new SendMessageRequest().withQueueUrl(queueUrl).withMessageBody(jsonString).withDelaySeconds(5)
            sqs.sendMessage(send_msg_request)
            System.out.println(jsonString)
        } catch {
            case e: IOException =>
                e.printStackTrace()
                Status(false)
        }
        Status(true)
    }
}
