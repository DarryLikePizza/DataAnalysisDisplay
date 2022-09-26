package com.example.bgpm.mapper;

import com.example.bgpm.pojo.NodePojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository(value="NodeMapper")
public interface NodeMapper {
    NodePojo findNodeByAsn(String asn);
    NodePojo findNodeById(int id);

    List<NodePojo> getAllInfo();

}
