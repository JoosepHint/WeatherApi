import entity.Forecast;
import entity.WeatherResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class ApiTest {

    private WeatherResponse response;

    @Before
    public void setup() {
        try {
            response = new WeatherService().getApiResponse();
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
