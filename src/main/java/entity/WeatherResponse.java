package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    @JsonProperty("cod")
    private String code;
    @JsonProperty("cnt")
    private Long forecastCount;
    @JsonProperty("list")
    private List<Forecast> forecasts;
    @JsonProperty("city")
    private City city;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getForecastCount() {
        return forecastCount;
    }

    public void setForecastCount(Long count) {
        this.forecastCount = count;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", count=" + forecastCount +
                ", forecasts=" + forecasts +
                ", city=" + city +
                '}';
    }
}
