package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.usian.constant.Constant;
import com.usian.mapper.SearchItemMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.pojo.TbItem;
import com.usian.util.JsonUtils;
import com.usian.utils.ESUtil;
import com.usian.vo.SearchItem;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ESUtil esUtil;

    @Autowired
    private TbItemMapper tbItemMapper;


    public void importAll() {
        // 查一部（一页），导入导一部分
        int pageNum = 1;
        while (true) {
            //1. 查询mysql
            PageHelper.startPage(pageNum, 1000);
            List<SearchItem> itemESVOS = searchItemMapper.queryAll4ES();

            if (itemESVOS == null || itemESVOS.size() == 0) {
                break;
            }
            //2. 存入到es  操作es
            //2.1 判断索引是否存在，不存在创建结构
            if (!esUtil.existsIndex(Constant.INDEXNAME)) {
                esUtil.createIndex(1, 0, Constant.INDEXNAME, Constant.TYPES, "{\n" +
                        "  \"_source\": {\n" +
                        "    \"excludes\": [\n" +
                        "      \"item_desc\"\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"properties\": {\n" +
                        "    \"id\": {\n" +
                        "       \"type\": \"keyword\",\n" +
                        "       \"index\": false\n" +
                        "    },\n" +
                        "    \"item_title\": {\n" +
                        "      \"type\": \"text\",\n" +
                        "      \"analyzer\": \"ik_max_word\",\n" +
                        "      \"search_analyzer\": \"ik_smart\"\n" +
                        "    },\n" +
                        "    \"item_sell_point\": {\n" +
                        "      \"type\": \"text\",\n" +
                        "      \"analyzer\": \"ik_max_word\",\n" +
                        "      \"search_analyzer\": \"ik_smart\"\n" +
                        "    },\n" +
                        "    \"item_price\": {\n" +
                        "      \"type\": \"float\"\n" +
                        "    },\n" +
                        "    \"item_image\": {\n" +
                        "      \"type\": \"text\",\n" +
                        "      \"index\": false\n" +
                        "    },\n" +
                        "    \"item_category_name\": {\n" +
                        "      \"type\": \"text\",\n" +
                        "      \"analyzer\": \"ik_max_word\",\n" +
                        "      \"search_analyzer\": \"ik_smart\"\n" +
                        "    },\n" +
                        "    \"item_desc\": {\n" +
                        "      \"type\": \"text\",\n" +
                        "      \"analyzer\": \"ik_max_word\",\n" +
                        "      \"search_analyzer\": \"ik_smart\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}");
            }
            //2.1 ，将数据存入es中

            BulkRequest bulkRequest = new BulkRequest();
            itemESVOS.forEach(e -> { // 商品对象 ---》商品json串
                bulkRequest.add(new IndexRequest(Constant.INDEXNAME, Constant.TYPES).source(JsonUtils.objectToJson(e),
                        XContentType.JSON));
            });
            try {
                restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 如果查询不到了，代表已经全部导入成功了，就结束该循环

            pageNum++;
        }


    }


    /**
     * 搜索商品信息
     *
     * @param q
     * @return
     */
    public List<SearchItem> search(String q) {
        SearchRequest searchRequest = new SearchRequest("usian");
        searchRequest.types("item");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //1、查询名字、描述、卖点、类别包括“q”的商品
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(q, "item_title", "item_desc", "item_sell_point"));
        //3、高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        List<HighlightBuilder.Field> fields = highlightBuilder.fields();
        fields.add(new HighlightBuilder.Field("item_title"));
        fields.add(new HighlightBuilder.Field("item_sell_point"));
        fields.add(new HighlightBuilder.Field("item_desc"));
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);
        List<SearchItem> searchItemList = new ArrayList<>();
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            //4、返回查询结果

            for (SearchHit hit : hits) {

                SearchItem searchItem = JsonUtils.jsonToPojo(hit.getSourceAsString(), SearchItem.class);
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if (highlightFields != null && highlightFields.size() > 0) {
                    HighlightField highlightField = highlightFields.get("item_title");
                    if (highlightField != null) {
                        Text[] fragments = highlightField.getFragments();
                        searchItem.setItemTitle(fragments[0].toString());
                    }
                    highlightFields.get("item_sell_point");
                    if (highlightField != null) {
                        Text[] fragments = highlightField.getFragments();
                        searchItem.setItemTitle(fragments[0].toString());
                    }
                    highlightFields.get("item_desc");
                    if (highlightField != null) {
                        Text[] fragments = highlightField.getFragments();
                        searchItem.setItemTitle(fragments[0].toString());
                    }

                }
                searchItemList.add(searchItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchItemList;
    }


    public TbItem selectItemInfo(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }
}

