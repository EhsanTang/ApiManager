package cn.crap.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import cn.crap.framework.SpringContextHolder;
import cn.crap.inter.service.ICacheService;
import cn.crap.service.CacheService;

/**
 * @author yan
 */
public class SolrClientKit {
	static Logger logger = Logger.getLogger(SolrClientKit.class);
	private static SolrClient httpSolrClinet  = null;
	private static SolrClient concurrentUpdateSolrClient = null;
	
	/**
	 * 查询时优先使用此对象
	 * @return
	 */
	public static SolrClient getHttpSolrCientInstance(){
		if(httpSolrClinet == null){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			return new HttpSolrClient(cacheService.getSetting(Const.SOLR_URL).getValue());
		}
		return httpSolrClinet;
	}
	
	/**
	 * 批量更新时，优先使用此对象
	 * Although any SolrClient request can be made with this implementation,
	 * it is only recommended to use the ConcurrentUpdateSolrClient for update requests.
	 * @return
	 */
	public static SolrClient getConcurrentUpdateSolrClientInstance(){
		if(concurrentUpdateSolrClient == null){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			return new ConcurrentUpdateSolrClient(cacheService.getSetting(Const.SOLR_URL).getValue(), Integer.parseInt(cacheService.getSetting(Const.SOLR_QUEUESIZE).getValue()), Integer.parseInt(cacheService.getSetting(Const.SOLR_THREADCOUNT).getValue()));
		}
		return concurrentUpdateSolrClient;
	}
	
	/**
	 * 批量添加索引数据
	 * @param documentlist
	 * @return
	 */
	public static int add(List<Map<String, Object>> documentlist){
		if(documentlist == null || documentlist.size() == 0){
			logger.debug("input list size: 0");
			return 0;
		}
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (Map<String, Object> map : documentlist) {
			SolrInputDocument document = new SolrInputDocument();
			Set<String> keySets = map.keySet();
			for (String key : keySets) {
				document.addField(key, map.get(key));
			}
			docs.add(document);
		}
		try {
			getHttpSolrCientInstance().add(docs);
			getHttpSolrCientInstance().optimize();
			UpdateResponse res = getHttpSolrCientInstance().commit();
			if(res.getStatus() == 0){
				return docs.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 查询索引
	 * @param queryParam
	 * @return
	 */
	public static QueryResponse query(String queryParam, Integer count, Integer start, Boolean hl, String sortfield, String sort, String ...fields){
		SolrClient client = getHttpSolrCientInstance();
		SolrQuery query = new SolrQuery();
		//query.set("wt", "json");
		if(hl != null && hl){
			query.setHighlight(true);
			query.addHighlightField("content");
            query.addHighlightField("title");
            query.addHighlightField("moduleName");
			query.setHighlightSimplePre("<font color='red'>");
			query.setHighlightSimplePost("</font>");
		}
		//设置需要返回的列
		query.setFields(fields);
		if(start == null){
			start = -1;
		}
		if(count == null){
			count = 0;
		}
		if(count > 0 & start == -1){
			start = 0;
		}
		if(start >= 0){
			query.setStart(start);
			query.setRows(start + count);
		}
		if(sort != null && sort.length() > 0 && sortfield != null && sortfield.length() > 0){
			ORDER order = ORDER.asc;
			if(sort.equals("desc")){
				order = ORDER.desc;
			}
			query.setSort(sort, order);
		}
		
		query.setQuery(queryParam);
		try {
			QueryResponse resp = client.query(query);
			client.close();
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 更新索引，更新策略为先删除原来索引，然后再重新加入需要更新的索引
	 */
	public static int update(List<Map<String, Object>> documentlist){
		SolrClient client = getConcurrentUpdateSolrClientInstance();
		if(documentlist == null || documentlist.size() == 0){
			logger.debug("input list size: 0");
			return 0;
		}
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		List<String> ids = new ArrayList<String>();
 		for (Map<String, Object> map : documentlist) {
			SolrInputDocument document = new SolrInputDocument();
			Set<String> keySets = map.keySet();
			for (String key : keySets) {
				document.addField(key, map.get(key));
				if(key.equals("id")){
					docs.add(document);
				}
			}
			if(keySets.contains("id")){
				ids.add(String.valueOf(map.get("id")));
			}else{
				logger.warn("no primary key");
			}
		}
		try {
			client.deleteById(ids);
			client.commit();
			client.add(docs);
			client.optimize();
			UpdateResponse res = client.commit();
			client.close();
			if(res.getStatus() == 0){
				return docs.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除索引
	 * @param ids
	 * @return
	 */
	public static int delete(List<String> ids){
		SolrClient client = getHttpSolrCientInstance();
		try {
			//先delete需要更新的id
			client.deleteById(ids);
			UpdateResponse res = client.commit();
			client.close();
			if(res.getStatus() == 0){
				return ids.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
