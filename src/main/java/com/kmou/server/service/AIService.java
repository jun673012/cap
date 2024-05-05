package com.kmou.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.StreamSupport;

@Service
public class AIService {

    public String analyzeImage(MultipartFile image) throws JsonProcessingException {
        final String url = "http://capstone.includer.site/predict";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            body.add("file", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });
        } catch (IOException e) {
            return "File conversion error";
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> tmp = restTemplate.postForEntity(url, requestEntity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(tmp.getBody());
        String response = "";

        if (root.isArray()) {
            response = StreamSupport.stream(root.spliterator(), false)
                    .flatMap(array -> StreamSupport.stream(array.spliterator(), false))
                    .filter(item -> item.has("class_name"))
                    .map(item -> item.get("class_name").asText())
                    .findFirst()
                    .orElse("No class_name found");
        }

        return response;
    }

}
