package com.dev.ms_upload_video.mapper;

import com.dev.ms_upload_video.api.dto.VideoDto;
import com.dev.ms_upload_video.api.dto.VideoResponseDto;
import com.dev.ms_upload_video.model.documents.VideoModel;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    VideoResponseDto toDtoResponse(VideoModel model);

    VideoModel toEntity(VideoDto dto);

    VideoDto toDto(VideoModel entityv);

    default String map(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }

}
