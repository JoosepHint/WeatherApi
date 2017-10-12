import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import entity.WeatherResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherService {
    public static void main(String[] args) throws Exception {
        WeatherResponse response1 = new WeatherService().getApiResponse();
        System.out.println(response1);
    }

    public WeatherResponse getApiResponse() throws IOException {
        String weatherAPI = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&appid=459d16e78c8ff4a179b9884bbbc18cce";

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(7000)
                        .setConnectTimeout(5000)
                        .setExpectContinueEnabled(false)
                        .setSocketTimeout(5000)
                        .setCookieSpec("easy")
                        .build())
                .build();
        HttpGet request = new HttpGet(weatherAPI);
        HttpResponse response = httpClient.execute(request);

        HttpEntity httpEntity = response.getEntity();
        String content = IOUtils.toString(httpEntity.getContent(), Charset.defaultCharset());
        EntityUtils.consume(httpEntity);

        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper.readValue(content, WeatherResponse.class);
    }
}