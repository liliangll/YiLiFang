package cn.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;

/**
 * 商品搜索dao
 * <p>Title: SearchDao</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	
	/**
	 * 根据查询条件查询索引库
	 * <p>Title: search</p>
	 * <p>Description: </p>
	 * @param query
	 * @return
	 * @throws SolrServerException 
	 */
	public SearchResult search(SolrQuery query) throws SolrServerException
	{
		//根据query查询索引库
		QueryResponse queryResponse=solrServer.query(query,METHOD.POST);
		//取查询结果
		SolrDocumentList solrDocumentList=queryResponse.getResults();
		//取查询结果总记录数，
		long numFound = solrDocumentList.getNumFound();
		SearchResult result=new SearchResult();
		result.setRecourdCount(numFound);
		//取商品列表，需要取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		List<SearchItem> itemList=new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
				SearchItem item=new SearchItem();
				item.setId((String) solrDocument.get("id"));
				item.setTitle((String) solrDocument.get("item_title"));
				item.setImage((String) solrDocument.get("item_image"));
				item.setCategory_name((String) solrDocument.get("item_category_name"));
				item.setPrice((long) solrDocument.get("item_price"));
				item.setSell_point((String) solrDocument.get("item_sell_point"));
				//取高亮显示
				List<String> list=highlighting.get(solrDocument.get("id")).get("item_title");
				String title="";
				if (list!=null&&list.size()>0) {
					title=list.get(0);
				}else {
					title=(String) solrDocument.get("item_title");
				}
				item.setTitle(title);
				//添加商品到列表
				itemList.add(item);
				
		}
		result.setItemList(itemList);
		//返回结果
		return result;
		
	}
}
