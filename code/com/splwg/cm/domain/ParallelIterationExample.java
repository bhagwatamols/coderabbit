package com.splwg.cm.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class ParallelIterationExample {

    public static void main(String[] args) {
        // Example data - replace this with your own data source
        Set<CmPojo> dataList = new HashSet<CmPojo>();
        for (int i = 0; i < 20; i++) {
            dataList.add(new CmPojo("Am"));
        }

        // Number of threads to be used
        int parallelism = Runtime.getRuntime().availableProcessors();
        System.out.println(parallelism);
        // Create a ForkJoinPool with the desired parallelism
        ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

        // Perform parallel iteration using ForkJoinPool
        forkJoinPool.submit(() ->
            dataList.parallelStream().forEach(ParallelIterationExample::processData)
        ).join();

        // Shutdown the ForkJoinPool
        forkJoinPool.shutdown();
        
        for(CmPojo s:dataList)
        {
        	System.out.println(s.getName());
        }
    }

    // Example method to process each data entry
    public static void processData(CmPojo data) {
       data.setName("AMOL");
    }
}
