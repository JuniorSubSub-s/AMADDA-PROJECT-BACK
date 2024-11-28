package amadda_back.amadda_back.loginpage.dto.SignupDto;

import java.util.Date;

import amadda_back.amadda_back.loginpage.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 파라미터가 없는 기본생성자 추가 public MyClass() {}
@AllArgsConstructor // 모든 필드를 포함하는 생성자
/*
 * public class MyClass {
 * private String field1;
 * private int field2;
 * }
 */
@Builder(toBuilder = true)
public class UsersFormDto {
  private String user_email;

  private String user_pwd;

  private String user_nickname;

  private String user_phonenumber;

  private Gender user_gender;

  private String user_name;

  private Integer user_nation;

  private Date user_birth;

  private String user_refresh_token;

  private String user_access_token;

  private Date user_expires_in;

}
