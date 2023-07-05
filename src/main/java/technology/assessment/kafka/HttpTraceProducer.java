package technology.assessment.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class HttpTraceProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName = "http-trace-topic";

    @Autowired
    public HttpTraceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendHttpTrace(String httpTrace) {
        kafkaTemplate.send(topicName, httpTrace);
    }
}
