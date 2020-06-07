package com.zrrjdd.helloworld;

import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;


/**
 * 实现 定义一个服务接口的实现类，写具体的Server 逻辑
 */
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {


    private static final Logger logger = Logger.getLogger(GreeterImpl.class.getName());

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        HelloReply reply = HelloReply.newBuilder().setMessage(" hello :" + request.getName()).build();

        logger.info("Server get Name:"+request.getName());

        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }
}
