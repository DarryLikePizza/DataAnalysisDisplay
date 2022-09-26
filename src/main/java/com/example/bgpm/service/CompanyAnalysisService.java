package com.example.bgpm.service;

import com.example.bgpm.pojo.ComAnaMapRespPojo;
import org.codehaus.jettison.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface CompanyAnalysisService {
    // flag为1，只返回树状图图，flag为2，只返回前后图，flag为3，返回全部图
    void getDrawData(String company, String monitor, String flag);
    ComAnaMapRespPojo getUpDownJsonData(String company, String monitor, String flag) throws JSONException, IOException;
    ComAnaMapRespPojo getTreeJsonData(String company, String monitor, String flag) throws JSONException, IOException;

    List<String> getFileList(String path);

}
