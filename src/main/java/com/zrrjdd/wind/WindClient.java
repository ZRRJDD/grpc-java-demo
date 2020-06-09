package com.zrrjdd.wind;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import zrrjdd.examples.wind.Options;
import zrrjdd.examples.wind.WindComputeGrpc;
import zrrjdd.examples.wind.WindData;

import java.util.concurrent.TimeUnit;

public class WindClient {

    private final ManagedChannel channel;

    private final WindComputeGrpc.WindComputeBlockingStub windComputeBlockingStub;

    public WindClient(String host,int port){
        this.channel = ManagedChannelBuilder.forAddress(host,port).usePlaintext().build();
        this.windComputeBlockingStub = WindComputeGrpc.newBlockingStub(channel);
    }


    private void wsd(){
        Options options = Options.newBuilder()
                .setCodes("002017.SZ")
                .setFields("close,volume")
                .setBeginTime("2020-04-26")
                .setEndTime("2020-05-25")
                .setOptions("")
                .build();

        WindData windData = windComputeBlockingStub.wsd(options);
        System.out.println(windData);
        System.out.println("------------------------");
        System.out.println(windData.getResult().toStringUtf8());
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        WindClient windClient = new WindClient("192.168.3.33",19998);
        try {
            windClient.wsd();
        } finally {
            windClient.shutdown();
        }
    }

}
