package com.myproject.google.chatbot.service;


import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.*;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.myproject.google.chatbot.config.ChatbotConfigProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class DialogFlowService {

    @Autowired
    private ChatbotConfigProperties chatbotConfigProperties;

    private static final Gson gson = new Gson();
    @PostConstruct
    private void postInit() {
        log.info("Chatbot config: {}", chatbotConfigProperties);
    }
    public Map<String, QueryResult> detectIntentTexts(
            List<String> texts)
            throws IOException, ApiException {

        return detectIntentTexts(texts,null);
    }

    public Map<String, QueryResult> detectIntentTexts(
            List<String> texts,String languageCode)
            throws IOException, ApiException {

        String name = chatbotConfigProperties.getName();
        String projectId = chatbotConfigProperties.getProjectId();

        // assign unique session; can be tied to user session
        String sessionId = UUID.randomUUID().toString();

        // default
        String languageCodeResolved = chatbotConfigProperties.getLanguageCode();

        log.debug("Language code:{}", languageCode);
        if (languageCode!=null) {
            languageCodeResolved=languageCode;
        }


        return detectIntentTexts(projectId,texts,sessionId, languageCodeResolved);
    }
        // DialogFlow API Detect Intent sample with text inputs.
    public Map<String, QueryResult> detectIntentTexts(
            String projectId, List<String> texts, String sessionId, String languageCode)
            throws IOException, ApiException {
        Map<String, QueryResult> queryResults = Maps.newHashMap();
        // Instantiates a client
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            // Set the session name using the sessionId (UUID) and projectID (my-project-id)
            SessionName session = SessionName.of(projectId, sessionId);
            log.debug("Session Path: {}", session.toString());

            // Detect intents for each text input
            for (String text : texts) {
                // Set the text (hello) and language code (en-US) for the query
                TextInput.Builder textInput =
                        TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

                // Build the query with the TextInput
                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

                // Performs the detect intent request
                DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

                // Display the query result
                QueryResult queryResult = response.getQueryResult();



                String json = gson.toJson(queryResult);
                log.debug("JSON query result: {}", json);

                log.debug("====================");
                log.debug("Query Text: '{}'", queryResult.getQueryText());
                log.debug(
                        "Detected Intent: {} (confidence: {})",
                        queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());

                // This can be the alternative for simple user info
                log.debug("Fulfillment Text - simple : '{}'",queryResult.getFulfillmentText());
                // This is
                log.debug(
                        "Fulfillment Text: '{}'",
                        queryResult.getFulfillmentMessagesCount() > 0
                                ? queryResult.getFulfillmentMessages(0).getText()
                                : "Triggered Default Fallback Intent");

                queryResults.put(text, queryResult);
            }
        }
        return queryResults;
    }

    public String detectIntentAudio(String projectId, byte[] inputAudio, String sessionId, String languageCode) throws IOException, ApiException {
        // Instantiates a client
        QueryResult queryResult = null;
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            // Set the session name using the sessionId (UUID) and projectID (my-project-id)
            SessionName session = SessionName.of(projectId, sessionId);
            log.debug("Session Path: {}", session.toString());

            // Note: hard coding audioEncoding and sampleRateHertz for simplicity.
            // Audio encoding of the audio content sent in the query request.
            AudioEncoding audioEncoding = AudioEncoding.AUDIO_ENCODING_LINEAR_16;
            int sampleRateHertz = 16000;

            // Instructs the speech recognizer how to process the audio content.
            InputAudioConfig inputAudioConfig = InputAudioConfig.newBuilder().setAudioEncoding(audioEncoding)
                    .setLanguageCode(languageCode) // languageCode = "en-US"
                    .setSampleRateHertz(sampleRateHertz) // sampleRateHertz = 16000
                    .build();

            // Build the query with the InputAudioConfig
            QueryInput queryInput = QueryInput.newBuilder().setAudioConfig(inputAudioConfig).build();

            // Read the bytes from the audio file

            // Build the DetectIntentRequest
            DetectIntentRequest request = DetectIntentRequest.newBuilder().setSession(session.getSession())
                    .setQueryInput(queryInput).setInputAudio(ByteString.copyFrom(inputAudio)).build();

            // Performs the detect intent request
            DetectIntentResponse response = sessionsClient.detectIntent(request);

            // unique identifier
            log.debug("ResponseId: {}", response.getResponseId());
            // Display the query result

            queryResult = response.getQueryResult();
            log.debug("====================");
            log.debug("Query Text: '{}'", queryResult.getQueryText());
            log.debug("Detected Intent: {} (confidence: {})",
                    queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
            log.debug("Fulfillment Text: '{}'", queryResult.getFulfillmentText());
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
        }
        return String.format("'%s'", queryResult.getFulfillmentText());
    }


    public Map<String,String> toJsonMap(Map<String, QueryResult> queryResultMap) {
        Map<String, String> map = new HashMap<>();
        if (queryResultMap ==null) {
            return map;
        }

        queryResultMap.forEach((k,v) ->{
            map.put(k, gson.toJson(v));
        });
        return map;
    }
}

