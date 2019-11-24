package com.it_uatech.benchmarkJMH;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Benchmarks {

    @State(Scope.Thread)
    public static class BenchmarkState{
        List<Integer> linkedList = new LinkedList<>();
        List<Integer> arrayList = new ArrayList<>();
        Set<Integer> hashSet = new HashSet<>();
        int min = 0;
        int max = 9_999_999;

        @Setup
        public void initState(){
            for(int i = min; i < (max + 1); i++){
                linkedList.add(i);
                arrayList.add(i);
                hashSet.add(i);
            }
        }
    }

    @Benchmark
    public List<Integer> containsLinkedList(BenchmarkState state){
        state.linkedList.contains(2_500_000);
        return state.linkedList;
    }

    @Benchmark
    public List<Integer> containsArrayList(BenchmarkState state){
        state.arrayList.contains(2_500_000);
        return state.arrayList;
    }

    @Benchmark
    public Set<Integer> containsHashSet(BenchmarkState state){
        state.hashSet.contains(2_500_000);
        return state.hashSet;
    }

    @Benchmark
    public List<Integer> addElementLinkedList(BenchmarkState state){
        state.linkedList.add(0, 6789);
        return state.linkedList;
    }

    @Benchmark
    public List<Integer> addElementArrayList(BenchmarkState state){
        state.arrayList.add(0, 6789);
        return state.arrayList;
    }

    @Benchmark
    public Set<Integer> addElementHashSet(BenchmarkState state){
        state.hashSet.add(state.max+356);
        return state.hashSet;
    }
}
