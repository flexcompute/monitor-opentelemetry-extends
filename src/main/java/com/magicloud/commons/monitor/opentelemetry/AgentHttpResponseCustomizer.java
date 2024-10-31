package com.magicloud.commons.monitor.opentelemetry;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.bootstrap.http.HttpServerResponseCustomizer;
import io.opentelemetry.javaagent.bootstrap.http.HttpServerResponseMutator;
import io.opentelemetry.javaagent.shaded.io.opentelemetry.api.trace.Span;
import io.opentelemetry.javaagent.shaded.io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.javaagent.shaded.io.opentelemetry.context.Context;

@AutoService(HttpServerResponseCustomizer.class)
public class AgentHttpResponseCustomizer implements HttpServerResponseCustomizer {
    @Override
    public <RESPONSE> void customize(Context context, RESPONSE response, HttpServerResponseMutator<RESPONSE> responseMutator) {
        SpanContext spanContext = Span.fromContext(context).getSpanContext();
        String traceId = spanContext.getTraceId();
        String spanId = spanContext.getSpanId();

        // 在 HTTP Response Header中设置 traceId 和 spanId, Header 字段名可以自定义
        responseMutator.appendHeader(response, "otm-trace-id", traceId);
        responseMutator.appendHeader(response, "otm-span-id", spanId);
    }
}
