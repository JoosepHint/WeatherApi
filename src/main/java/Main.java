import entity.WeatherResponse;
import service.WeatherService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WeatherService weatherService = new WeatherService();
        WeatherResponse response = weatherService.readFromConsole();
        weatherService.saveResponseToFile(response);

        weatherService.getWeatherResponsesBasedOnFile().forEach(t -> {
            try {
                weatherService.saveResponseToFile(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}