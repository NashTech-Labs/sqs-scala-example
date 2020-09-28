This template is about getting yourself familiarize with Amazon SQS.

For setup:
    
    1) Download and extract aws sdk on your system.
    2) Navigate to ".aws/config" and set region, by default it will be us-east-1.
    3) Similarly, navigate to ".aws/credentials" and set you access_key_id and secret_access_key
    
Now, you can clone this repository and build it.
    
    mvn clean compile
    And then, run:
        SendMessage and Retrieve Message API.
        
    Open postman or insomnia or using curl, send post request to SendMessage API with raw json body
    path = localhost:8100/user
    {
        "id":0,
        "name":"yamini"
    }
    
    And login to AWS console -> sqs -> you will see, queue name "testing" with Messages-Available = 1
    
    Now, send a get request to RetrieveMessage API, path = localhost:8300/user
    
    In the Intellij's console, you will see the retrieved message. And on AWS console, you will notice that the message is now removed. 
    