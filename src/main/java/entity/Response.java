package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    @JsonProperty("cod")
    private String code;
    @JsonProperty("cnt")
    private Long count;
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
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
                ", count=" + count +
                ", forecasts=" + forecasts +
                ", city=" + city +
                '}';
    }
}
