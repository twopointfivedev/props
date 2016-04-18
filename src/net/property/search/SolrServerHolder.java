

package net.property.search;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import net.property.utils.GlobalConstants;

/**
 */
@Deprecated
public class SolrServerHolder {
    private static HttpSolrClient solrServer = null;
  //  private static DefaultHttpClient client = null;

    private static synchronized void initHttpClient() {
    	PoolingClientConnectionManager connManager = new PoolingClientConnectionManager();
    	connManager.setDefaultMaxPerRoute(20); // Allow upto 20 connections for solr server
    	connManager.setMaxTotal(1000);
   // 	client = new DefaultHttpClient(connManager);
    }
    private static synchronized void initNormalSolrServer() throws Exception {
    /*	if(client == null)
    		initHttpClient();*/
    
//    	client.getCredentialsProvider().setCredentials(
//                new AuthScope(GlobalConstants.SOLR_HOST, 
//                		GlobalConstants.SOLR_PORT, AuthScope.ANY_REALM),
//                new UsernamePasswordCredentials(GlobalConstants.SOLR_USER, GlobalConstants.SOLR_PASSWORD));
    	
//    	solrServer = new HttpSolrServer("http://" + GlobalConstants.SOLR_HOST + ":" + GlobalConstants.SOLR_PORT 
//    			+ GlobalConstants.SOLR_PATH, client);
//    	solrServer = new HttpSolrServer("http://" + GlobalConstants.SOLR_HOST + ":" + GlobalConstants.SOLR_PORT 
//    			+ GlobalConstants.SOLR_PATH);
    	solrServer.setAllowCompression(true);
    }

    public static HttpSolrClient getNormalSolrServer() throws Exception {
        if(solrServer == null){ 
        	initNormalSolrServer();
        }
        return solrServer;
    }

}
