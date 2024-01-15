package io.ganguo.library.core.http.util;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.ganguo.library.core.http.HttpConstants;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * Created by Tony on 11/9/14.
 */
public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpConstants.TAG);

    /**
     * Converts <code>params</code> 使用于URL参数格式化，或者body参数格式化
     */
    public static String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(dealParamKey(entry.getKey()), paramsEncoding));
                encodedParams.append('=');
                //TODO: edited by wilson
                if (entry.getValue() != null) {
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                }
                encodedParams.append('&');
            }
            logger.v(encodedParams.toString());
            return encodedParams.toString();
        } catch (Exception uee) {
            logger.e("encodedParams: " + params, uee);
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    public static byte[] encodeParametersToBytes(Map<String, String> params, String paramsEncoding) {
        try {
            return encodeParameters(params, paramsEncoding).getBytes(paramsEncoding);
        } catch (Exception uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    /**
     * name[0]
     * to
     * name[]
     *
     * @param key
     * @return
     */
    public static String dealParamKey(String key) {
        String content = key;
        String regex = "\\[[0-9]\\d*\\]$";

        Matcher matcher = Pattern.compile(regex).matcher(content);
        if (matcher.find()) {
            content = content.replace(matcher.group(), "[]");
        }

        return content;
    }

    public static void initSSL() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取请求中的头部信息包
     *
     * @return
     */
    public static Header[] toHeaders(Map<String, String> headerMap) {
        Header[] headers = new Header[headerMap.size()];
        int i = 0;
        for (String key : headerMap.keySet()) {
            headers[i] = new BasicHeader(key, headerMap.get(key));
            i++;
        }
        return headers;
    }
}
