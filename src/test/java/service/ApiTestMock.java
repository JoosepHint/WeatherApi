package service;

import entity.Forecast;
import entity.WeatherResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ApiTestMock {

    private WeatherService weatherService;
    private WeatherResponse response;

    @Before
    public void setup() {
        try {
            String apiResponse = new String(Files.readAllBytes(Paths.get("mock.json")));
            weatherService = spy(WeatherService.class);
            doReturn(apiResponse).when(weatherService).getWeatherApiResponseJSON(anyString());
            response = weatherService.getApiResponse("Tallinn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCorrectJSON() {
        assertNotNull(response);
    }

    @Test
    public void testCorrectCity() {
        assertEquals("Tallinn", response.getCity().getName());
    }

    @Test
    public void testCorrectCountry() {
        assertEquals("EE", response.getCity().getCountry());
    }

    @Test
    public void testCorrectLatitude() {
        assertEquals(59.437D, response.getCity().getCoordinates().getLatitude(), 0.5);
    }

    @Test
    public void testCorrectLongitude() {
        assertEquals(24.7D, response.getCity().getCoordinates().getLongitude(), 0.5);
    }

    @Test
    public void testCorrectMomentTemperature() {
        assertEquals(new Double(274.16),response.getForecasts().get(0).getTemperature().getTemp());
    }

    @Test
    public void testCorrectMaxTemperature() {
        assertEquals(new Double(274.639),response.getForecasts().get(0).getTemperature().getTempMax());
    }

    @Test
    public void testCorrectMinTemperature() {
        assertEquals(new Double(274.16),response.getForecasts().get(0).getTemperature().getTempMin());
    }

    @Test
    public void testCorrectTemperaturesAmount() {
        assertEquals(response.getForecastCount(), new Long(response.getForecasts().size()));
    }

    @Test
    public void testCorrectInterval() {
        LocalDateTime date = null;
        for (Forecast forecast : response.getForecasts()) {
            if (date != null) {
                assertEquals(date, forecast.getDate().minusHours(3));
            }
            date = forecast.getDate();
        }
    }

    @Test
    public void testCorrectCoordinatesFormat() {
        assertEquals("59:25", response.getCity().getCoordinates().getFormattedCoordinates());
    }

    @Test
    public void testWritesCorrectly() throws IOException {
        doNothing().when(weatherService).writeToFile(anyString(), anyString());
        weatherService.saveResponseToFile(response);
        verify(weatherService).writeToFile("Tallinn", "Tallinn 59:25\n" +
                "Esimese kolme päeva ennustused\n" +
                "Esimene päev - \n" +
                "  Max - 274.639K\n" +
                "  Min - 274.16K\n" +
                "---\n" +
                "  Max - 273.647K\n" +
                "  Min - 273.29K\n" +
                "---\n" +
                "  Max - 272.479K\n" +
                "  Min - 272.24K\n" +
                "---\n" +
                "Teine päev - \n" +
                "  Max - 270.996K\n" +
                "  Min - 270.996K\n" +
                "---\n" +
                "  Max - 271.582K\n" +
                "  Min - 271.582K\n" +
                "---\n" +
                "  Max - 272.364K\n" +
                "  Min - 272.364K\n" +
                "---\n" +
                "Kolmas päev - \n" +
                "  Max - 277.169K\n" +
                "  Min - 277.169K\n" +
                "---\n" +
                "  Max - 277.799K\n" +
                "  Min - 277.799K\n" +
                "---\n" +
                "  Max - 278.694K\n" +
                "  Min - 278.694K\n" +
                "---\n" +
                "Hetke temperatuur - 274.16K");
    }

    @Test
    public void testReadsFileCorrectly() throws IOException {
        WeatherService weatherService = spy(WeatherService.class);

        doReturn(null).when(weatherService).getApiResponse(anyString());
        doReturn(Arrays.asList("Tallinn", "Soome", "Venemaa")).when(weatherService).readCitiesFromFile(anyString());

        weatherService.getWeatherResponsesBasedOnFile();

        verify(weatherService).getApiResponse("Tallinn");
        verify(weatherService).getApiResponse("Soome");
        verify(weatherService).getApiResponse("Venemaa");
    }

    @Test
    public void testReadsEmptyFile() throws IOException {
        WeatherService weatherService = spy(WeatherService.class);
        doReturn(Collections.emptyList()).when(weatherService).readCitiesFromFile(anyString());
        weatherService.getWeatherResponsesBasedOnFile();
        verify(weatherService, times(0)).getApiResponse(any());
    }

    @Test
    public void testReadsInputCorrectly() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("Helsinki".getBytes());
        System.setIn(in);

        WeatherService weatherService = spy(WeatherService.class);
        doReturn(null).when(weatherService).getApiResponse(anyString());
        weatherService.readFromConsole();

        verify(weatherService).getApiResponse("Helsinki");
    }

    @Test
    public void testEmptyInput() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
        System.setIn(in);

        WeatherService weatherService = spy(WeatherService.class);
        weatherService.readFromConsole();

        verify(weatherService, times(0)).getApiResponse(any());
    }
}
