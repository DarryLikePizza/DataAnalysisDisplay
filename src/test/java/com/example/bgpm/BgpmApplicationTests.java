package com.example.bgpm;

import com.example.bgpm.mapper.NodeMapper;
import com.example.bgpm.pojo.NodePojo;
import com.example.bgpm.serviceImpl.CompanyAnalysisServiceImpl;
import org.codehaus.jettison.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class BgpmApplicationTests {
    @Autowired
    private NodeMapper nodeMapper;

    @Test
    void contextLoads() throws JSONException, IOException {
        NodePojo nodePojo = nodeMapper.findNodeByAsn("AS7922");
        System.out.println(nodePojo);

    }

}
