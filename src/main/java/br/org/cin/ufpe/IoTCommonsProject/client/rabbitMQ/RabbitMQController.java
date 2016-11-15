package br.org.cin.ufpe.IoTCommonsProject.client.rabbitMQ;

import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;

public class RabbitMQController {

    private RabbitMQConnection connection;
    private String queuName;

    public RabbitMQController() {
        this.connection = new RabbitMQConnection();
    }

    public RabbitMQController(String queuName){
        this();
        this.queuName = queuName;
    }

    public void subscribe(final SubscriptionListener listener){    	
        connection.subscribe(listener);
    }

    public void discoverDevices(){
         // Request all registered devices
        //  Split by QUEUE
    }

    public void register(){
        // Assign the device using.
    }

}
