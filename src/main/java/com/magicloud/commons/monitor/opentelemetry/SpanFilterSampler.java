package com.magicloud.commons.monitor.opentelemetry;


import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.trace.data.LinkData;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.sdk.trace.samplers.SamplingDecision;
import io.opentelemetry.sdk.trace.samplers.SamplingResult;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpanFilterSampler implements Sampler {
    //過濾Span名稱在EXCLUDED_SPAN_NAMES中的所有Span
    private static final List<String> EXCLUDED_SPAN_NAMES = Collections.unmodifiableList(
            Arrays.asList("spanName1", "spanName2")
    );

    //過濾attributes.http.target在EXCLUDED_HTTP_REQUEST_TARGETS中的所有Span
    private static final List<String> EXCLUDED_HTTP_REQUEST_TARGETS = Collections.unmodifiableList(
            List.of("/health")
    );

    @Override
    public SamplingResult shouldSample(Context parentContext, String traceId, String name, SpanKind spanKind, Attributes attributes, List<LinkData> parentLinks) {
        String httpTarget = attributes.get(SemanticAttributes.HTTP_TARGET) != null ? attributes.get(SemanticAttributes.HTTP_TARGET) : "";

        if (EXCLUDED_SPAN_NAMES.contains(name) || EXCLUDED_HTTP_REQUEST_TARGETS.contains(httpTarget)) { // 根據條件進行過濾
            return SamplingResult.create(SamplingDecision.DROP);
        } else {
            return SamplingResult.create(SamplingDecision.RECORD_AND_SAMPLE);
        }
    }

    @Override
    public String getDescription() {
        return "SpanFilterSampler"; // SpanFilterSampler可以替換為自訂的名稱
    }
}
