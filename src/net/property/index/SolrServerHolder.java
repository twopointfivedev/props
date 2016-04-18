/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.property.index;

import java.io.IOException;

import net.property.utils.GlobalConstants;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;


/**
 * stream update
 * @author Saurabh
 */
public class SolrServerHolder {
	private static SolrClient concurrentSolrServer = null;
    private static HttpSolrClient solrServer = null;
    private static DefaultHttpClient client = null;

    private static synchronized void initHttpClient() {
    	try {
	    	PoolingClientConnectionManager connManager = new PoolingClientConnectionManager();
	    	connManager.setDefaultMaxPerRoute(20); // Allow upto 20 connections for solr server
	    	connManager.setMaxTotal(1000);
	    	client = new DefaultHttpClient(connManager);
    	} catch (Error e) {
    		e.getMessage();
    	}
    }
    
    private static synchronized void initConcurrentSolrServer() throws Exception {    	
    	if(client == null)
    		initHttpClient();    	
    	
    	//concurrentSolrServer = new ConcurrentUpdateSolrClient("http://localhost:8980/solr/prop", client, 5000, 2);
    	try{
    	
    	 concurrentSolrServer = new HttpSolrClient("http://localhost:8980/solr/prop");
    	}
    	catch(Throwable e){
    		e.printStackTrace();
    	}
    }
    
    private static synchronized void initNormalSolrServer() throws Exception {
    	if(client == null)
    		initHttpClient();
    	
    	//solrServer = new HttpSolrClient("http://localhost:8980/solr/prop", client);
    //	solrServer = new HttpSolrClient(GlobalConstants.SOLR_URL);
    	solrServer.setAllowCompression(true);    	
    }
    
    public static  SolrClient getConcurrentSolrServer() throws Exception {
    	if(concurrentSolrServer == null)
    		initConcurrentSolrServer();
    	return concurrentSolrServer;
    }

    public static HttpSolrClient getNormalSolrServer() throws Exception {
        if(solrServer == null) 
        	initNormalSolrServer();
        return solrServer;
    }

}
