package com.example.bgpm.pojo;

import java.math.BigInteger;

public class NodePojo {
    private String asn;
    private String asnName;
    private String company;
    private String country;
    private BigInteger numOfHostedDomains;
    private BigInteger numOfIps;
    private String asnType;

    public NodePojo() {
    }

    public NodePojo(String asn) {
        this.asn = asn;
    }

    public NodePojo(String asn, String asnName, String company, String country, BigInteger numOfHostedDomains, BigInteger numOfIps, String asnType) {
        this.asn = asn;
        this.asnName = asnName;
        this.company = company;
        this.country = country;
        this.numOfHostedDomains = numOfHostedDomains;
        this.numOfIps = numOfIps;
        this.asnType = asnType;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public String getAsnName() {
        return asnName;
    }

    public void setAsnName(String asnName) {
        this.asnName = asnName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigInteger getNumOfHostedDomains() {
        return numOfHostedDomains;
    }

    public void setNumOfHostedDomains(BigInteger numOfHostedDomains) {
        this.numOfHostedDomains = numOfHostedDomains;
    }

    public BigInteger getNumOfIps() {
        return numOfIps;
    }

    public void setNumOfIps(BigInteger numOfIps) {
        this.numOfIps = numOfIps;
    }

    public String getAsnType() {
        return asnType;
    }

    public void setAsnType(String asnType) {
        this.asnType = asnType;
    }

    @Override
    public String toString() {
        return "NodePojo{" +
                "asn='" + asn + '\'' +
                ", asnName='" + asnName + '\'' +
                ", company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", numOfHostedDomains=" + numOfHostedDomains +
                ", numOfIps=" + numOfIps +
                ", asnType='" + asnType +
                '}';
    }
}
