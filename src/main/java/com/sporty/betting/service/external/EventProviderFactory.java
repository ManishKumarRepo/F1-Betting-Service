package com.sporty.betting.service.external;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventProviderFactory {

    private final Map<String, F1EventProvider> providers;

    public EventProviderFactory(List<F1EventProvider> providerList) {
        this.providers = providerList.stream()
                .collect(Collectors.toMap(
                        p -> p.getClass().getSimpleName(),
                        p -> p
                ));
    }

    public F1EventProvider getProvider(String name) {
        return providers.getOrDefault(name, providers.get("OpenF1EventProvider"));
    }
}

