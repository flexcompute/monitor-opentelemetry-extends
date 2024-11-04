package com.magicloud.commons.monitor.opentelemetry;

import com.google.auto.service.AutoService;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import io.opentelemetry.sdk.autoconfigure.spi.traces.ConfigurableSamplerProvider;
import io.opentelemetry.sdk.trace.samplers.Sampler;

@AutoService(ConfigurableSamplerProvider.class)
public class SpanFilterSamplerProvider implements ConfigurableSamplerProvider {
    @Override
    public Sampler createSampler(ConfigProperties configProperties) {
        return new SpanFilterSampler();
    }

    @Override
    public String getName() {
        return "SpanFilterSampler"; // SpanFilterSampler可以替換為自訂的名稱
    }
}
