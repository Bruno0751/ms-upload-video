package com.dev.ms_upload_video.repository;

import com.dev.ms_upload_video.model.documents.VideoModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepositrory extends ReactiveMongoRepository<VideoModel, ObjectId> {
}
