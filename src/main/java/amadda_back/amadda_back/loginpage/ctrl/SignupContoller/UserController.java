package amadda_back.amadda_back.loginpage.ctrl.SignupContoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import amadda_back.amadda_back.loginpage.dto.SignupDto.UsersFormDto;
import amadda_back.amadda_back.loginpage.service.SignupService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// @CrossOrigin(origins = "*")
@RestController("signupUserController")
@RequestMapping("/ama")

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // private final UsersFormDto usersFormDto ;
    // private final PasswordEncoder passwordEncoder;

    // 회원가입
    @PostMapping(value = "/new")
    public ResponseEntity<?> newUsers(@Valid @RequestBody UsersFormDto usersFormDto) {

        userService.createUsers(usersFormDto);
        String jwt = tokensService.createJwt(usersFormDto.getUser_email());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    // 중복회원 확인
    @PostMapping(value = "/check-duplicate")
    public ResponseEntity<Boolean> duplicateButton(@Valid @RequestBody UsersFormDto usersFormDto) {

        boolean flag = userService.checkDuplicate(usersFormDto.getUser_email());
        System.out.println("flag : " + flag);
        return new ResponseEntity<>(flag, HttpStatus.OK);
    }
}
