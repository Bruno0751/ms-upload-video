package com.dev.ms_upload_video.client.dto;

import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmailDto {

    private Long id;
    private String emailTo;
    private String subject;
    private String text;

}
