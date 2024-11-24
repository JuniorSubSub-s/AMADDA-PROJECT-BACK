package amadda_back.amadda_back.OpenAI.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import amadda_back.amadda_back.OpenAI.dto.OpenAIRequest;
import amadda_back.amadda_back.OpenAI.service.OpenAIService;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateContent(@RequestBody OpenAIRequest request) {
        try {
            String content = openAIService.generateContent(request);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("AI 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
