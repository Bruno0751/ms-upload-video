package com.dev.ms_upload_video.api.controller;

import com.dev.ms_upload_video.api.dto.VideoDto;
import com.dev.ms_upload_video.api.dto.VideoResponseDto;
import com.dev.ms_upload_video.client.GenericClient;
import com.dev.ms_upload_video.client.dto.EmailDto;
import com.dev.ms_upload_video.mapper.VideoMapper;
import com.dev.ms_upload_video.repository.VideoRepositrory;
import com.dev.ms_upload_video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;

@RequiredArgsConstructor
@RestController
public class VideoImpl implements VideoApi {

    private final VideoRepositrory videoRepository;
    private final VideoMapper videoMapper;
    private final VideoService videoService;
    private final GenericClient emailClient;

    @Override
    public Flux<VideoDto> save(VideoDto videoDto, FilePart file) {
        try {
            Files.createDirectories(Paths.get("videos"));
        } catch (IOException e) {
            return Flux.error(e);
        }
        return videoService.save(videoMapper.toEntity(videoDto), file).map(videoMapper::toDto);
    }

    @Override
    public Flux<VideoResponseDto> findAll() {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailTo("brunogressler1@gmail.com");
        emailDto.setText("TESTE");
        emailDto.setSubject("-------------------");

        emailClient.sendEmail(emailDto).subscribe();
        return videoService.findAll().map(videoMapper::toDtoResponse);
    }

    @Override
    public Mono<VideoResponseDto> findById(String id) {
        return videoService.findById(new ObjectId(id)).map(videoMapper::toDtoResponse );
    }

    @Override
    public Mono<Void> delete(String id) {
        return videoService.delete(new ObjectId(id));
    }

    @Override
    public Mono<ResponseEntity<Flux<DataBuffer>>> stream(String id, HttpHeaders headers) {
        return videoRepository.findById(new ObjectId(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Vídeo não encontrado")))
                .flatMap(video -> {
                    try {
                        var path = Paths.get("videos").resolve(video.getName());
                        if (!Files.exists(path)) {
                            return Mono.just(ResponseEntity.notFound().build());
                        }
                        long fileSize = Files.size(path);
                        long start = 0;
                        long end = fileSize - 1;

                        if (!headers.getRange().isEmpty()) {
                            HttpRange range = headers.getRange().get(0);
                            start = range.getRangeStart(fileSize);
                            end = range.getRangeEnd(fileSize);
                        }
                        long contentLength = end - start + 1;

                        Flux<DataBuffer> data = DataBufferUtils.read(
                                path,new DefaultDataBufferFactory(),4096
                        )
                        .skip(start / 4096)
                        .take((contentLength / 4096) + 1);
                        HttpStatus status = headers.getRange().isEmpty() ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT;
                        return Mono.just(
                                ResponseEntity.status(status)
                                        .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                                        .header(HttpHeaders.CONTENT_RANGE,
                                                "bytes " + start + "-" + end + "/" + fileSize)
                                        .body(data)
                        );
                    } catch (IOException e) {
                        return Mono.error(e);
                    }
                });
    }

}
