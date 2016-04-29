package tudou.httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author tudou
 *
 */
public class HttpClientUltimate {
	private CloseableHttpClient httpclient = null;
	private CloseableHttpResponse response = null;
	private HttpGet httpget;
	private HttpEntity entity;
	private final String PASSWORD="123456";
	private final String URF8 = "UTF-8";
	private final String getUrl = "http://localhost:8080/order/jsps/userEdit.do?userId=30";
	private final String httpsgetUrl = "https://localhost:8443/order/jsps/userEdit.do?userId=30";
	private final String postBasicUrl = "http://localhost:8080/order/jsps/userEdit.do";

	@Test
	public void test() {
		List<String> urlParam = inputParam();
		get(getUrl);
		postForm(postBasicUrl, urlParam);
		ssl(httpsgetUrl);
	}
	// get方式提交
	public void get(String url) {
		httpclient = HttpClients.createDefault();
		httpget = new HttpGet(url);
		try {
			response = httpclient.execute(httpget);
			entity = response.getEntity();
			if (entity != null) {
				// 打印响应内容
				System.out.println(
						"Response content1: " + EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			// 关闭连接,释放资源
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void postForm(String basicUrl, List<String> params) {
		httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(basicUrl);
		// 创建参数队列
		List<NameValuePair> formparams = setParams(params);
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, URF8);
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("Response content: "
						+ EntityUtils.toString(entity, "UTF-8"));
			}
		} catch (Exception temp) {
			temp.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				if (httpclient != null) {
					response.close();
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param params:表示输入的一行，两个值中间用“,”隔开
	 * @return
	 */
	public List<NameValuePair> setParams(List<String> params) {
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		String[] paramLine = new String[2];
		for (String param : params) {
			if (param.split(",").length == 2) {
				paramLine = param.split(",");
				formParams.add(
						new BasicNameValuePair(paramLine[0], paramLine[1]));
			}
		}
		return formParams;
	}
	// 控制台按行输入
	private List<String> inputParam() {
		List<String> inputString = new ArrayList<String>();
		Scanner scanner = new Scanner(System.in);
		String inputTempLine = null;
		try {
			while ((inputTempLine = scanner.nextLine()) != null) {
				if (inputTempLine.equals("end"))
					break;
				inputString.add(inputTempLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		// 隔一行输入一行（scanner.nextline()）
		/*
		 * while(scanner.hasNextLine()){ if(scanner.nextLine().equals("end"))
		 * break; inputString.add(scanner.nextLine()); }
		 */
		return inputString;
	}

	private char[] getsslPass(String password){
		return password.toCharArray();
	}
	public void ssl(String url) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(new File("D:\\home\\tomcat.keystore"));
			try {
				// 加载keyStore
				trustStore.load(instream, getsslPass(PASSWORD));
			} catch (CertificateException e) {
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) {
				}
			}
			// 相信自己的CA和所有自签名的证书
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
					.build();
			// 只允许使用TLSv1协议
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			// 创建http请求(get方式)
			httpget = new HttpGet(url);
			System.out.println("executing request" + httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					System.out.println("Response content length: " + entity.getContentLength());
					System.out.println(EntityUtils.toString(entity));
					EntityUtils.consume(entity);
				}
			} finally {
				response.close();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
