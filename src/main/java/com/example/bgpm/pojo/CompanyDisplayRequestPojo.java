package com.example.bgpm.pojo;

public class CompanyDisplayRequestPojo {
    private String company;
    private String monitor;
    private String flag;

    public CompanyDisplayRequestPojo() {
    }

    public CompanyDisplayRequestPojo(String company, String monitor, String flag) {
        this.company = company;
        this.monitor = monitor;
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    @Override
    public String toString() {
        return "CompanyDisplayRequestPojo{" +
                "company='" + company + '\'' +
                ", monitor='" + monitor + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
