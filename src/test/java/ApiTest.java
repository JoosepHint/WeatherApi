import entity.Forecast;
import entity.WeatherResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApiTest {

    private WeatherResponse response;

    @Before
    public void setup() {
        try {
            response = new WeatherService().getApiResponse("Tallinn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCorrectJSON() {
        assertNotNull(response);
    }

    @Test
    public void testCorrectJSONParsing() throws Exception {
        WeatherService weatherService = spy(WeatherService.class);
        when(weatherService.getWeatherApiResponseJSON("Tallinn")).thenReturn("{ \"cod\": \"200\", \"message\": 0.003, \"cnt\": 1, \"list\": [{ \"dt\": 1510066800, \"main\": { \"temp\": 278.32, \"temp_kf\": -0.3 }, \"weather\": [{ \"id\": 800 }], \"dt_txt\": \"2017-11-07 15:00:00\" }], \"city\": { \"id\": 588409, \"name\": \"Tallinn\", \"coord\": { \"lat\": 59.437, \"lon\": 24.7535 }, \"country\": \"EE\" } }");
        WeatherResponse weatherResponse = weatherService.getApiResponse("Tallinn");

        assertEquals(new Long(1), weatherResponse.getForecastCount());
        assertEquals("Tallinn", weatherResponse.getCity().getName());
        assertEquals(new Double(59.437), weatherResponse.getCity().getCoord().getLatitude());
        assertEquals(new Double(24.7535), weatherResponse.getCity().getCoord().getLongitude());
        assertEquals(new Double(278.32), weatherResponse.getForecasts().get(0).getTemperature().getTemp());
    }

    @Test
    public void testCorrectCity() {
        assertEquals("EE", response.getCity().getCountry());
    }

    @Test
    public void testCorrectLatitude() {
        assertEquals(59.437D, response.getCity().getCoord().getLatitude(), 0.5);
    }

    @Test
    public void testCorrectLongitude() {
        assertEquals(24.7D, response.getCity().getCoord().getLongitude(), 0.5);
    }

    @Test
    public void testCorrectMomentTemperature() {
        assertNotNull(response.getForecasts().get(0).getTemperature().getTemp());
    }

    @Test
    public void testCorrectMaxTemperature() {
        assertNotNull(response.getForecasts().get(0).getTemperature().getTempMax());
    }

    @Test
    public void testCorrectMinTemperature() {
        assertNotNull(response.getForecasts().get(0).getTemperature().getTempMin());
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
        assertEquals("59:25", response.getCity().getCoord().getFormattedCoordinates());
    }
}
