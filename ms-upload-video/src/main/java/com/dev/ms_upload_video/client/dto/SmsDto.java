package com.dev.ms_upload_video.client.dto;

import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SmsDto {

    private String phoneTo;
    private String message;

}
