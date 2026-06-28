package com.dev.ms_upload_video.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VideoDto {

        @Positive(message = "O idVideo é inválido")
        long idVideo;
        @NotBlank(message = "O campo name é obrigatório")
        String name;
        @Positive(message = "O length é inválido")
        long length;
        String email;

}