package vit.mini.vitfaculty;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpManager{
	
	public static DefaultHttpClient client;
	
	static{
		client = new DefaultHttpClient();
	}
	
	public static HttpResponse execute(HttpGet get) throws ClientProtocolException, IOException{
		return client.execute(get);
	}
	
	public static HttpResponse execute(HttpPost post) throws ClientProtocolException, IOException{
		return client.execute(post);
	}
}
