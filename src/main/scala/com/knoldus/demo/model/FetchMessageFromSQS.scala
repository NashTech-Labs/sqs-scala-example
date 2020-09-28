package com.knoldus.demo.model

import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.knoldus.demo.entity.{Employee, Status}
import net.liftweb.json.{DefaultFormats, _}

import scala.collection.JavaConverters._

class FetchMessageFromSQS
{
    implicit val formats = DefaultFormats
    def fetchMessage(QUEUE_NAME: String): List[Status] = {
        val sqs = AmazonSQSClientBuilder.defaultClient
        val queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl
        val messages = asScalaBuffer(sqs.receiveMessage(queueUrl).getMessages).toList
        println("No of message received:: " + messages.size)
        messages.map(m => {
            try
            {
                val jValue = parse(m.getBody)
                val user = jValue.extract[Employee]
                println(s"Message fetched with username: ${user.name} and id: ${user.id}")
                println("Removing message from queue with id : " + user.id)
                sqs.deleteMessage(queueUrl, m.getReceiptHandle)
            }
            catch
            {
                case exception: Exception =>
                {
                    exception.printStackTrace()
                    Status(false)
                }
                
            }
            Status(true)
        })
    }
    
}
