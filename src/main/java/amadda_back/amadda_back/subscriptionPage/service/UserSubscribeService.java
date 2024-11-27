package amadda_back.amadda_back.subscriptionPage.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import amadda_back.amadda_back.subscriptionPage.dao.UserSubscribeRepository;

import amadda_back.amadda_back.subscriptionPage.domain.entity.SubscribeUser;

@Service
public class UserSubscribeService {

    @Autowired
    private UserSubscribeRepository userRepository;

    private static final String SUBSCRIPTION_ACTIVE = "Y";
    private static final String SUBSCRIPTION_INACTIVE = "N";
    private static final int SUBSCRIPTION_COST = 150;

    // 유저 찾기 메서드
    private SubscribeUser findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    // 구독 확인
    public boolean checkSubscription(Integer userId) {
        SubscribeUser user = findUserById(userId);
        return SUBSCRIPTION_ACTIVE.equals(user.getSubscription());
    }

    // 구독 요청
    public String subscribe(Integer userId) {
        // 1. 유저 찾기
        SubscribeUser user = findUserById(userId);

        // 2. 코인 잔액 확인
        if (user.getCurrencyBalance() < SUBSCRIPTION_COST) {
            throw new IllegalArgumentException("보유한 코인이 부족합니다. (필요 코인: 150)");
        }
    
        // 3. 이미 구독 중인지 확인
        if (SUBSCRIPTION_ACTIVE.equals(user.getSubscription())) {
            throw new IllegalArgumentException("이미 구독 중입니다.");
        }
    
        // 4. 구독 처리
        user.setCurrencyBalance(user.getCurrencyBalance() - SUBSCRIPTION_COST);
        user.setSubscription(SUBSCRIPTION_ACTIVE);
        user.setSubscriptionDate(LocalDateTime.now());
        
        // 5. 저장
        userRepository.save(user);
        return "구독이 성공적으로 완료되었습니다!";
    }

    // 구독 취소
    public String unsubscribe(Integer userId) {
        SubscribeUser user = findUserById(userId);
        
        // 구독 상태 확인
        if(!SUBSCRIPTION_ACTIVE.equals(user.getSubscription())) {
            throw new IllegalArgumentException("구독 상태가 아닙니다.");
        }

        user.setSubscription(SUBSCRIPTION_INACTIVE);
        user.setSubscriptionDate(null);
        userRepository.save(user);

        return "구독이 성공적으로 취소되었습니다.";
    }
}
