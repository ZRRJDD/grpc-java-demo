package com.zrrjdd.helloworld;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class HelloWorldServer {

    private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

    private int port = 19999;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build();
        server.start();

        logger.info("Server started , listening on "+port);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("*** shutting down gRpc server since Jvm is shutting down ***");
                HelloWorldServer.this.stop();
                System.out.println("*** server shut down ***");
            }
        });
    }

    private void stop() {
        if (server != null){
            server.shutdown();
        }
    }


//    block 一直到退出程序
    private void blockUntilShutDown() throws InterruptedException {
        if (server != null){
            server.awaitTermination();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        HelloWorldServer server = new HelloWorldServer();
        server.start();
        server.blockUntilShutDown();
    }

}
