package com.movie.movieinfo.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {


    private final JavaMailSender mailSender;

    public void sendEmail(String userEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("비밀번호 재설정");
        message.setText("비밀번호를 재설정하려면 다음 링크를 클릭하세요: " + resetLink);

        try {
            mailSender.send(message);
            log.info("비밀번호 재설정 이메일이 성공적으로 전송되었습니다. 수신자: {}", userEmail);
        } catch (MailException e) {
            log.error("비밀번호 재설정 이메일 전송에 실패했습니다. 수신자: {}, 오류: {}", userEmail, e.getMessage());
            // 필요하다면, 여기에서 사용자 정의 예외를 던지거나, 다른 방식으로 오류를 처리할 수 있습니다.
        }
    }
}