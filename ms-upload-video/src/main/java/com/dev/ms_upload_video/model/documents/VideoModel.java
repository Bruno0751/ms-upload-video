package com.dev.ms_upload_video.model.documents;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.time.Instant;

@Document(collection = "videos")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VideoModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @Field(name = "id_video")
    private Long idVideo;

    @Field(name = "name")
    private String name;

    @CreatedDate
    @Field(name = "date_time")
    private Instant dateTime;

    @Field(name = "length")
    private long length;

    @Field(name = "file_path")
    private String filePath;

    @Field(name = "email")
    private String email;

}
