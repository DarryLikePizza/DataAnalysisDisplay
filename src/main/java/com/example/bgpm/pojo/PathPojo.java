package com.example.bgpm.pojo;

import java.math.BigInteger;

public class PathPojo {
    private String source;
    private String target;
    private BigInteger weight;

    public PathPojo() {
    }

    public PathPojo(String begin, String end, BigInteger weight) {
        this.source = begin;
        this.target = end;
        this.weight = weight;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public BigInteger getWeight() {
        return weight;
    }

    public void setWeight(BigInteger weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "PathPojo{" +
                "begin=" + source +
                ", end=" + target +
                ", weight=" + weight +
                '}';
    }
}