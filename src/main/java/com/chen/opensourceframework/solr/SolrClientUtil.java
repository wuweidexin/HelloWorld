package com.chen.opensourceframework.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrClientUtil {
	static SolrClient solr = null;
	public static SolrClient getSolrClient(String urlString){
		if(null == solr) {
			
			solr = new HttpSolrClient.Builder(urlString).build();
			return solr;
		} else {
			return solr;
		}
	}
}
