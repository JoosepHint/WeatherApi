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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherService {

    public static final String INPUT_FILE = "input.txt";
    public static final String OUTPUT_FILE = "output.txt";

    public WeatherResponse getApiResponse(String city) throws IOException {
        String content = getWeatherApiResponseJSON(city);

        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper.readValue(content, WeatherResponse.class);
    }

    public String getWeatherApiResponseJSON(String city) throws IOException {
        String weatherAPI = "http://api.openweathermap.org/data/2.5/forecast?q=" + city +
                "&appid=459d16e78c8ff4a179b9884bbbc18cce";

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
        Files.write(Paths.get(OUTPUT_FILE), content.getBytes());
        EntityUtils.consume(httpEntity);
        return content;
    }

    public WeatherResponse readFromFile() throws IOException {
        String fileContents = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
        return getApiResponse(fileContents);
    }

    public WeatherResponse readFromInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Insert city - ");
        String input = br.readLine();

        return getApiResponse(input);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new WeatherService().readFromInput());
        System.out.println(new WeatherService().readFromFile());
    }
}