package com.example.demo.engine;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Jian Chen
 * @create: 2023-03
 **/
public class Engine {

    public static void main(String[] args) {
        final Engine engine = new Engine();
        try {
            engine.engine1();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void engine1() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();

        String a = "return {'type':'removeRepeat','nodes':" +
                "       [{'type':'downOrgManager','orgId':outCompanyId,'orgNames':'学生事务部'}," +
                "       {'type':'downOrgManager','orgId':outCompanyId,'orgNames':'教学中心'}," +
                "       {'type':'orgDown','orgId':outCompanyId,'positionName':'总经理'}," +
                "       {'type':'downOrgManager','orgId':inCompanyId,'orgNames':'学生事务部'}," +
                "       {'type':'downOrgManager','orgId':inCompanyId,'orgNames':'教学中心'}," +
                "       {'type':'orgDown','orgId':inCompanyId,'positionName':'总经理'}," +
                "       {'type':'orgDown','orgId':'org10002','positionName':'董事长'}]};";
        final ScriptEngine javascript = manager.getEngineByName("javascript");
        javascript.put("outCompanyId","12");
        javascript.put("inCompanyId","123");
        javascript.eval("var obj = (function(){" + a + "})();");
        final ScriptObjectMirror obj = (ScriptObjectMirror)javascript.get("obj");
        final String[] nodes1 = (String[]) obj.get("nodes");
        final List<String> collect = Arrays.stream(nodes1).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));
    }
}
