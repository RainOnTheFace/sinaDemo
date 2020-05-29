package com.example.demo.utils;

import com.example.demo.exception.SinaDemoException;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyb
 * Date: 2018/11/27
 * Time: 15:11
 * Description:
 **/
public class HttpClient {

    private static Logger log = LoggerFactory.getLogger(HttpClient.class);

    private static final int DEFAULT_SOCKET_TIMEOUT = 60000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 60000;
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 60000;

    public static String post(String url) {
        return post(url, null);
    }

    public static String post(String url, Map<String, String> param) {
        return post(url, param,
                RequestConfig.custom().setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).setConnectTimeout(HttpClient.DEFAULT_CONNECT_TIMEOUT)
                        .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT).build());
    }

    public static String post(String url, Map<String, String> param, RequestConfig requestConfig) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseString = null;
        if (null != param && param.size() > 0) {
            Set<String> keySet = param.keySet();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, param.get(key)));
            }
            try {
                log.info("set utf-8 form entity to httppost");
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8.name()));
            } catch (UnsupportedEncodingException e) {
                log.error("http请求失败", e);
                throw new SinaDemoException("http请求失败");
            }
        }
        try {
            httpClient = getHttpClient();
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            log.error("HttpClient post error", e);
            throw new SinaDemoException("http请求失败");
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("HttpClient post CloseableHttpClient close error", e);
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("HttpClient post CloseableHttpResponse close error", e);
                }
            }
        }
        return responseString;
    }

    public static String postJson(String url) {
        return postJson(url, null);
    }

    public static String postJson(String url, String jsonEntity) {
        return postJson(url, jsonEntity,
                RequestConfig.custom().setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).setConnectTimeout(HttpClient.DEFAULT_CONNECT_TIMEOUT)
                        .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT).build());
    }

    public static String postJson(String url, String jsonEntity, RequestConfig requestConfig) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.addHeader("Connection", "Keep-Alive");
        httpPost.addHeader("user-agent", "MEICAI/WMC");
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseString = null;
        if (jsonEntity != null) {
            httpPost.setEntity(new StringEntity(jsonEntity, Consts.UTF_8.name()));
        }
        try {
            httpClient = getHttpClient();
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            log.error("HttpClient postJson error", e);
            throw new SinaDemoException("http请求失败");
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("HttpClient postJson CloseableHttpClient close error", e);
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("HttpClient postJson CloseableHttpResponse close error", e);
                }
            }
        }
        return responseString;
    }


    public static String get(String url){
        String result = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            //用get方法发送http请求
            HttpGet get = new HttpGet(url);
            //发送get请求
            httpClient = getHttpClient();
            httpResponse = httpClient.execute(get);
            //response实体
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity){
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            log.error("HttpClient doGet error", e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("HttpClient do get httpClient close error", e);
                }
            }
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    log.error("HttpClient do get httpResponse close error", e);
                }
            }
        }
        return result;
    }

    private static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = null;
        SSLHelper sslHelper = new SSLHelper();
        PoolingHttpClientConnectionManager pcm = sslHelper.GetPcm();
        httpClient = HttpClients.custom().setConnectionManager(pcm).setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT).build()).build();
        return httpClient;
    }

}
