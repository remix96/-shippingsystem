package org.project9.shipping.service;

import com.google.gson.Gson;
import org.project9.shipping.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shipping.DDT;
import shipping.Shipping;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class ShippingService {

    @Autowired
    ShippingRepository repository;

    @Autowired
    DDTRepository ddtRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topicLogging}")
    private String topicLogging;

    public Optional<Shipping> getShipping(Integer shippingId, Optional<Integer> userId, HttpServletRequest request) {
        if(!userId.isPresent()) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "400");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<Shipping> shipping;
        if(!repository.existsById(shippingId)) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "404");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(userId.get() == 0)
            shipping = repository.findById(shippingId);
        else
            shipping = repository.findByShippingIdAndUserId(shippingId, userId);
        if(shipping.isEmpty()) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "404");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shipping;
    }

    public Page<Shipping> getAll(Optional<Integer> userId, Pageable pageable, HttpServletRequest request) {
        if(!userId.isPresent()) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "400");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Page<Shipping> shipping;
        if(userId.get() == 0)
            shipping = repository.findAll(pageable);
        else
            shipping = repository.findByUserId(userId, pageable);
        if((shipping.getTotalPages() < pageable.getPageNumber()+1) || shipping.isEmpty()) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "404");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shipping;
    }

    public void addShipping(ShippingCreateRequest shippingRequest) {
        Shipping shipping = new Shipping();
        shipping.setOrderId(shippingRequest.getOrderId());
        shipping.setShippingAddress(shippingRequest.getShippingAddress());
        shipping.setProducts(shippingRequest.getProducts());
        shipping.setUserId(shippingRequest.getUserId());
        shipping.setStatus("Default initial");
        repository.save(shipping);
    }

    public void updateStatus(ShippingUpdateRequest updateRequest) {
        Optional<Shipping> shipping = repository.findByOrderId(updateRequest.getOrderId());
        Shipping s = shipping.get();
        if(updateRequest.getStatus() != 0)
            s.setStatus("Abort");
        else
            s.setStatus("Ok");
        repository.save(s);
    }

    public void updateStatusInvoicing(ShippingUpdateInvoicing updateInvoicing) {
        Optional<Shipping> shipping = repository.findByOrderIdAndUserId(updateInvoicing.getOrderId(), updateInvoicing.getUserId());
        if(!shipping.isPresent()) {
            updateInvoicing.setTimestamp(Instant.now().getEpochSecond());
            kafkaTemplate.send(topicLogging, "shipping_unavailable", new Gson().toJson(updateInvoicing));
        } else {
            Shipping s = shipping.get();
            s.setStatus("TODO");
            if(s.getDDT() == null) {
                DDT ddt = ddtRepository.findById(1).get();
                ddt.setSeq(ddt.getSeq() + 1);
                ddtRepository.save(ddt);
                s.setDDT(ddt.getSeq());
            }
        }
    }

    public String pingAck(Optional<Integer> userId, HttpServletRequest request) {
        if(!userId.isPresent()) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "400");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(userId.get() != 0) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "403");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return "'serviceStatus': 'up', 'dbStatus': 'up'";
    }

    public void sendKafkaError(Long timestamp, String sourceIp, String request, String error) {
        ShippingHttpErrors httpErrors = new ShippingHttpErrors();
        httpErrors.setTimestamp(timestamp);
        httpErrors.setSourceIp(sourceIp);
        httpErrors.setRequest(request);
        httpErrors.setError(error);
        kafkaTemplate.send(topicLogging, "http_errors", new Gson().toJson(httpErrors));
    }

}
