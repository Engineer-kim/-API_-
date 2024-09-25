package com.movie.movieinfo.service.user;

import com.movie.movieinfo.Repository.PasswordResetTokenRepository;
import com.movie.movieinfo.Repository.UserRepository;
import com.movie.movieinfo.dto.user.JoinRequestDto;
import com.movie.movieinfo.entity.PasswordResetToken;
import com.movie.movieinfo.entity.User;
import com.movie.movieinfo.exception.UserEmailNotFoundException;
import com.movie.movieinfo.response.CustomResponse;
import com.movie.movieinfo.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    public final String activationUserDiscrimination = "A";

    //패스워드 초기화 만료 시간 (24시간)
    private static final int EXPIRATION = 60 * 24;
    @Transactional
    public ResponseEntity<?> registerNewUserAccount(JoinRequestDto joinRequestDto) {

        // 사용자 ID로 기존 사용자 존재 여부를 확인
        Optional<User> existingUser = userRepository.findById(joinRequestDto.getUserId());
        if (existingUser.isPresent()) {
            // 사용자가 이미 존재하면 400 상태 코드와 메시지를 JSON 형태로 반환
            return ResponseEntity
                    .badRequest()
                    .body(new CustomResponse(400, "해당 아이디로 가입된 사용자가 이미 존재합니다."));
        }

        User user = User.builder()
                .dbSts(activationUserDiscrimination)
                .userName(joinRequestDto.getUserName())
                .userId(joinRequestDto.getUserId())
                .userEmail(joinRequestDto.getUserEmail())
                .password(joinRequestDto.getUserPassword())
                .signDate(LocalDateTime.now())
                .build();

        // 사용자 저장
        userRepository.save(user);
        // 성공 응답 반환
        return ResponseEntity.ok(new CustomResponse(200, "회원가입 성공"));
    }

    public CustomResponse checkIfUserIdExists(String userId) {
        if (userRepository.findById(userId).isPresent() && findActivationUser(userId)) {
            return new CustomResponse(409, "중복된 아이디로 해당 아이디로 가입 불가합니다");
        } else {
            return new CustomResponse(200, "사용 가능한 아이디입니다");
        }
    }

    /**이메일로 유저 아이디 찿기(단건 혹은 다건)*/
    public List<String> findUserByEmail(String email) {
        return userRepository.findByDbStsAndUserEmail(activationUserDiscrimination, email)
                .stream()//[User{id='id1', email='email1@email.com'}, User{id='id2', email='email2@email.com'}]
                .map(User::getUserId)//User{id='id1', email='email1@email.com'}  , User{id='id2', email='email2@email.com'}
                .collect(Collectors.toList()); //("id1", "id2")
    }
    public CustomResponse findUserIdsByEmailResponse(String email) {
        List<String> userIds = findUserByEmail(email);
        if (userIds.isEmpty()) {
            return new CustomResponse(404, "해당 이메일로 등록된 사용자를 찾을 수 없습니다.");
        } else {
            return new CustomResponse(200, userIds.toString());
        }
    }

    /**패스워드 초기화 로직
     *
     *회원가입시 사용한 아이디에 토큰을 1대1 로 발급하고
     * 초기화 설정이 완료되면 발급했던 토큰은 삭제됨
     * 나중에 도메인 (http://localhost:8080) 바꿀것
     * */
    public void sendPasswordResetLink(String userEmail , String userId){
        System.out.println(userRepository.countByUserEmailAndUserId(userEmail ,userId));
        User user = userRepository.findOneByDbStsAndUserEmailAndUserId(activationUserDiscrimination,userEmail, userId)
                .orElseThrow(() -> new UserEmailNotFoundException("이메일에 대한 계정 정보를 찾을 수 없습니다."));

        String token = UUID.randomUUID().toString();
        savePasswordResetToken(token, user);

        String resetLink = "http://localhost:8080/userManagement/resetNewPasswordView?token=" + token;
        emailService.sendEmail(userEmail, resetLink);
    }

    private void savePasswordResetToken(String token, User user) {
        PasswordResetToken myToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(calculateExpiryDate(EXPIRATION)).build();
        System.out.println(myToken.toString());
        passwordResetTokenRepository.save(myToken);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //토큰 발급 받은 당일 날짜 하루종일 토큰 유효하도록
        calendar.add(Calendar.DATE, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        calendar.add(Calendar.SECOND, -1);
        System.out.println("calendar.getTime():::::::::::::::::::"+calendar.getTime());
        return calendar.getTime();
    }

    public boolean isTokenExpired(PasswordResetToken token) {
        Date expiryDate = calculateExpiryDate(EXPIRATION);
        return  token.getExpiryDate().before(new Date());
    }

    public CustomResponse  resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        // 토큰 유효성 검사
        if (passwordResetToken == null || isTokenExpired(passwordResetToken)) {
            return new CustomResponse(400, "토큰이 유효하지않거나 만료 되었습니다.");
        }
        // 토큰에 해당하는 사용자 찾기
        User user = passwordResetToken.getUser();
        if (user == null) {
            return new CustomResponse(404, "해당 토큰에 대응하는 사용자를 찾을 수 없습니다.");
        }

        // 새 비밀번호 설정
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // 토큰 사용 처리(토큰 삭제)
        passwordResetTokenRepository.delete(passwordResetToken);

        return new CustomResponse(200, "비밀번호가 성공적으로 재설정되었습니다.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
        SecurityConfig에서 설정한 .usernameParameter("userId") 로 인해 login.html에서 사용자가 입력한 userId 값이 loadUserByUsername의 username으로 전달됩니다.
        loadUserByUsername은 전달받은 값을 이용해서 User 엔티티를 찾은 후, 이 엔티티를 이용해서 UserDetails 객체를 만들어서 반환합니다.
        그러면 스프링 시큐리티는 이 메서드에서 반환한 객체를 이용해 비밀번호 대조를 한 후 로그인 성공 여부를 결정합니다.
         */
        log.debug("Attempting to load user: " + username);

//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자의 이름으로 가입된 계정이 있습니다: " + username));

        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자의 이름으로 가입된 계정이 있습니다: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserId())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    /**탈퇴한 유저인지 판단하는 메서드
     * dbSts 컬럼으로 소프트 딜리트 형식으로 탈퇴한 회원을 DB에 보관
     * A: 활성화
     * D: 비활성화(화원탈퇴한 유저)
     * */
    private boolean findActivationUser(String userId) {
        Optional<User> result = userRepository.findByUserIdAndDbSts(userId,activationUserDiscrimination);
        System.out.print("result::::::::::::::::" + result.toString());
        if (result.isPresent()) {
            User user = result.get();
            log.info("findActivationUser.user  == "  + user);
            if ("A".equals(user.getDbSts())) {
                return true;
            } else {
                return false;
            }
        } else {
            log.info("없는 사용자 계정 정보 입니다\n DB에 해당 사용자 계정이 있는지 체크 해주세요");
            return  false;
        }
    }

    public boolean deleteUserAccount(String userId) {
        if (findActivationUser(userId)) {
            return userRepository.updateUserDbSts(userId, "D") > 0;
        } else {
            // 활성 상태가 아니면 false 반환
            return false;
        }
    }


}
