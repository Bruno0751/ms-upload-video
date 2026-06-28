package com.dev.ms_upload_video.api.dto;

public record VideoResponseDto (
    String id,
    Long idVideo,
    String name,
    long length,
    String email
) {}
