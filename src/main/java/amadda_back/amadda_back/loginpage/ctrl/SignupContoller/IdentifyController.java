package amadda_back.amadda_back.loginpage.ctrl.SignupContoller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@RestController
public class IdentifyController {

    final DefaultMessageService messageService;
    private final Map<String, String> phoneNumAndCode = new HashMap<>();

    public IdentifyController() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("NCS5MINUGNHLIDCH", "CBI1C0OSZYEMOTUZCAUUZII3YIFC66N4",
                "https://api.coolsms.co.kr");
    }

    // 메시지 발송
    @PostMapping("/send-one")
    public SingleMessageSentResponse sendOne(@RequestParam("user_phonenumber") String phoneNumber) {
        Message message = new Message();

        String randomCode = generateRandomCode(6);
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01099430536");
        message.setTo(phoneNumber);
        message.setText("인증코드는[" + randomCode + "]");

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            phoneNumAndCode.put(phoneNumber, randomCode);
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;

    }

    // 인증번호 검증 엔드포인트
    @PostMapping("/identify-code")
    public Boolean identifyCode(@RequestParam("user_phonenumber") String phoneNumber,
            @RequestParam("identify_num") String identifyNum) {
        String savedCode = phoneNumAndCode.get(phoneNumber);

        if (savedCode != null && savedCode.equals(identifyNum)) {
            phoneNumAndCode.remove(phoneNumber); // 인증 성공 시 삭제
            return true;
        } else {
            return false;
        }
    }

    private String generateRandomCode(int length) {
        StringBuilder randomCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 0부터 9까지의 랜덤 숫자
            randomCode.append(digit);
        }

        return randomCode.toString();
    }
}
