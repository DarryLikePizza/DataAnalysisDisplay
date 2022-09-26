package com.example.bgpm.utils.bgp1_0;


import com.example.bgpm.bean.AsDetectBean;
import com.example.bgpm.bean.AsLineBean;
import com.example.bgpm.bean.AsNodeBean;
import com.example.bgpm.utils.SaveAsFile;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class KSkipMap {

    /**
     * get node list in each level, get duplicated net line info
     * @param centerAs
     * @param asDetectDataset
     * @param asLocationDictionary
     * @param k
     * @param chinaAs
     * @param findChina
     * @return
     */
    public void skipAnalysis(String centerAs, List<AsDetectBean> asDetectDataset,
                             HashMap<String, AsNodeBean> asLocationDictionary, int k,
                             String chinaAs, Boolean findChina,
                             String nodeSavePath, String lineSavePath,
                             String nodeSavePathForNation, String lineSavePathForNation,
                             String asResultPath, String nationResultPath,
                             String asResultPath2, String asResultPath4) throws FileNotFoundException {

        HashMap<String, BigInteger> lineMap = new HashMap<String, BigInteger>(); // duplicated net line info map
        HashMap<String, BigInteger> lineMapForNation = new HashMap<String, BigInteger>();
        HashSet<String> nodeSet = new HashSet<String>();  // duplicated node set
        HashSet<String> nationSet = new HashSet<String>();  // duplicated nation set

        List<List<String>> nodesInEachLevel = new ArrayList<List<String>>();
        List<List<String>> nodesInEachLevel2 = new ArrayList<List<String>>();
        List<List<String>> nodesInEachLevel4 = new ArrayList<List<String>>();
        // begin node
        List<String> beginList = new ArrayList<String>();
        if(findChina) {
            beginList.add(chinaAs);
            nodeSet.add(chinaAs);
        }
        else {
            beginList.add(centerAs);
            nodeSet.add(centerAs);
        }
        nodesInEachLevel.add(beginList);
        nodesInEachLevel2.add(beginList);
        nodesInEachLevel4.add(beginList);

        // begin nations
        String beginNation = asLocationDictionary.get(centerAs).getNationName();
        List<List<String>> nationsInEachLevel = new ArrayList<List<String>>();
        List<String> beginNationsList = new ArrayList<String>();
        if(findChina) {
            beginNation = asLocationDictionary.get(chinaAs).getNationName();
        }
        beginNationsList.add(beginNation);
        nationSet.add(beginNation);
        nationsInEachLevel.add(beginNationsList);

        int ASLIMIT = (int)Math.pow(2.0, 16.0);  // as limit count

        for (AsDetectBean asDetectItem : asDetectDataset) {
            //
            String[] asList = asDetectItem.getAsList().split(" ");

            if (!Objects.equals(asList[0], centerAs)) continue; // del As which in-nation

            int asListSize = asList.length;

            int i = 0;  // begin node cycle index
            int t = 0;  // begin node static index

            // DEMAND: begin node may not the first in the record
            if(findChina){
                int hasChinaAs = 0;
                AsNodeBean asNBB = asLocationDictionary.get(chinaAs);
                if(asNBB==null) continue;  // this node not in dict
//                beginNation = asNBB.getNationName();  // get begin nation name
                for (; t < asListSize - 1; t++){  // get the target node location in this record
                    if(Objects.equals(asList[t], chinaAs)){
                        hasChinaAs = 1;
                        i = t;
                        break;
                    }
                }
                if(hasChinaAs == 0) continue;  // this node not in this record
            }

            // cycle to get (1) nodes in each level, (2) line map
            for (; i < t+k && i < asListSize - 1; i++) {
                String leftNode = asList[i];
                String rightNode = asList[i+1];

                if(Objects.equals(leftNode, rightNode)) continue;
                AsNodeBean asNBR = asLocationDictionary.get(rightNode);  // confirm can build a line
                if(asNBR==null) continue;
                String rightNation = asNBR.getNationName();
//                if(rightNation.equals(beginNation)) continue;
                AsNodeBean asNBL = asLocationDictionary.get(leftNode);
                if(asNBL==null) continue;
                String leftNation = asNBL.getNationName();
//                if(leftNation==null || leftNation.equals(rightNation) ||
//                        (i!=t && beginNation.equals(leftNation))) continue;
                String key = leftNode + "-" + rightNode;  // connect to get key value

                // (1) get nodes in each level
                if(!nodeSet.contains(rightNode)){  // hadn't read it
                    nodeSet.add(rightNode);
                    if(nodesInEachLevel.size()<(i-t+2)){  // if don't have this level yet
                        List<String> newList = new ArrayList<String>();
                        newList.add(rightNode);
                        nodesInEachLevel.add(newList);
                    } else {
                        nodesInEachLevel.get(i-t+1).add(rightNode);
                    }

                    int asId = Integer.parseInt(rightNode);
                    if(asId <= ASLIMIT) {
                        // 2字节
                        if(nodesInEachLevel2.size()<(i-t+2)){  // if don't have this level yet
                            List<String> newList = new ArrayList<String>();
                            newList.add(rightNode);
                            nodesInEachLevel2.add(newList);
                        } else {
                            nodesInEachLevel2.get(i-t+1).add(rightNode);
                        }
                    } else {
                        // 4字节
                        if(nodesInEachLevel4.size()<(i-t+2)){  // if don't have this level yet
                            List<String> newList = new ArrayList<String>();
                            newList.add(rightNode);
                            nodesInEachLevel4.add(newList);
                        } else {
                            nodesInEachLevel4.get(i-t+1).add(rightNode);
                        }
                    }

                }
                if(!nationSet.contains(rightNation)){
                    nationSet.add(rightNation);
                    if(nationsInEachLevel.size()<(i-t+2)){  // if don't have this level yet
                        List<String> newList = new ArrayList<String>();
                        newList.add(rightNation);
                        nationsInEachLevel.add(newList);
                    } else {
                        nationsInEachLevel.get(i-t+1).add(rightNation);
                    }
                }

                // (2) get line map which just save as-ip-size to calculate the sum
                lineMap.merge(key, BigInteger.valueOf(2).pow(asDetectItem.getPowSize()), BigInteger::add);
                if(!Objects.equals(leftNation, rightNation)){
                    String keyForNation = leftNation + "-" + rightNation;
                    lineMapForNation.merge(keyForNation,
                            BigInteger.valueOf(2).pow(asDetectItem.getPowSize()), BigInteger::add);
                }
            }
        }

        // ------------------------------ get node rank ------------------------------

        for(Map.Entry<String, BigInteger> lineItem : lineMap.entrySet()) {

        }

        // ------------------------------ get print txt ------------------------------
        // convert line map to list
        List<AsLineBean> lineList = new ArrayList<>();
        for (Map.Entry<String, BigInteger> entry : lineMap.entrySet()) {
            String [] beginEnd = entry.getKey().split("-");
            lineList.add(new AsLineBean(beginEnd[0], beginEnd[1], entry.getValue()));
        }
        List<AsLineBean> lineListForNation = new ArrayList<>();
        for (Map.Entry<String, BigInteger> entry : lineMapForNation.entrySet()) {
            String [] beginEnd = entry.getKey().split("-");
            lineListForNation.add(new AsLineBean(beginEnd[0], beginEnd[1], entry.getValue()));
        }
        // AS print-data out put
        outputForNetworkX(nodeSet, lineList, nodeSavePath, lineSavePath);
        outputForNetworkX(nationSet, lineListForNation, nodeSavePathForNation, lineSavePathForNation);

        // ------------------------------ get json result ------------------------------
        // get outline nodes and nations
        HashSet<String> outNodesSet = new HashSet<>();
        HashSet<String> outNationsSet = new HashSet<>();
        for (Map.Entry<String, AsNodeBean> entry : asLocationDictionary.entrySet()) {
            String asId = entry.getValue().getAsId();
            if (!nodeSet.contains(asId)) {
                outNodesSet.add(asId);
                String asNation = entry.getValue().getNationName();
                if (!nationSet.contains(asNation)) {
                    outNationsSet.add(asNation);
                }
            }
        }
        ArrayList<String> outNodesList = new ArrayList<>(outNodesSet);
        ArrayList<String> outNationsList = new ArrayList<>(outNationsSet);
        // get node count sum
        int countDDD = 0;
        for(List<String> al : nodesInEachLevel){
            countDDD += al.size();
        }
        double nationSum = outNationsList.size() + nationSet.size() + 0.0;
        double asSum = countDDD + outNodesList.size() + 0.0;
        // get country skip json result
        saveJson(nationsInEachLevel, outNationsList, nationSum, nationResultPath);
        // get as skip json result
        saveJson(nodesInEachLevel, outNodesList, asSum, asResultPath);
        saveJson(nodesInEachLevel2, new ArrayList<String>(), asSum, asResultPath2);
        saveJson(nodesInEachLevel4, new ArrayList<String>(), asSum, asResultPath4);
    }

    /**
     * output networkx format info
     * @param nodeSet
     * @param lineList
     */
    private void outputForNetworkX(HashSet<String> nodeSet, List<AsLineBean> lineList,
                                   String nodePath, String linePath) throws FileNotFoundException {

        SaveAsFile saveAsFile = new SaveAsFile();

        // save node list to txt
        List<String> nodeListForNetwrokX = new ArrayList<>(nodeSet);
        saveAsFile.saveAsTxt(nodeListForNetwrokX.toString(), nodePath);

        // save line list to txt
        List<List<String>> lineListForNetworkX = new ArrayList<>();
        for(AsLineBean alb: lineList){
            List<String> tmp = new ArrayList<>();
            tmp.add(alb.getBegin());
            tmp.add(alb.getEnd());
            tmp.add(alb.getIpSize().toString());
            lineListForNetworkX.add(tmp);
        }
        saveAsFile.saveAsTxt(lineListForNetworkX.toString(), linePath);
    }

    private void saveJson(List<List<String>> nodesInEachLevel, List<String> outNodesList, double nationSum, String asResultPath) {
        SaveAsFile saveAsFile = new SaveAsFile();
        int nodesLevel = nodesInEachLevel.size();
        JSONArray asSkipArray = new JSONArray();
        for(int index = 0; index < nodesLevel; index++) {

            int level = index;
            int asCount = nodesInEachLevel.get(level).size();
            double asPrb = asCount/nationSum;
            List<String> asList = nodesInEachLevel.get(level);

            Map<String, Long> item = new HashMap() {
                {
                    put("level", level);
                    put("count", asCount);
                    put("prob", String.format("%f",asPrb));
                    put("list", asList);
                }
            };
            asSkipArray.put(item);
        }
        // 不可达
        int asCount = outNodesList.size();
        Map<String, Long> item = new HashMap() {
            {
                put("level", -1);
                put("count", asCount);
                put("prob", String.format("%f",(asCount/nationSum)));
                put("list", outNodesList);
            }
        };
        asSkipArray.put(item);
        // save as json file
        saveAsFile.saveAsJson(asSkipArray.toString(), asResultPath);
    }


    public static void main(String[] args) throws FileNotFoundException, JSONException {
        String centralAs = "4134";
        String inPath = "/Users/mac/desktop/snapBGP.csv";
        String asInfoDictPath = "/Users/mac/desktop/as_country_with_geo.csv";
        int skipTimes = 100;

        KSkipMap kSkipMap = new KSkipMap();
        AsSkip asSkip = new AsSkip();
        // dict
        HashMap<String, AsNodeBean> asLocationDictionary = asSkip.ReadAsInformationAsMap(asInfoDictPath);
        // dataset
        List<AsDetectBean> asDetectDataset = asSkip.ReadAsDetectData(inPath, centralAs);

        String nodeSavePath = "/Users/mac/Desktop/as_node.txt";
        String lineSavePath = "/Users/mac/Desktop/as_line.txt";
        String nodeSavePathForNation = "/Users/mac/Desktop/nation_node.txt";
        String lineSavePathForNation = "/Users/mac/Desktop/nation_line.txt";
        String asJsonPath = "/Users/mac/desktop/as_level.json";
        String nationJsonPath = "/Users/mac/desktop/nation_level.json";

        String asJsonPath2 = "/Users/mac/desktop/as_level_2.json";
        String asJsonPath4 = "/Users/mac/desktop/as_level_4.json";


        kSkipMap.skipAnalysis(centralAs, asDetectDataset, asLocationDictionary, skipTimes, "45352", false,
                nodeSavePath, lineSavePath, nodeSavePathForNation, lineSavePathForNation, asJsonPath, nationJsonPath,
                asJsonPath2, asJsonPath4);


    }
}