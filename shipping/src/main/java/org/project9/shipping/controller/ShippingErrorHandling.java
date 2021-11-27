package org.project9.shipping.controller;

import com.google.gson.Gson;
import org.project9.shipping.data.ShippingHttpErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
public class ShippingErrorHandling extends AbstractHandlerExceptionResolver {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topicLogging}")
    private String topicLogging;

    @ExceptionHandler(Exception.class)
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ShippingHttpErrors httpErrors = new ShippingHttpErrors();
        httpErrors.setTimestamp(Instant.now().getEpochSecond());
        httpErrors.setSourceIp(request.getRemoteAddr());
        httpErrors.setRequest(request.getRequestURI().concat(" ").concat(request.getMethod()));
        httpErrors.setError(ex.toString());
        kafkaTemplate.send(topicLogging,"http_errors", new Gson().toJson(httpErrors));
        return null;
    }

    private ModelAndView handleIllegalArgument(IllegalArgumentException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendError(HttpServletResponse.SC_CONFLICT);
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        return new ModelAndView();
    }

}
