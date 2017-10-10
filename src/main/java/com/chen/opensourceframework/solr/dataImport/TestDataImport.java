package com.chen.opensourceframework.solr.dataImport;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;

import com.chen.javase.http.HttpUtils;


public class TestDataImport {
	
	public void deltaImport(){
		//http://<host>:<port>/ solr/ <collection_name>/ dataimport? command=delta-import
		String urlString = "http://localhost:8983/solr/test/dataimport?command=delta-import";
		try {
			String str = HttpUtils.net(urlString, null, "GET", null, null);
			System.out.println(str);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		TestDataImport tdi = new TestDataImport();
		tdi.deltaImport();
	}
}
