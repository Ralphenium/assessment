package technology.assessment.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import technology.assessment.kafka.HttpTraceProducer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HttpTraceInterceptor implements HandlerInterceptor {

    private final HttpTraceProducer httpTraceProducer;

    @Autowired
    public HttpTraceInterceptor(HttpTraceProducer httpTraceProducer) {
        this.httpTraceProducer = httpTraceProducer;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // Capture the HTTP trace
        String httpTrace = captureHttpTrace(request, response);

        // Send the HTTP trace to Kafka
        httpTraceProducer.sendHttpTrace(httpTrace);

        return true;
    }

    private String captureHttpTrace(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder trace = new StringBuilder();

        // Append request details to the trace
        trace.append("Request Method: ").append(request.getMethod()).append(System.lineSeparator());
        trace.append("Request URI: ").append(request.getRequestURI()).append(System.lineSeparator());
        trace.append("Request Headers: ").append(getHeadersAsString(request)).append(System.lineSeparator());

        // Append response details to the trace
        trace.append("Response Status: ").append(response.getStatus()).append(System.lineSeparator());
        trace.append("Response Headers: ").append(getHeadersAsString(response)).append(System.lineSeparator());

        return trace.toString();
    }

    private String getHeadersAsString(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(name -> name, request::getHeader)));

        return headers.toString();
    }

    private String getHeadersAsString(HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        response.getHeaderNames().forEach(name -> {
            List<String> values = new ArrayList<>(response.getHeaders(name));
            headers.addAll(name, values);
        });

        return headers.toString();
    }
}

