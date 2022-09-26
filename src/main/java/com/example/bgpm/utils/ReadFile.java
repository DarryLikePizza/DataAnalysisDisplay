package com.example.bgpm.utils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class ReadFile {

    public String getStringFromFile(String pathString, boolean isNeedMake) throws IOException, JSONException {
        Path path = Paths.get(pathString);
        byte[] data = Files.readAllBytes(path);
        String orS = new String(data, StandardCharsets.UTF_8);

        if(!isNeedMake){
            return orS;
        }
        // 把科学计数法表示的转为字符串
        JSONObject jo = new JSONObject(orS);
        Iterator it = jo.keys();
        while (it.hasNext()){
            String i = (String) it.next();
            JSONObject abr = (JSONObject) jo.get(i);
            String s = String.valueOf(abr.get("weight"));
            if(s.contains("E")){
                String[] ss = s.split("E");
                abr.put("weight", ss[0].replace(".",""));
            }
            jo.put(i, abr);
        }

        String midS = jo.toString();
//        String newS = "[" + midS.substring(1, midS.length()-1) + "]";

        return midS;
    }

    public String[] readJson(String company, String monitor, String flag) throws IOException, JSONException {
//        String filePath = "src/main/resources/static/data/input/" + company + "/" + monitor + "/";
        // 打包路径
        String filePath = "data/company/" + company + "/" + monitor + "/";

        String f1 = "downstream_as_list.txt";
        String f2 = "inner_org_graph.txt";
        String f3 = "upstream_as_list.txt";

        if(flag.equals("1")){
            String[] sArray = new String[1];
            sArray[0] = getStringFromFile(filePath + f2, false);
            return sArray;
        }

        if(flag.equals("2")){
            String[] sArray = new String[2];
            sArray[0] = getStringFromFile(filePath + f1, true);
            sArray[1] = getStringFromFile(filePath + f3, true);
            return sArray;
        }

        String[] sArray = new String[3];
        sArray[1] = getStringFromFile(filePath + f2, false);
        sArray[0] = getStringFromFile(filePath + f1, true);
        sArray[2] = getStringFromFile(filePath + f3, true);

        return sArray;
    }

    public static void main(String[] args) throws IOException, JSONException {
        String[] ss = new ReadFile().readJson("Alibaba", "chicago", "2");

        for(String s : ss){
            System.out.println(s);
        }

    }
}
