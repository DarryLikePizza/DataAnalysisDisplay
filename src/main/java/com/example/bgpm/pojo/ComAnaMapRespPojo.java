package com.example.bgpm.pojo;

import java.util.List;

public class ComAnaMapRespPojo {
    private List<MapRespNodePojo> nodes;
    private List<PathPojo> edges;

    public List<MapRespNodePojo> getNodes() {
        return nodes;
    }

    public void setNodes(List<MapRespNodePojo> nodes) {
        this.nodes = nodes;
    }

    public List<PathPojo> getEdges() {
        return edges;
    }

    public void setEdges(List<PathPojo> edges) {
        this.edges = edges;
    }

    public ComAnaMapRespPojo() {
    }

    public ComAnaMapRespPojo(List<MapRespNodePojo> nodeList, List<PathPojo> pathList) {
        this.nodes = nodeList;
        this.edges = pathList;
    }

    @Override
    public String toString() {
        return "CompanyAnalysisResponsePojo{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                '}';
    }
}
