package com.example.bgpm.pojo;

import java.math.BigInteger;

public class MapRespNodePojo {
    private String id;
    private String label;
    private String asnName;
    private String company;
    private String country;
    private BigInteger numOfHostedDomains;
    private BigInteger numOfIps;
    private String asnType;

    private int upOrDown;   // -1 下游， 0 自己公司， 1上游

    public MapRespNodePojo() {
    }

    public MapRespNodePojo(String asn) {
        this.label = asn;
    }

    public MapRespNodePojo(String asn, String asnName, String company, String country, BigInteger numOfHostedDomains, BigInteger numOfIps, String asnType) {
        this.label = asn;
        this.asnName = asnName;
        this.company = company;
        this.country = country;
        this.numOfHostedDomains = numOfHostedDomains;
        this.numOfIps = numOfIps;
        this.asnType = asnType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MapRespNodePojo(String asn, int upOrDown) {
        this.id = asn;
        this.label = asn;
        this.upOrDown = upOrDown;
    }

    public int getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(int upOrDown) {
        this.upOrDown = upOrDown;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
        return "MapRespNodePojo{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", asnName='" + asnName + '\'' +
                ", company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", numOfHostedDomains=" + numOfHostedDomains +
                ", numOfIps=" + numOfIps +
                ", asnType='" + asnType + '\'' +
                ", upOrDown=" + upOrDown +
                '}';
    }
}
