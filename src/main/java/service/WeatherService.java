package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import entity.Forecast;
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
import java.util.ArrayList;
import java.util.List;

public class WeatherService {

    private static final String INPUT_FILE = "input.txt";

    WeatherResponse getApiResponse(String city) throws IOException {
        String content = getWeatherApiResponseJSON(city);

        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper.readValue(content, WeatherResponse.class);
    }

    String getWeatherApiResponseJSON(String city) throws IOException {
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
        EntityUtils.consume(httpEntity);
        return content;
    }

    void writeToFile(String city, String data) throws IOException {
        Files.write(Paths.get(city + ".txt"), data.getBytes());
    }

    public void saveResponseToFile(WeatherResponse response) throws IOException {
        if (response == null) {
            return;
        }

        StringBuilder forecasts = new StringBuilder();
        int forecastOfDay = 0;
        int dayCount = 0;
        Integer today = 0;
        for (Forecast forecast : response.getForecasts()) {
            if (forecast.getDate().getDayOfMonth() != today) {
                if (dayCount == 0) {
                    forecasts.append("Esimene päev - \n");
                } else if (dayCount == 1) {
                    forecasts.append("Teine päev - \n");
                } else if (dayCount == 2) {
                    forecasts.append("Kolmas päev - \n");
                }

                today = forecast.getDate().getDayOfMonth();
                dayCount++;
                if (dayCount > 3) { // Only get results for first three days
                    break;
                }
                forecastOfDay = 0;
            }

            if (forecastOfDay > 2) {
                continue;
            }


            forecasts.append("  Max - ")
                    .append(forecast.getTemperature().getTempMax()).append("K\n")
                    .append("  Min - ")
                    .append(forecast.getTemperature().getTempMin())
                    .append("K\n---\n");
            forecastOfDay++;
        }

        String data = response.getCity().getName() + " " +
                response.getCity().getCoord().getFormattedCoordinates() + "\n" +
                "Esimese kolme päeva ennustused\n" +
                forecasts +
                "Hetke temperatuur - " + response.getForecasts().get(0).getTemperature().getTemp() + "K";
        writeToFile(response.getCity().getName(), data);
    }

    List<String> readCitiesFromFile(String inputFile) throws IOException {
        return Files.readAllLines(Paths.get(inputFile));
    }

    public List<WeatherResponse> getWeatherResponsesBasedOnFile() throws IOException {
        List<WeatherResponse> responses = new ArrayList<>();

        for (String city : readCitiesFromFile(INPUT_FILE)) {
            responses.add(getApiResponse(city));
        }

        return responses;
    }

    public WeatherResponse readFromConsole() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Insert city - ");
        String input = br.readLine();

        if (input == null || input.isEmpty()) {
            return null;
        }

        return getApiResponse(input);
    }
}