package ru.job4j.weather.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.job4j.weather.Weather;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();

    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPB", 18));
        weathers.put(3, new Weather(3, "Yekaterinburg", 25));
        weathers.put(4, new Weather(4, "Nizhniy Novgorod", 16));
        weathers.put(5, new Weather(5, "Kaliningrad", 10));
        weathers.put(6, new Weather(6, "Cheboksary", 19));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Mono<Weather> hottest() {
        return Mono.justOrEmpty(weathers.values()
                .stream()
                .max(Comparator.comparing(Weather::getTemperature)));
    }

    public Flux<Weather> cityGreatThen(int size) {
        return Flux.fromIterable(
                weathers.values()
                        .stream()
                        .filter(e -> e.getTemperature() > size)
                        .collect(Collectors.toList())
        );
    }
}

