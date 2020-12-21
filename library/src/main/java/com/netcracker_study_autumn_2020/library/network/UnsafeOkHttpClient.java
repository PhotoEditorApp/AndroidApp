package com.netcracker_study_autumn_2020.library.network;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;

//Bad temporary solution!
public class UnsafeOkHttpClient {
    private static OkHttpClient okHttpClientInstance;

    public static OkHttpClient getUnsafeOkHttpClient() {
        if (okHttpClientInstance == null) {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                OkHttpClient okHttpClient = builder.build();
                okHttpClientInstance = okHttpClient;
                return okHttpClientInstance;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return okHttpClientInstance;
        }
    }

    public static OkHttpClient getPicassoUnsafeOkHttpClient(String token) {
        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
                .newBuilder()
                .addInterceptor(chain -> {
                    Request customImageRequest = chain.request().newBuilder()
                            .addHeader("Authorization", token)
                            .build();

                    return chain.proceed(customImageRequest);
                })
                .build();
        return client;
    }
}
