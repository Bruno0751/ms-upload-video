package com.dev.ms_upload_video.client;

import com.dev.ms_upload_video.client.dto.EmailDto;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GenericClient {

    @Value("${spring.mail.username}")
    private String emailFrom;
    private static final Logger log = LoggerFactory.getLogger(GenericClient.class);
    final JavaMailSender javaMailSender;
    private final WebClient emailWebClient;

    public GenericClient(JavaMailSender javaMailSender, WebClient emailWebClient) {
        this.javaMailSender = javaMailSender;
        this.emailWebClient = emailWebClient;
    }

//    public Mono<Void> sendEmail(EmailDto rmailDto) {
//        return emailWebClient.post()
//                .uri("/v1/email")
//                .bodyValue(rmailDto)
//                .retrieve()
//                .bodyToMono(Void.class)
//                .doOnSuccess(v -> log.info("Solicitação de e-mail enviada"))
//                .doOnError(e -> log.error("Erro ao chamar ms-email", e))
//                .onErrorResume(e -> Mono.empty());
//    }

    public Mono<EmailDto> sendEmail(EmailDto emailDto) {
        return Mono.fromCallable(() -> {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(this.emailFrom);
            helper.setTo(emailDto.getEmailTo());
            helper.setText(emailDto.getText(), true);
            helper.setSubject(emailDto.getSubject());
            javaMailSender.send(mimeMessage);
            log.info("E-mail enviado com sucesso para {}", emailDto.getEmailTo());
            return emailDto;
        }).onErrorResume(e -> {
            log.error("Erro ao enviar e-mail: {}", e.getMessage());
            return Mono.just(emailDto);
        });
    }
}
