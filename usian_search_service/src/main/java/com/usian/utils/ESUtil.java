package com.usian.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Title: ESUtil
 * @Description:
 * @Auther:
 * @Version: 1.0
 * @create 2021/5/19 14:55
 */
@Component
public class ESUtil {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 判断指定索引，是否存在
     * @param indexName
     * @return
     */
    public   boolean existsIndex(String indexName){
        IndicesClient indicesClient = restHighLevelClient.indices();
        GetIndexRequest getIndexRequest = new GetIndexRequest().indices(indexName);
        try {
            return  indicesClient.exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return false;
        }
    }


    /**
     *  创建索引，及类型及映射
     * @param shardNums
     * @param repliNums
     * @param indexName
     * @param typeName
     * @param mappingStr
     * @throws IOException
     */
    public void createIndex(Integer shardNums,Integer repliNums,String indexName,String typeName,String mappingStr) {
        //1. 1. 获取操作索引的客户端
        IndicesClient indicesClient = restHighLevelClient.indices();
        Settings settings = Settings.builder()
                .put("number_of_shards",shardNums)
                .put("number_of_replicas",repliNums)
                .build();

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName, settings);
        // 创建映射，type
        createIndexRequest.mapping(typeName, mappingStr, XContentType.JSON);
        try {
            indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
        }

    }


}
