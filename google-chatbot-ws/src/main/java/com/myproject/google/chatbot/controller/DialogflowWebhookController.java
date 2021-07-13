package com.myproject.google.chatbot.controller;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageText;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse;
import com.myproject.google.chatbot.annotation.JsonBody;
import com.myproject.google.chatbot.config.WebMvcConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;

import static java.util.Arrays.asList;

@Controller
@Slf4j
public class DialogflowWebhookController {

//    private static JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

//    @PostMapping("/dialogflow")
//    public Mono<String> webhook(@RequestBody String rawRequest) throws IOException {
//
//        GoogleCloudDialogflowV2WebhookRequest request = jacksonFactory.createJsonParser(rawRequest)
//                .parse(GoogleCloudDialogflowV2WebhookRequest.class);
//
//        StringWriter stringWriter = new StringWriter();
//        JsonGenerator jsonGenerator = jacksonFactory.createJsonGenerator(stringWriter);
//        GoogleCloudDialogflowV2WebhookResponse response = ...
//        jsonGenerator.serialize(response);
//        jsonGenerator.flush();
//        return Mono.just(stringWriter.toString());
//    }


    @Autowired
    private JsonFactory jsonFactory;
// Using RequestBody string or httpEntity<String> is causing an error.  Might be due to GSON HttpMessageConverter not registered
    @PostMapping(value = "/dialogflow-webhook", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<String> webhook( @RequestHeader HttpHeaders headers) throws IOException {
//        String rawData ="{}";

    public ResponseEntity<String> webhook(@RequestBody String rawData , @RequestHeader HttpHeaders headers) throws IOException {
//        public ResponseEntity<String> webhook(@JsonBody String rawData , @RequestHeader HttpHeaders headers) throws IOException {
//        public ResponseEntity<String> webhook(@RequestBody String rawData, @RequestHeader HttpHeaders headers) throws IOException {


//, HttpServletRequest httpServletRequest
//        final String requestBody = IOUtils.toString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
//        log.info("Request body raw: {}", requestBody);
//        String rawData = requestBody;

        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });

        log.info("Incoming webhook request: {}", rawData);
        //Step 1. Parse the request
        GoogleCloudDialogflowV2WebhookRequest request = jsonFactory
                .createJsonParser(rawData)
                .parse(GoogleCloudDialogflowV2WebhookRequest.class);

        //Step 2. Process the request
        //Step 3. Build the response message
        GoogleCloudDialogflowV2IntentMessage msg = new GoogleCloudDialogflowV2IntentMessage();
        GoogleCloudDialogflowV2IntentMessageText text = new GoogleCloudDialogflowV2IntentMessageText();
        text.setText(asList("Welcome to Spring Boot"));
        msg.setText(text);

        GoogleCloudDialogflowV2WebhookResponse response = new GoogleCloudDialogflowV2WebhookResponse();
        response.setFulfillmentMessages(asList(msg));

        // Straight to the point
        response.setFulfillmentText("test message");

        // Serialize to json
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(stringWriter);
        jsonGenerator.enablePrettyPrint();
        jsonGenerator.serialize(response);
        jsonGenerator.flush();

        log.info("json to send out: {}", stringWriter.toString());
        ResponseEntity responseEntity = ResponseEntity.ok(stringWriter.toString());



        String s = "{\n" +
                "  \"fulfillmentMessages\": [\n" +
                "    {\n" +
                "      \"text\": {\n" +
                "        \"text\": [\n" +
                "          \"Text response from webhook\"\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

//        ResponseEntity responseEntity = ResponseEntity.ok(s);

        return responseEntity;
//        return stringWriter.toString();
    }


//    // Using reactive
//    @PostMapping(value = "/dialogflow-webhook", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Flux<ServerSentEvent<String>> streamEvents() {
//        return Flux.interval(Duration.ofSeconds(1))
//                .map(sequence -> ServerSentEvent.<String>builder().id(String.valueOf(sequence))
//                        .event("periodic-event")
//                        .data("SSE - " + LocalTime.now().toString())
//
//                        // this is to prevent say a proxy from killing
//                        // the connnection
//                        .comment("keep alive")
//                        .build());
//    }

}
