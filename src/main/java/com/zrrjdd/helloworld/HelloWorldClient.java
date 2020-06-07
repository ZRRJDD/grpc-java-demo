package com.zrrjdd.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloWorldClient {

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

    public HelloWorldClient(String host ,int port) {
        this.channel = ManagedChannelBuilder.forAddress(host,port).usePlaintext().build();
        this.blockingStub = GreeterGrpc.newBlockingStub(channel);
    }


    public void sayHello(String name) {

        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply reply;
        try {

            reply = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e){
            logger.log(Level.WARNING,"RPC failed{0}",e.getStatus());
            return;
        }
        logger.info("Message from gRPC-Server: "+reply.getMessage());

    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    public static void main(String[] args) throws InterruptedException {
        HelloWorldClient client = new HelloWorldClient("localhost",19999);
        try {
            String user = "zrrjdd";
            if (args.length >0 ){
                user = args[0];
            }
            client.sayHello(user);
        } finally {
            client.shutdown();
        }
    }


}
