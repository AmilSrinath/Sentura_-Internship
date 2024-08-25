package lk.ijse.gdse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.gdse.dto.UserDTO;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.CertificateException;

/**
 * @author Amil Srinath
 */
@Service
public class WeavyService {

    @Value("${weavy.api.key}")
    private String apiKey;

    @Value("${weavy.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String save(UserDTO userDTO) throws IOException {
        OkHttpClient client = getUnsafeOkHttpClient();

        String userJson = objectMapper.writeValueAsString(userDTO);
        RequestBody body = RequestBody.create(userJson, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    public String getUser(String uid) throws IOException {
        OkHttpClient client = getUnsafeOkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl + "/users/" + uid)
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    public String delete(String uid) throws IOException {
        OkHttpClient client = getUnsafeOkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl + "/users/" + uid)
                .delete()
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    public String update(String uid, UserDTO userDTO) throws IOException {
        OkHttpClient client = getUnsafeOkHttpClient();
        String userJson = objectMapper.writeValueAsString(userDTO);
        RequestBody body = RequestBody.create(userJson, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(apiUrl + "/users/" + uid)
                .put(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    public String getUsers() throws IOException {
        OkHttpClient client = getUnsafeOkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl + "/users")
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
}
