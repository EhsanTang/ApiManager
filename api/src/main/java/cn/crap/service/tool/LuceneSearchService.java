package cn.crap.service.tool;

import cn.crap.dto.SearchDto;
import cn.crap.service.ILuceneService;
import cn.crap.service.ISearchService;
import cn.crap.utils.*;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("luceneSearch")
public class LuceneSearchService implements ISearchService {
	protected Logger log = Logger.getLogger(getClass());

	@Autowired
	private SettingCache settingCache;
	@Autowired
	private StringCache stringCache;

	/**
	 * 在默认情况下使用 @Autowired 注释进行自动注入时，Spring 容器中匹配的候选 Bean 数目必须有且仅有一个
	 * @Autowired(required = false)，这等于告诉 Spring：在找不到匹配 Bean 时也不报错
	 */
	@SuppressWarnings("rawtypes")
	@Autowired(required=false)
	private cn.crap.service.ILuceneService[] luceneServices;


	@Override
	public List<SearchDto> search(String keyword, Page page) throws Exception {
		keyword = handleHref(keyword);
		if (MyString.isEmpty(keyword))
			return new ArrayList<SearchDto>();
		IndexReader reader = null;
		try {
			reader = DirectoryReader
					.open(FSDirectory.open(Paths.get(settingCache.get(ISetting.S_LUCENE_DIR).getValue())));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			String[] fields = { "id", "url", "contents", "modelName", "title","href"};
			QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
			Query query = parser.parse(keyword);

			// Sort sort = new Sort();
			// sortField = "relativeScore";
			// if(!sortField.equals(Constant.RELATIVE_SCORE)){
			// sort.setSort(new SortField(sortField,false)); // 默认为升序，修改为降序
			// }
			TopDocs topDocs = searcher.search(query, 1000);

			List<SearchDto> searchDtos = new ArrayList<SearchDto>();

			// ============== 准备高亮器
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Scorer scorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter, scorer);

			// 设置最长的 读取数量
			Fragmenter fragmenter = new SimpleFragmenter(300);
			highlighter.setTextFragmenter(fragmenter);

			// 取出当前页的数据
			page.setAllRow(topDocs.totalHits);
			if (page.getCurrentPage() > page.getTotalPage()){
			    return new ArrayList<>();
            }

			int end = Math.min(page.getStart() + page.getSize(), topDocs.totalHits);
			for (int i = page.getStart(); i < end; i++) {
				ScoreDoc scoreDoc = topDocs.scoreDocs[i];
				float relativeScore = scoreDoc.score;
				int docSn = scoreDoc.doc; // 文档内部编号
				Document doc = searcher.doc(docSn); // 根据编号取出相应的文档
				doc.add(new StringField("relativeScore", relativeScore + "", Field.Store.NO));

				addHighterField(highlighter, doc, "title");
				addHighterField(highlighter, doc, "contents");
				addHighterField(highlighter, doc, "moduleName");

				searchDtos.add(docToDto(doc));
			}
			return searchDtos;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO 消息时间需要修改为12小时
			stringCache.add(IConst.C_CACHE_ERROR_TIP, "Lucene搜索异常，请联系管理员查看日志，错误信息（消息将保留10分钟，请及时处理）：" + e.getMessage());
		} finally {
			if (reader != null)
				reader.close();
		}
		return new ArrayList<SearchDto>();
	}

	@Override
	public boolean delete(SearchDto searchDto) throws IOException {
		IndexWriter writer = null;
		try {
			IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
			conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(
					FSDirectory.open(Paths.get(settingCache.get(ISetting.S_LUCENE_DIR).getValue())), conf);
			writer.deleteDocuments(new Term("id", searchDto.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			stringCache.add(IConst.C_CACHE_ERROR_TIP, "Lucene删除异常，请联系管理员查看日志，错误信息（消息将保留10分钟，请及时处理）：" + e.getMessage());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		return true;
	}


	private static SearchDto docToDto(Document doc) {
		SearchDto dto = new SearchDto();
		// 高亮处理的搜索结果
		dto.setContent(doc.get("r_contents"));
		dto.setCreateTime(doc.get("createTime") == null? null : new Date(Long.parseLong(doc.get("createTime"))));
		// 恢复反斜杠
		dto.setUrl(unHandleHref(doc.get("url")));
		dto.setId(doc.get("id"));
		dto.setModuleName(doc.get("r_moduleName"));
		dto.setTitle(doc.get("r_title"));
		dto.setType(doc.get("type"));
		dto.setVersion(doc.get("version"));
		dto.setProjectId(doc.get("projectId"));

		return dto;
	}

	private static Document dtoToDoc(SearchDto dto) {
		Document doc = new Document();

		// Add the path of the file as a field named "path". Use a
		// field that is indexed (i.e. searchable), but don't tokenize
		// the field into separate words and don't index term frequency
		// or positional information:
		doc.add(new StringField("id", dto.getId(), Field.Store.YES));
		doc.add(new StringField("url", handleHref(dto.getUrl()), Field.Store.YES));
		doc.add(new StringField("version", dto.getVersion(), Field.Store.YES));
		doc.add(new StringField("createTime", dto.getCreateTime() == null ? System.currentTimeMillis() + "" : dto.getCreateTime().getTime() + "", Field.Store.YES));
		doc.add(new TextField("contents", Tools.removeHtml(dto.getContent()), Field.Store.YES));
		doc.add(new TextField("moduleName", dto.getModuleName(), Field.Store.YES));
		doc.add(new TextField("title", dto.getTitle(), Field.Store.YES));
		doc.add(new TextField("type", dto.getType(), Field.Store.YES));
		doc.add(new StringField("projectId", dto.getProjectId(), Field.Store.YES));
		// 将反斜杠替换
		doc.add(new TextField("href", handleHref(dto.getHref()) , Field.Store.YES));

		return doc;
	}

	/**
	 * 为某个属性设置高亮操作
	 *
	 * @param highlighter
	 *            高亮器
	 * @param doc
	 *            文本
	 * @param fieldName
	 *            属性名字
	 * @throws Exception
	 */
	private static void addHighterField(Highlighter highlighter, Document doc, String fieldName) throws Exception {
		String hc = doc.get(fieldName);
		if (hc != null)
			hc = highlighter.getBestFragment(new StandardAnalyzer(), fieldName, doc.get(fieldName));

		if (hc == null) {
			String content = doc.get(fieldName);
			if (content != null) {
				hc = content.substring(0, Math.min(50, content.length()));
			} else {
				hc = "";
			}
		}
		doc.add(new StringField("r_" + fieldName, hc, Field.Store.YES));
	}

	@Override
	public boolean add(SearchDto searchDto){
		IndexWriter writer = null;
		try {
			if(!searchDto.isNeedCreateIndex()){
				delete(searchDto);
				return true;
			}

			IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
			conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(
					FSDirectory.open(Paths.get(settingCache.get(ISetting.S_LUCENE_DIR).getValue())), conf);
			writer.updateDocument(new Term("id", searchDto.getId()), dtoToDoc(searchDto));
		} catch (Exception e) {
			e.printStackTrace();
			stringCache.add(IConst.C_CACHE_ERROR_TIP, "Lucene异常，请联系管理员查看日志，错误信息（消息将保留10分钟，请及时处理）：" + e.getMessage());
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	@Override
	public boolean update(SearchDto searchDto) throws IOException {
		return add(searchDto);
	}

	private boolean isRebuild = false;
	/**
	 * 重建系统索引
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean rebuild(){
		if(isRebuild){
			return true;
		}
		synchronized (LuceneSearchService.this) {
			try{
				isRebuild = true;
				File file = new File(settingCache.get(ISetting.S_LUCENE_DIR).getValue());
				File[] tempList = file.listFiles();
			    for (int i = 0; i < tempList.length; i++) {
			    	tempList[i].delete();
			    }

			    for(ILuceneService service:luceneServices){
					log.error("正在创建索引：" + service.getLuceneType());
			    	int i = 0;
			    	List<SearchDto> dtos= service.getAll();
			    	for (SearchDto dto : dtos) {
			    		i++;
						stringCache.add(IConst.C_CACHE_ERROR_TIP, "当前正在创建【"+service.getLuceneType()+"】索引，共"+dtos.size()+"，正在创建第"+i+"条记录");
						// 避免占用太大的系统资源
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						add(dto);
					}
                    log.error("建索引创建完成：" + service.getLuceneType());
			    }
			    stringCache.add(IConst.C_CACHE_ERROR_TIP,"重建索引成功！");
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				isRebuild = false;
			}

		}
	    return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean rebuildByProjectId(String projectId){
		for (ILuceneService service : luceneServices) {
			List<SearchDto> dtos = service.getAllByProjectId(projectId);
			for (SearchDto dto : dtos) {
				// 避免占用太大的系统资源
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				add(dto);
			}
		}
		return true;
	}

	public static String handleHref(String href){
		if(href == null)
			return "";
		// + – && || ! ( ) { } [ ] ^ ” ~ * ? : /
		return href.replaceAll("\\/", "CA_FXG").replaceAll("\\+", "CA_ADD").replaceAll("\\-", "CA_DES").replaceAll("\\&", "CA_AND")
				.replaceAll("\\|", "CA_HZ").replaceAll("\\{", "CA_DKHS").replaceAll("\\}", "CA_DKHE").replaceAll("\\?", "CA_WH")
				.replaceAll("\\*", "CA_XH").replaceAll("\\#", "CA_JH").replaceAll("\\:", "CA_MH").replaceAll("\\.", "CA_DH");
	}
	public static String unHandleHref(String href){
		if(href == null)
			return "";
		// + – && || ! ( ) { } [ ] ^ ” ~ * ? : /
		return href.replaceAll("CA_FXG", "\\/").replaceAll( "CA_ADD","\\+").replaceAll( "CA_DES", "\\-").replaceAll( "CA_AND","\\&")
				.replaceAll("CA_HZ", "\\|").replaceAll("CA_DKHS", "\\{").replaceAll("CA_DKHE","\\}").replaceAll( "CA_WH","\\?")
				.replaceAll("CA_XH", "\\*").replaceAll("CA_JH", "\\#").replaceAll("CA_MH", "\\:").replaceAll("CA_DH", "\\.");
	}
}
