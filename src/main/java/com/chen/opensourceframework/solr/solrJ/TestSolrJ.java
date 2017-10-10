package com.chen.opensourceframework.solr.solrJ;

import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.chen.opensourceframework.solr.SolrClientUtil;


public class TestSolrJ {
	SolrClient solr = null;
	public TestSolrJ(){
		String urlString = "http://localhost:8983/solr/test";
		solr = SolrClientUtil.getSolrClient(urlString);
	}
	/**
	 * 查询
	 * @param phone
	 */
	public void solrQuery(String phone){
		
		//如果部署多个ZooKeeper的时候使用下面的情况
		/*String zkHostString = "zkServerA:2181,zkServerB:2181,zkServerC:2181/solr";
		SolrClient solr = new CloudSolrClient.Builder().withZkHost(zkHostString).build();*/
//		solr.setParser(new XMLResponseParser());
		SolrQuery query = new SolrQuery();
//		query.setQuery(mQueryString);
		
//		query.setRequestHandler("/spellCheckCompRH");
//		query.set("fl", "category,title,price");
		query.setFields("SEX", "PHONE", "NAME");
		query.set("q", "PHONE:"+phone);
		QueryResponse response;
		try {
			response = solr.query(query);
			SolrDocumentList list = response.getResults();
			Iterator<SolrDocument> ita = list.iterator();
			while(ita.hasNext()) {
				SolrDocument doc = ita.next();
				StringBuffer sbf = new StringBuffer();
				for(String key : doc.keySet()) {
					sbf.append(key + " = " + doc.get(key));
					sbf.append("  ,");
				}
				System.out.println(sbf.toString());
			}
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 添加索引的数据
	 */
	public void addDoc(){
		SolrInputDocument document = new SolrInputDocument();
		document.addField("ID", UUID.randomUUID().toString());
		document.addField("SEX", "1");
		document.addField("PHONE", "18028752937");
		document.addField("NAME", "陈俊全");
		try {
			UpdateResponse response = solr.add(document);
			// Remember to commit your changes!
			solr.commit();
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		TestSolrJ tsj = new TestSolrJ();
		tsj.solrQuery("18028752937");
//		tsj.addDoc();
	}
}
