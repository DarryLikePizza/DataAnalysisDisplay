package com.example.bgpm.serviceImpl;

import com.example.bgpm.mapper.NodeMapper;
import com.example.bgpm.pojo.ComAnaMapRespPojo;
import com.example.bgpm.pojo.MapRespNodePojo;
import com.example.bgpm.pojo.NodePojo;
import com.example.bgpm.pojo.PathPojo;
import com.example.bgpm.service.CompanyAnalysisService;
import com.example.bgpm.utils.ReadFile;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Service
public class CompanyAnalysisServiceImpl implements CompanyAnalysisService {

    @Autowired
    NodeMapper nodeMapper;

    @Override
    public List<String> getFileList(String path) {
        List<String> list = new ArrayList<>();
        File f = new File(path);
        if(!f.exists()){
            System.out.println(path + " not exists");
        }

        File[] result = f.listFiles();
        for(int i = 0; i< Objects.requireNonNull(result).length; i++){
            File fs = result[i];
            if (!fs.isFile()) {
                list.add(fs.getName());
            }
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public void getDrawData(String company, String monitor, String flag) {

    }

    /**
     * 引力图json数据
     */
    public ComAnaMapRespPojo getUpDownJsonData(String company, String monitor, String flag) throws JSONException, IOException {
        String[] ss = new ReadFile().readJson(company, monitor, flag);
        List<PathPojo> pathArray = new ArrayList<>();
        List<MapRespNodePojo> nodeList = new ArrayList<>();  // 点集合，用于后续得到所有电信系
        HashSet<String> nodeIdSet = new HashSet<>();
        for(int k = 0; k < 2; k++){
            JSONObject json = new JSONObject(ss[k]);

            Iterator it = json.keys();
            while(it.hasNext()){
                String key = (String)it.next();
                String asKey = "AS" + key;
                if(!nodeIdSet.contains(asKey)){
                    if(k == 0){ // down
                        if(!nodeIdSet.contains(asKey)){
                            nodeList.add(new MapRespNodePojo(asKey, 1));
                        }
                    }else{  // up
                        nodeList.add(new MapRespNodePojo(asKey, -1));
                    }
                    nodeIdSet.add(asKey);
                }
                JSONObject value = (JSONObject) json.get(key);

                JSONArray listObj = (JSONArray) value.get("link_as");
                String w = String.valueOf(value.get("weight"));
                BigInteger weight = BigInteger.valueOf(Long.parseLong(w)).divide(BigInteger.valueOf(listObj.length()));

                String keyN = "AS" + key;

                for(int i = 0; i < listObj.length(); i++){
                    String keyIn = "AS" + listObj.get(i).toString();
                    if(!nodeIdSet.contains(keyIn)){
                        nodeList.add(new MapRespNodePojo(keyIn, 0));
                        nodeIdSet.add(keyIn);
                    }
                    if(k == 0){ // down
                        PathPojo pathPojo = new PathPojo(keyN, keyIn, weight);
                        pathArray.add(pathPojo);
                    }else{  // up
                        PathPojo pathPojo = new PathPojo(keyIn, keyN, weight);
                        pathArray.add(pathPojo);
                    }
                }
            }
        }

        List<MapRespNodePojo> nodeArray = new ArrayList<>();
        for (MapRespNodePojo nodePojo : nodeList) {
            String as = nodePojo.getId();
            NodePojo node = nodeMapper.findNodeByAsn(as);
            nodePojo.setCompany(node.getCompany());
            nodePojo.setAsnName(node.getAsnName());
            nodePojo.setAsnType(node.getAsnType());
            nodePojo.setCountry(node.getCountry());
            nodePojo.setNumOfHostedDomains(node.getNumOfHostedDomains());
            nodePojo.setNumOfIps(node.getNumOfIps());
            nodeArray.add(nodePojo);
        }
        return new ComAnaMapRespPojo(nodeArray, pathArray);
    }

    @Override
    public ComAnaMapRespPojo getTreeJsonData(String company, String monitor, String flag) throws JSONException, IOException {
        String[] ss = new ReadFile().readJson(company, monitor, flag);
        JSONObject json = new JSONObject(ss[0]);

        List<PathPojo> pathArray = new ArrayList<>();
        List<MapRespNodePojo> nodeList = new ArrayList<>();  // 点集合，用于后续得到所有电信系
        HashSet<String> nodeIdSet = new HashSet<>();

        Iterator it = json.keys();
        while(it.hasNext()){
            String key = (String) it.next();

            String asKey = "AS" + key;
            if(!nodeIdSet.contains(asKey)){
                nodeList.add(new MapRespNodePojo(asKey, 0));
                nodeIdSet.add(asKey);
            }

            pathArray.add(new PathPojo(company, asKey, BigInteger.valueOf(100)));

            JSONObject value = (JSONObject) json.get(key);
            Iterator itIn = value.keys();
            while(itIn.hasNext()){
                String keyIn = (String) itIn.next();

                String asKeyIn = "AS" + keyIn;
                if(!nodeIdSet.contains(asKeyIn)){
                    nodeList.add(new MapRespNodePojo(asKeyIn, 0));
                    nodeIdSet.add(asKeyIn);
                }
                pathArray.add(new PathPojo(asKey, asKeyIn, BigInteger.valueOf((new Double((Double) value.get(keyIn)).longValue()))));
            }
        }

        List<MapRespNodePojo> nodeArray = new ArrayList<>();
        nodeArray.add(new MapRespNodePojo(company,1));
        for (MapRespNodePojo nodePojo : nodeList) {
            String as = nodePojo.getId();
            NodePojo node = nodeMapper.findNodeByAsn(as);
            nodePojo.setCompany(node.getCompany());
            nodePojo.setAsnName(node.getAsnName());
            nodePojo.setAsnType(node.getAsnType());
            nodePojo.setCountry(node.getCountry());
            nodePojo.setNumOfHostedDomains(node.getNumOfHostedDomains());
            nodePojo.setNumOfIps(node.getNumOfIps());
            nodeArray.add(nodePojo);
        }
        return new ComAnaMapRespPojo(nodeArray, pathArray);
    }
}
