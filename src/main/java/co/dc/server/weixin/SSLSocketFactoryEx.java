 package co.dc.server.weixin;
 
 import java.io.IOException;
 import java.net.Socket;
 import java.net.UnknownHostException;
 import java.security.KeyManagementException;
 import java.security.KeyStore;
 import java.security.KeyStoreException;
 import java.security.NoSuchAlgorithmException;
 import java.security.UnrecoverableKeyException;
 import java.security.cert.CertificateException;
 import java.security.cert.X509Certificate;
 import javax.net.ssl.SSLContext;
 import javax.net.ssl.TrustManager;
 import javax.net.ssl.X509TrustManager;
 import org.apache.http.HttpVersion;
 import org.apache.http.client.HttpClient;
 import org.apache.http.conn.ClientConnectionManager;
 import org.apache.http.conn.scheme.PlainSocketFactory;
 import org.apache.http.conn.scheme.Scheme;
 import org.apache.http.conn.scheme.SchemeRegistry;
 import org.apache.http.impl.client.DefaultHttpClient;
 import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
 import org.apache.http.params.BasicHttpParams;
 import org.apache.http.params.HttpParams;
 import org.apache.http.params.HttpProtocolParams;
 
 public class SSLSocketFactoryEx extends org.apache.http.conn.ssl.SSLSocketFactory
 {
   SSLContext sslContext = SSLContext.getInstance("TLS");
 
   public SSLSocketFactoryEx(KeyStore truststore)
     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
   {
     super(truststore);
 
     TrustManager tm = new X509TrustManager()
     {
       public X509Certificate[] getAcceptedIssuers()
       {
         return null;
       }
 
       public void checkClientTrusted(X509Certificate[] chain, String authType)
         throws CertificateException
       {
       }
 
       public void checkServerTrusted(X509Certificate[] chain, String authType)
         throws CertificateException
       {
       }
     };
     this.sslContext.init(null, new TrustManager[] { tm }, null);
   }
 
   public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
     throws IOException, UnknownHostException
   {
     return this.sslContext.getSocketFactory().createSocket(socket, host, port, 
       autoClose);
   }
 
   public Socket createSocket()
     throws IOException
   {
     return this.sslContext.getSocketFactory().createSocket();
   }
 
   public static HttpClient getNewHttpClient()
   {
     try
     {
       KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
       trustStore.load(null, null);
 
       org.apache.http.conn.ssl.SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
       sf.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
 
       HttpParams params = new BasicHttpParams();
       HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
       HttpProtocolParams.setContentCharset(params, "UTF-8");
 
       SchemeRegistry registry = new SchemeRegistry();
       registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
       registry.register(new Scheme("https", sf, 443));
 
       ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
 
       return new DefaultHttpClient(ccm, params); } catch (Exception e) {
     }
     return new DefaultHttpClient();
   }
 }

/* Location:           D:\迅雷下载\wxtest\WEB-INF\classes\
 * Qualified Name:     co.dc.SSLSocketFactoryEx
 * JD-Core Version:    0.6.0
 */