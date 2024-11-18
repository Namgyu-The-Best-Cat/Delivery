package com.bestcat.delivery.ai.service;

import com.bestcat.delivery.ai.dto.AiRequestDto;
import com.bestcat.delivery.ai.dto.AiResponseDto;
import com.bestcat.delivery.ai.entity.AiLog;
import com.bestcat.delivery.ai.repository.AiRepository;
import com.bestcat.delivery.user.entity.RoleType;
import com.bestcat.delivery.user.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    private final WebClient webClient;
    private final AiRepository aiRepository;

    @Value("${gemini.key}")
    private String secretKey;

    public ResponseEntity<AiResponseDto> createDescription(AiRequestDto requestDto, User user) {

//        if ( !user.getRole().equals(RoleType.OWNER) ) {
//            throw new IllegalArgumentException("메뉴 설명 생성 권한이 없습니다.");
//        }

        List<String> responseFromAi = requestToAi(requestDto.name());

        if (responseFromAi.isEmpty() || responseFromAi.size() < 2) {
            throw new IllegalArgumentException("Ai 설명 생성에 오류가 발생했습니다.");
        }

        AiLog aiLog = AiLog.builder()
                .reqText(responseFromAi.get(0))
                .respText(responseFromAi.get(1))
                .build();

        System.out.println("🍓🍓 Request : " + responseFromAi.get(0));
        System.out.println("Length = " + responseFromAi.get(0).length());
        System.out.println("🍓🍓 Response : " + responseFromAi.get(1));
        System.out.println("Length = " + responseFromAi.get(1).length());

        aiRepository.save(aiLog);
        return ResponseEntity.ok().body(new AiResponseDto(aiLog.getRespText().split("\\^")));
    }

    private List<String> requestToAi(String requestMsg){

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + secretKey;

        String template = """
                %s 상품의 설명을 추천해주는데 해당 조건을 반드시 따라줘
                    1. %d가지를 추천해줘
                    2. 각 설명은 %d자 이하로 제안해줘
                    3. 한 줄로 대답하되 구분자를 '^'를 사용하여 응답 문자열을 나누기 쉽게 해줘
                    4. 응답의 총 길이는 255자를 넘어가면 안돼
                """;

        int maxLength = 50;
        int resCount = 3;

        String reqStr = String.format(template, requestMsg, resCount, maxLength);

        Map<String, Object> body = new HashMap<>();
        Map<String, Object> contentMap = new HashMap<>();
        Map<String, Object> partMap = new HashMap<>();

        partMap.put("text", reqStr);
        contentMap.put("parts", List.of(partMap));
        body.put("contents", List.of(contentMap));


        List<String> reqAndRes = new ArrayList<>();
        reqAndRes.add(reqStr);
        // POST 요청 보내기
        reqAndRes.add(
                extractText(
                    webClient.post()
                        .uri(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block()  // 비동기 호출을 동기적으로 변환하여 결과를 기다림
                )
        );

        return reqAndRes;
    }

    private String extractText(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            // text 필드로 접근하여 값 추출
            return root.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            throw new IllegalArgumentException("AI 요청 응답에 오류가 발생했습니다.");
        }
    }

}
