package com.dev.ms_upload_video.service;

import com.dev.ms_upload_video.model.documents.VideoModel;
import com.dev.ms_upload_video.repository.VideoRepositrory;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;
import org.springframework.core.io.buffer.DataBufferUtils;

@RequiredArgsConstructor
@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepositrory videoRepository;

    @Override
    public Flux<VideoModel> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public Mono<Void> delete(ObjectId id) {
        return videoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Vídeo não encontrado")))
                .flatMap(video -> {
                    try {
                        Path path = Paths.get(video.getFilePath());
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        return Mono.error(new RuntimeException("Erro ao deletar arquivo", e));
                    }
                    return videoRepository.deleteById(id);
                });
    }

    @Override
    public Flux<VideoModel> save(VideoModel videoModel, FilePart file) {
        final String newFile = UUID.randomUUID() + "_" + file.filename();
        Path path = Paths.get("videos/" + newFile);
        videoModel.setName(newFile);
        videoModel.setFilePath(path.toString());
        videoModel.setDateTime(Instant.now());
        return DataBufferUtils.write(file.content(), path)
                .then(videoRepository.save(videoModel))
                .flux();
    }

    @Override
    public Mono<VideoModel> findById(ObjectId id) {
        return videoRepository.findById(id);
    }

}
