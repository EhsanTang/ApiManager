package cn.crap.service.tool;
//package cn.crap.service;
//
//import cn.crap.dto.SearchDto;
//import cn.crap.inter.service.ISearchService;
//import cn.crap.utils.Page;
//import cn.crap.utils.SolrClientKit;
//import cn.crap.utils.Tools;
//import org.apache.log4j.Logger;
//import org.apache.lucene.document.StringField;
//import org.apache.lucene.document.TextField;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.springframework.stereotype.Service;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by yan on 16/6/18.
// */
//@Service("solrSearch")
//public class SolrSearchService implements ISearchService{
//    Logger logger = Logger.getLogger(getClass());
//    @Override
//    public List<SearchDto> search(String keyword, Page page) throws Exception {
//        List<SearchDto> searchDtos = new ArrayList<SearchDto>();
//        QueryResponse queryResponse =  SolrClientKit.query(keyword, page.getSize(), page.getStart(), true, null, null);
//        SolrDocumentList results = queryResponse.getResults();
//        logger.debug("use solr find result:" + queryResponse.getStatus() + ", and result num:" + queryResponse.getResults().getNumFound());
//        if(null == queryResponse || results.getNumFound() == 0 || queryResponse.getStatus() != 0){
//            return searchDtos;
//        }
//        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
//        for (int i = 0; i < results.getNumFound(); i++) {
//            SolrDocument entries = results.get(i);
//            entries.getFieldValueMap();
//            SearchDto sd = new SearchDto();
//            sd.setId(entries.get("id") != null ? (String)entries.get("id") : "0");
//            sd.setUrl(entries.get("url") != null ? (String) ((List)entries.get("url")).get(0) : "");
//            sd.setVersion(entries.get("version") != null ? (String) ((List)entries.get("version")).get(0) : "");
//            sd.setCreateTime(entries.get("createTime") != null ? (String) ((List)entries.get("createTime")).get(0) : "");
//            sd.setType(entries.get("type") != null ? (String) ((List)entries.get("type")).get(0) : "");
//            sd.setModuleName(entries.get("moduleName") != null ? (String) ((List)entries.get("moduleName")).get(0) : "");
//            if (highlighting.get(sd.getId()) != null && highlighting.get(sd.getId()).get("moduleName")!=null){
//                sd.setContent(highlighting.get(sd.getId()).get("moduleName").get(0));
//            }
//            sd.setContent(entries.get("content") != null ? (String) ((List)entries.get("content")).get(0) : "");
//            if (highlighting.get(sd.getId()) != null && highlighting.get(sd.getId()).get("content")!=null){
//                sd.setContent(highlighting.get(sd.getId()).get("content").get(0));
//            }
//            sd.setTitle(entries.get("title") != null ? (String) ((List)entries.get("title")).get(0) : "");
//            if (highlighting.get(sd.getId()) != null && highlighting.get(sd.getId()).get("title")!=null){
//                sd.setContent(highlighting.get(sd.getId()).get("title").get(0));
//            }
//            searchDtos.add(sd);
//        }
//        return searchDtos;
//    }
//
//    @Override
//    public boolean delete(SearchDto searchDto) throws IOException {
//        List<String> ids = new ArrayList<>();
//        if(searchDto != null && searchDto.getId() != null){
//            ids.add(searchDto.getId());
//        }
//        int result = SolrClientKit.delete(ids);
//        return (result > 0) ? true : false;
//    }
//
//    @Override
//    public boolean update(SearchDto searchDto) throws IOException {
//        Field[] fields = searchDto.getClass().getDeclaredFields();
//        Field.setAccessible(fields, true);
//        Map<String, Object> map = new HashMap<>();
//        getSearchDtoField(searchDto, fields, map);
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        list.add(map);
//        int result = SolrClientKit.update(list);
//        return (result > 0) ? true : false;
//    }
//
//    /**
//     * 遍历
//     * @param searchDto
//     * @param fields
//     * @param map
//     */
//    private void getSearchDtoField(SearchDto searchDto, Field[] fields, Map<String, Object> map) {
//        for (int i = 0; i < fields.length; i++){
//            try {
//                if (fields[i].get(searchDto) != null) {
//                    if(fields[i].getName().equals("content")){
//                        map.put(fields[i].getName(), Tools.removeHtml((String) fields[i].get(searchDto)));
//                    }else{
//                        map.put(fields[i].getName(), fields[i].get(searchDto));
//                    }
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public boolean add(SearchDto searchDto) throws IOException {
//        Field[] fields = searchDto.getClass().getDeclaredFields();
//        Field.setAccessible(fields, true);
//        Map<String, Object> map = new HashMap<>();
//        getSearchDtoField(searchDto, fields, map);
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        list.add(map);
//        int result = SolrClientKit.add(list);
//        return (result > 0) ? true : false;
//    }
//}
