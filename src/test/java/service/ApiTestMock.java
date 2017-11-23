package service;

import entity.Forecast;
import entity.WeatherResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
            String apiResponse = "{\"cod\":\"200\",\"message\":0.2439,\"cnt\":40,\"list\":[{\"dt\":1511352000,\"main\":{\"temp\":274.16,\"temp_min\":274.16,\"temp_max\":274.639,\"pressure\":1023.64,\"sea_level\":1027.25,\"grnd_level\":1023.64,\"humidity\":100,\"temp_kf\":-0.48},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13d\"}],\"clouds\":{\"all\":44},\"wind\":{\"speed\":3.36,\"deg\":176.502},\"rain\":{},\"snow\":{\"3h\":0.0785},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-22 12:00:00\"},{\"dt\":1511362800,\"main\":{\"temp\":273.29,\"temp_min\":273.29,\"temp_max\":273.647,\"pressure\":1024.81,\"sea_level\":1028.52,\"grnd_level\":1024.81,\"humidity\":100,\"temp_kf\":-0.36},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":3.91,\"deg\":165.001},\"rain\":{},\"snow\":{\"3h\":0.011},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-22 15:00:00\"},{\"dt\":1511373600,\"main\":{\"temp\":272.24,\"temp_min\":272.24,\"temp_max\":272.479,\"pressure\":1026.07,\"sea_level\":1029.68,\"grnd_level\":1026.07,\"humidity\":100,\"temp_kf\":-0.24},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":44},\"wind\":{\"speed\":4.41,\"deg\":144.004},\"rain\":{},\"snow\":{\"3h\":0.003},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-22 18:00:00\"},{\"dt\":1511384400,\"main\":{\"temp\":271.13,\"temp_min\":271.13,\"temp_max\":271.252,\"pressure\":1026.74,\"sea_level\":1030.29,\"grnd_level\":1026.74,\"humidity\":100,\"temp_kf\":-0.12},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":5.36,\"deg\":141},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-22 21:00:00\"},{\"dt\":1511395200,\"main\":{\"temp\":270.996,\"temp_min\":270.996,\"temp_max\":270.996,\"pressure\":1026.78,\"sea_level\":1030.43,\"grnd_level\":1026.78,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":6.46,\"deg\":140.002},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-23 00:00:00\"},{\"dt\":1511406000,\"main\":{\"temp\":271.582,\"temp_min\":271.582,\"temp_max\":271.582,\"pressure\":1026.02,\"sea_level\":1029.7,\"grnd_level\":1026.02,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":7.81,\"deg\":139.002},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-23 03:00:00\"},{\"dt\":1511416800,\"main\":{\"temp\":272.364,\"temp_min\":272.364,\"temp_max\":272.364,\"pressure\":1024.89,\"sea_level\":1028.58,\"grnd_level\":1024.89,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":9.22,\"deg\":144.004},\"rain\":{},\"snow\":{\"3h\":0.14825},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-23 06:00:00\"},{\"dt\":1511427600,\"main\":{\"temp\":272.919,\"temp_min\":272.919,\"temp_max\":272.919,\"pressure\":1023.49,\"sea_level\":1027.11,\"grnd_level\":1023.49,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":10.67,\"deg\":146.001},\"rain\":{},\"snow\":{\"3h\":0.835},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-23 09:00:00\"},{\"dt\":1511438400,\"main\":{\"temp\":273.313,\"temp_min\":273.313,\"temp_max\":273.313,\"pressure\":1022.09,\"sea_level\":1025.76,\"grnd_level\":1022.09,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":11.06,\"deg\":158.5},\"rain\":{},\"snow\":{\"3h\":0.98875},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-23 12:00:00\"},{\"dt\":1511449200,\"main\":{\"temp\":273.597,\"temp_min\":273.597,\"temp_max\":273.597,\"pressure\":1021.06,\"sea_level\":1024.62,\"grnd_level\":1021.06,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":10.45,\"deg\":160.501},\"rain\":{\"3h\":0.065},\"snow\":{\"3h\":0.0575},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-23 15:00:00\"},{\"dt\":1511460000,\"main\":{\"temp\":274.676,\"temp_min\":274.676,\"temp_max\":274.676,\"pressure\":1019.71,\"sea_level\":1023.24,\"grnd_level\":1019.71,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":10.12,\"deg\":162.001},\"rain\":{\"3h\":0.195},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-23 18:00:00\"},{\"dt\":1511470800,\"main\":{\"temp\":276.163,\"temp_min\":276.163,\"temp_max\":276.163,\"pressure\":1018.28,\"sea_level\":1021.85,\"grnd_level\":1018.28,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":10.22,\"deg\":170.001},\"rain\":{\"3h\":0.225},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-23 21:00:00\"},{\"dt\":1511481600,\"main\":{\"temp\":277.169,\"temp_min\":277.169,\"temp_max\":277.169,\"pressure\":1017.08,\"sea_level\":1020.65,\"grnd_level\":1017.08,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":9.96,\"deg\":177.5},\"rain\":{\"3h\":0.31},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-24 00:00:00\"},{\"dt\":1511492400,\"main\":{\"temp\":277.799,\"temp_min\":277.799,\"temp_max\":277.799,\"pressure\":1016.19,\"sea_level\":1019.79,\"grnd_level\":1016.19,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":9.75,\"deg\":181.501},\"rain\":{\"3h\":1.16},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-24 03:00:00\"},{\"dt\":1511503200,\"main\":{\"temp\":278.694,\"temp_min\":278.694,\"temp_max\":278.694,\"pressure\":1016.65,\"sea_level\":1020.2,\"grnd_level\":1016.65,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":9.06,\"deg\":190.5},\"rain\":{\"3h\":0.62},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-24 06:00:00\"},{\"dt\":1511514000,\"main\":{\"temp\":279.848,\"temp_min\":279.848,\"temp_max\":279.848,\"pressure\":1017.31,\"sea_level\":1020.81,\"grnd_level\":1017.31,\"humidity\":97,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":7.41,\"deg\":196.001},\"rain\":{\"3h\":0.23},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-24 09:00:00\"},{\"dt\":1511524800,\"main\":{\"temp\":280.328,\"temp_min\":280.328,\"temp_max\":280.328,\"pressure\":1017.74,\"sea_level\":1021.38,\"grnd_level\":1017.74,\"humidity\":97,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":6.68,\"deg\":191.001},\"rain\":{\"3h\":0.515},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-24 12:00:00\"},{\"dt\":1511535600,\"main\":{\"temp\":280.264,\"temp_min\":280.264,\"temp_max\":280.264,\"pressure\":1017.04,\"sea_level\":1020.61,\"grnd_level\":1017.04,\"humidity\":96,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":7.71,\"deg\":183.5},\"rain\":{\"3h\":0.795},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-24 15:00:00\"},{\"dt\":1511546400,\"main\":{\"temp\":279.768,\"temp_min\":279.768,\"temp_max\":279.768,\"pressure\":1016.3,\"sea_level\":1019.79,\"grnd_level\":1016.3,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":8.68,\"deg\":177.502},\"rain\":{\"3h\":0.28},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-24 18:00:00\"},{\"dt\":1511557200,\"main\":{\"temp\":279.181,\"temp_min\":279.181,\"temp_max\":279.181,\"pressure\":1015.22,\"sea_level\":1018.8,\"grnd_level\":1015.22,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":8.8,\"deg\":183.501},\"rain\":{\"3h\":2.075},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-24 21:00:00\"},{\"dt\":1511568000,\"main\":{\"temp\":279.667,\"temp_min\":279.667,\"temp_max\":279.667,\"pressure\":1015.19,\"sea_level\":1018.81,\"grnd_level\":1015.19,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":6.2,\"deg\":218.004},\"rain\":{\"3h\":3.135},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-25 00:00:00\"},{\"dt\":1511578800,\"main\":{\"temp\":278.192,\"temp_min\":278.192,\"temp_max\":278.192,\"pressure\":1017.2,\"sea_level\":1020.76,\"grnd_level\":1017.2,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.22,\"deg\":253.501},\"rain\":{\"3h\":0.030000000000001},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-25 03:00:00\"},{\"dt\":1511589600,\"main\":{\"temp\":277.468,\"temp_min\":277.468,\"temp_max\":277.468,\"pressure\":1019.04,\"sea_level\":1022.6,\"grnd_level\":1019.04,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":5.65,\"deg\":229.501},\"rain\":{\"3h\":0.0099999999999998},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-25 06:00:00\"},{\"dt\":1511600400,\"main\":{\"temp\":278.063,\"temp_min\":278.063,\"temp_max\":278.063,\"pressure\":1020.87,\"sea_level\":1024.37,\"grnd_level\":1020.87,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":5.48,\"deg\":219.001},\"rain\":{\"3h\":0.0099999999999998},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-25 09:00:00\"},{\"dt\":1511611200,\"main\":{\"temp\":278.603,\"temp_min\":278.603,\"temp_max\":278.603,\"pressure\":1021.82,\"sea_level\":1025.37,\"grnd_level\":1021.82,\"humidity\":99,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":4.37,\"deg\":211.003},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-25 12:00:00\"},{\"dt\":1511622000,\"main\":{\"temp\":276.888,\"temp_min\":276.888,\"temp_max\":276.888,\"pressure\":1022.45,\"sea_level\":1025.91,\"grnd_level\":1022.45,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":3.87,\"deg\":176.501},\"rain\":{\"3h\":0.0099999999999998},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-25 15:00:00\"},{\"dt\":1511632800,\"main\":{\"temp\":276.968,\"temp_min\":276.968,\"temp_max\":276.968,\"pressure\":1021.35,\"sea_level\":1024.91,\"grnd_level\":1021.35,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":4.76,\"deg\":134.008},\"rain\":{\"3h\":0.14},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-25 18:00:00\"},{\"dt\":1511643600,\"main\":{\"temp\":277.38,\"temp_min\":277.38,\"temp_max\":277.38,\"pressure\":1018.84,\"sea_level\":1022.44,\"grnd_level\":1018.84,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":6.91,\"deg\":110.501},\"rain\":{\"3h\":1.5},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-25 21:00:00\"},{\"dt\":1511654400,\"main\":{\"temp\":277.941,\"temp_min\":277.941,\"temp_max\":277.941,\"pressure\":1017.06,\"sea_level\":1020.63,\"grnd_level\":1017.06,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":7.36,\"deg\":151.504},\"rain\":{\"3h\":1.06},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-26 00:00:00\"},{\"dt\":1511665200,\"main\":{\"temp\":277.758,\"temp_min\":277.758,\"temp_max\":277.758,\"pressure\":1015.29,\"sea_level\":1018.82,\"grnd_level\":1015.29,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":7.76,\"deg\":152.503},\"rain\":{\"3h\":0.17},\"snow\":{\"3h\":0.0024999999999999},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-26 03:00:00\"},{\"dt\":1511676000,\"main\":{\"temp\":276.442,\"temp_min\":276.442,\"temp_max\":276.442,\"pressure\":1013.73,\"sea_level\":1017.34,\"grnd_level\":1013.73,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":9.7,\"deg\":155.502},\"rain\":{\"3h\":0.11},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-26 06:00:00\"},{\"dt\":1511686800,\"main\":{\"temp\":277.1,\"temp_min\":277.1,\"temp_max\":277.1,\"pressure\":1012.99,\"sea_level\":1016.51,\"grnd_level\":1012.99,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":9.77,\"deg\":160.003},\"rain\":{\"3h\":1.51},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-26 09:00:00\"},{\"dt\":1511697600,\"main\":{\"temp\":279.037,\"temp_min\":279.037,\"temp_max\":279.037,\"pressure\":1011.82,\"sea_level\":1015.38,\"grnd_level\":1011.82,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":9.8,\"deg\":170.503},\"rain\":{\"3h\":0.82},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-26 12:00:00\"},{\"dt\":1511708400,\"main\":{\"temp\":280.103,\"temp_min\":280.103,\"temp_max\":280.103,\"pressure\":1012.53,\"sea_level\":1016.08,\"grnd_level\":1012.53,\"humidity\":97,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":8.81,\"deg\":190.003},\"rain\":{\"3h\":0.96},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-26 15:00:00\"},{\"dt\":1511719200,\"main\":{\"temp\":278.877,\"temp_min\":278.877,\"temp_max\":278.877,\"pressure\":1015.08,\"sea_level\":1018.6,\"grnd_level\":1015.08,\"humidity\":97,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":8.42,\"deg\":214.002},\"rain\":{\"3h\":0.0099999999999998},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-26 18:00:00\"},{\"dt\":1511730000,\"main\":{\"temp\":278.205,\"temp_min\":278.205,\"temp_max\":278.205,\"pressure\":1017.51,\"sea_level\":1021.03,\"grnd_level\":1017.51,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":6.61,\"deg\":212.501},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-26 21:00:00\"},{\"dt\":1511740800,\"main\":{\"temp\":277.163,\"temp_min\":277.163,\"temp_max\":277.163,\"pressure\":1019.21,\"sea_level\":1022.79,\"grnd_level\":1019.21,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":56},\"wind\":{\"speed\":5.42,\"deg\":197.505},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-27 00:00:00\"},{\"dt\":1511751600,\"main\":{\"temp\":276.245,\"temp_min\":276.245,\"temp_max\":276.245,\"pressure\":1019.84,\"sea_level\":1023.34,\"grnd_level\":1019.84,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":44},\"wind\":{\"speed\":4.86,\"deg\":176.001},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-27 03:00:00\"},{\"dt\":1511762400,\"main\":{\"temp\":276.132,\"temp_min\":276.132,\"temp_max\":276.132,\"pressure\":1019.98,\"sea_level\":1023.58,\"grnd_level\":1019.98,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":3.21,\"deg\":156.502},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-11-27 06:00:00\"},{\"dt\":1511773200,\"main\":{\"temp\":276.847,\"temp_min\":276.847,\"temp_max\":276.847,\"pressure\":1019.78,\"sea_level\":1023.4,\"grnd_level\":1019.78,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":4.11,\"deg\":125.509},\"rain\":{\"3h\":0.16},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-11-27 09:00:00\"}],\"city\":{\"id\":588409,\"name\":\"Tallinn\",\"coord\":{\"lat\":59.437,\"lon\":24.7535},\"country\":\"EE\"}}";
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
        assertEquals(59.437D, response.getCity().getCoord().getLatitude(), 0.5);
    }

    @Test
    public void testCorrectLongitude() {
        assertEquals(24.7D, response.getCity().getCoord().getLongitude(), 0.5);
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
        assertEquals("59:25", response.getCity().getCoord().getFormattedCoordinates());
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
