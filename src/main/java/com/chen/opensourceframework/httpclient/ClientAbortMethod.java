package com.chen.opensourceframework.httpclient;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates how to abort an HTTP method before its normal completion.
 */
public class ClientAbortMethod {

    public final static void main(String[] args) throws Exception{
    	testGet();
    }
    
    private static void testGet()  throws Exception{
    	// TODO Auto-generated method stub
    	CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://httpbin.org/get");

            System.out.println("Executing request " + httpget.getURI());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                // Do not feel like reading the response body
                // Call abort on the request object
                httpget.abort();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
	}
    
    private static void testPost()  throws Exception{
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	 HttpUriRequest login = RequestBuilder.post()
                 .setUri(new URI("https://someportal/"))
                 .addParameter("IDToken1", "username")
                 .addParameter("IDToken2", "password")
                 .build();
         CloseableHttpResponse response2 = httpclient.execute(login);
         try {
             HttpEntity entity = response2.getEntity();
             System.out.println("Login form get: " + response2.getStatusLine());
             EntityUtils.consume(entity);
             System.out.println("Post logon cookies:");
         } finally {
             response2.close();
         }
	}
}
