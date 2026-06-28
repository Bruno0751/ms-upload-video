package com.dev.ms_upload_video.service;

import com.dev.ms_upload_video.model.documents.VideoModel;
import org.bson.types.ObjectId;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VideoService {

    Flux<VideoModel> findAll();

    Mono<Void> delete(ObjectId id);

    Flux<VideoModel> save(VideoModel videoModel, FilePart file);

    Mono<VideoModel> findById(ObjectId id);
}
