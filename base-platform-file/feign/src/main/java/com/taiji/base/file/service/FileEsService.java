package com.taiji.base.file.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.file.vo.EsFileVo;
import com.taiji.base.file.vo.EsJsonHitsListVo;
import com.taiji.base.file.vo.EsJsonVo;
import org.apache.http.Consts;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ContentType;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunyi
 * @create: 2019-02-25
 **/
@Service
public class FileEsService {

    private static final  String ADD_URL = "http://192.168.57.96:32222/bdpes/esservice/v1.0/doc/doc_add";
    private static final  String EDIT_URL = "http://192.168.57.96:32222/bdpes/esservice/v1.0/doc/doc_edit";
    private static final  String SELECT_URL = "http://192.168.57.96:32222/bdpes/esservice/v1.0/doc/doc_query";
    /**
     * @param esFileVo esFileVo试题VO  组织数据使用
     * @param isAddOrUpdate 需要执行什么操作  0:新增  1：修改
     * @throws Exception
     */
    public String updateOrAdd(EsFileVo esFileVo,int isAddOrUpdate) throws ClientProtocolException, IOException{
        String objJsonString = esjsonString(esFileVo,esObjJsonString(esFileVo),isAddOrUpdate);
        if(isAddOrUpdate == 0){
            return esService(objJsonString,ADD_URL);
        }else if(isAddOrUpdate == 1) {
            return esService(objJsonString,EDIT_URL);
        }else {
            return "";
        }

    }

    /**
     * 根据vo组装JSON格式数据（内层）
     * @param vo
     */
    private String esObjJsonString(EsFileVo vo) {
        String objJsonString  = "{" +
                "\"yjId\":\""+vo.getId()+"\"," +
                "\"title\":\""+vo.getTitle()+"\"," +
                "\"createBy\":\""+vo.getAccount()+"\"," +
                "\"createTime\":\""+vo.getCreateTime()+"\"," +
                "\"content\":\""+vo.getContent()+"\"," +
                "\"type\":\"1\"," +
                "\"status\":\"1\"}";
        return objJsonString;
    }

    /**
     * 根据vo组装  JSON格式数据（外层）
     * @param vo
     * @return
     */
    private String esjsonString(
            EsFileVo vo, String objJsonString,int isAddOrUpdate)
    {

        String jsonStr = "{" +
                "\"indexName\":\""+vo.getIndexName()+"\"," +
                "\"typeName\":\""+vo.getTypeName()+"\"," +
                "\"docId\":\""+vo.getId()+"\"," +
                "\"objJsonString\":"+objJsonString;
        if(isAddOrUpdate == 1){
            jsonStr+= ",\"docAsInsert\":\"true\"";
        }
        jsonStr += "}";
        return jsonStr;
    }

    /**
     * 调用服务
     * @param jsonStr 组装的json数据
     */
    private String esService(String jsonStr,String Url) throws ClientProtocolException, IOException {
        String result = Request.Post(Url)
                .useExpectContinue()
                .version(HttpVersion.HTTP_1_1)
                .bodyString(jsonStr,ContentType.create(
                        "application/json", Consts.UTF_8))
                .connectTimeout(5000)
                .socketTimeout(10000)
                .execute().returnContent().toString();
        return result;
    }

    /**
     * 根据业务实体ID进行删除
     * @param docId
     */


    public String delete(String docId) throws ClientProtocolException, IOException{
        String url = "http://192.168.57.96:32222/bdpes/esservice/v1.0/doc/doc_del/neimengyqrb/neimengyqrb/"+docId;
        String result = Request.Get(url)
                .connectTimeout(5000)
                .socketTimeout(10000)
                .execute().returnContent().toString();
        return result;
    }
    /**
     * 根据vo先拼接sql语句，在调用大数据服务进行查询
     * @param vo
     * @return 返回json格式字符串
     */
    public String esSelect(EsFileVo vo) throws ClientProtocolException, IOException{
        String jsonSql = esJsonSqlString(vo);
        String result = Request.Post(SELECT_URL)
                .useExpectContinue()
                .version(HttpVersion.HTTP_1_1)
                .bodyString(jsonSql,ContentType.create(
                        "application/json", Consts.UTF_8))
                .connectTimeout(5000)
                .socketTimeout(10000)
                .execute().returnContent().toString();
        return result;
    }
    /**
     * 根据vo组装  查询sql  提供给大数据服务查询
     * @param vo
     * @return
     */
    private String esJsonSqlString(EsFileVo vo) {
        String jsonStr = "{" +
                "\"sql\":\"select yjid from "+vo.getIndexName()+" where 1=1 and content like '"+vo.getTitle()+
                "' or title like '"+vo.getTitle()+"' limit "+vo.getPage()+","+vo.getSize();
        jsonStr += "\"}";
        return jsonStr;
    }

    /**
     * 根据查询返回的json字符串，转换为对象中所需的所有业务实体ID串
     * @param JsonString
     * @return ids
     */
    public List<String> esJsonStrToIds(String JsonString) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(JsonString);
        String obj = root.path("obj").asText();
        EsJsonVo esJsonVo = mapper.readValue(obj, EsJsonVo.class);
        //获得IDS  并根据该id串查询对应数据
        List<EsJsonHitsListVo> hits = esJsonVo.getHits().getHits();
        List<String> ids = new ArrayList<>();
        if(!CollectionUtils.isEmpty(hits)){
            for (EsJsonHitsListVo hitsVo:hits) {
                ids.add(hitsVo.get_id());
            }
        }
        return ids;
    }


}