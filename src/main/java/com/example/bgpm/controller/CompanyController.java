package com.example.bgpm.controller;

import com.example.bgpm.pojo.ComAnaMapRespPojo;
import com.example.bgpm.pojo.CompanyDisplayRequestPojo;
import com.example.bgpm.serviceImpl.CompanyAnalysisServiceImpl;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@ResponseBody
@CrossOrigin
public class CompanyController {

    @Autowired
    CompanyAnalysisServiceImpl companyAnalysisService;

    @RequestMapping(value="/index")
    public String show() {
        return "index.html";
    }

    @RequestMapping(value="/getUpDown",method= RequestMethod.POST)
    public ComAnaMapRespPojo getUpDown(@ModelAttribute("companyRequest") CompanyDisplayRequestPojo companyRequest) throws JSONException, IOException {
        System.out.println("getUpDown: " + companyRequest);

        ComAnaMapRespPojo json = companyAnalysisService.getUpDownJsonData(
                companyRequest.getCompany(),
                companyRequest.getMonitor(),
                companyRequest.getFlag());
        System.out.println("getUpDown success!");
        return json;
    }

    @RequestMapping(value="/getTree",method= RequestMethod.POST)
    public ComAnaMapRespPojo getTree(@ModelAttribute("companyRequest") CompanyDisplayRequestPojo companyRequest) throws JSONException, IOException {
        System.out.println("getTree: " + companyRequest);

        ComAnaMapRespPojo json = companyAnalysisService.getTreeJsonData(
                companyRequest.getCompany(),
                companyRequest.getMonitor(),
                companyRequest.getFlag());
        System.out.println("getTree success!");
        return json;
    }

    @RequestMapping(value="/getTest",method= RequestMethod.POST)
    public String getTest(String s) {
        System.out.println(s);
        return s;
    }

    @RequestMapping(value="/getFileList",method= RequestMethod.POST)
    public List<String> getFileList(@ModelAttribute("companyRequest") CompanyDisplayRequestPojo companyRequest) {
        String a = companyRequest.getCompany();
//        String filePath = "src/main/resources/static/data/input/";
        // 打包路径
        String filePath = "data/company/";
        if(a!=null){
            filePath += a + "/";
        }
        System.out.println("getFileList: " + filePath);
        List<String> ans = companyAnalysisService.getFileList(filePath);
        System.out.println("getFileList success!");
        return ans;
    }

}
