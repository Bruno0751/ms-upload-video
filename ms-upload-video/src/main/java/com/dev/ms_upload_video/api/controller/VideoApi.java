package com.dev.ms_upload_video.api.controller;

import com.dev.ms_upload_video.api.dto.VideoDto;
import com.dev.ms_upload_video.api.dto.VideoResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Videos", description = "Operações relacionadas a vídeos")
@RequestMapping("/v1/video")
public interface VideoApi {

    @Operation(summary = "Cadastrar Video", description = "Forneça dados válido para cadastrar Video.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vídeo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    Flux<VideoDto> save(@Valid @RequestPart("metadata") VideoDto videoDto, @RequestPart("file") FilePart file);

    @Operation(summary = "Buscar videos", description = "Retorno todos os videos cadastrados.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    Flux<VideoResponseDto> findAll();

    @Operation(summary = "Buscar videos", description = "Retorno todos os videos cadastrados.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    Mono<VideoResponseDto> findById(@PathVariable String id);

    @Operation(summary = "Deletar video", description = "Forneça um ID válido para deletar video.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vídeo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vídeo não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    Mono<Void> delete(@PathVariable String id);

    @GetMapping(value = "/stream/{id}", produces = "video/mp4")
//    @GetMapping(value = "/stream/{id}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    Mono<ResponseEntity<Flux<DataBuffer>>> stream(@PathVariable String id, @RequestHeader HttpHeaders headers);

}
