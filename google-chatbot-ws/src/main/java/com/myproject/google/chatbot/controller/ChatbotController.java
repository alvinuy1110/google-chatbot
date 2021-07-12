package com.myproject.google.chatbot.controller;

import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.gson.Gson;
import com.myproject.google.chatbot.service.DialogFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/api")
public class ChatbotController {

    @Autowired
    private DialogFlowService dialogFlowService;

//    @GetMapping("/intent")
//    public ResponseEntity<Map<String, QueryResult>> getIntent(
//                               Model model, HttpServletRequest request,
//                               @RequestParam(value="lang", required = false, defaultValue = "en-CA") String lang) throws IOException {
//
//
//            // TODO dummy inputs
//        List<String> texts = new ArrayList<>();
//        texts.add("Hi");
//        texts.add("I know English");
//
//        texts.add("Bonjour");
//        Map<String, QueryResult>  queryResultMap = dialogFlowService.detectIntentTexts(texts, lang);
//
//        ResponseEntity responseEntity = ResponseEntity.ok(queryResultMap);
//
//        return responseEntity;
//    }

    @GetMapping("/intent")
    public ResponseEntity<Map<String, String>> getIntent(
            Model model, HttpServletRequest request,
            @RequestParam(value="lang", required = false, defaultValue = "en-CA") String lang) throws IOException {


        // TODO dummy inputs
        List<String> texts = new ArrayList<>();
        texts.add("Hi");
        texts.add("I know English");

        texts.add("Bonjour");
        Map<String, QueryResult>  queryResultMap = dialogFlowService.detectIntentTexts(texts, lang);

        Map<String, String> resultMap=  dialogFlowService.toJsonMap(queryResultMap);
        ResponseEntity responseEntity = ResponseEntity.ok(resultMap);

        return responseEntity;
    }
}