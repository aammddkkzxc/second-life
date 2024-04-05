package com.example.secondlife.domain.attachment.controller;

import com.example.secondlife.domain.attachment.dto.AttachmentRequest;
import com.example.secondlife.domain.attachment.dto.AttachmentResponse;
import com.example.secondlife.domain.attachment.service.AttachmentService;
import com.example.secondlife.domain.attachment.util.AttachmentUtil;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentUtil attachmentUtil;

    @PostMapping("/posts/{postId}/files")
    @Operation(summary = "파일 업로드", description = "파일을 업로드합니다.")
    public ResponseEntity<List<AttachmentResponse>> uploadFiles(@PathVariable Long postId,
                                                                @RequestParam("files") List<MultipartFile> files) {
        List<AttachmentRequest> uploadedFiles = attachmentUtil.uploadFiles(files);

        List<AttachmentResponse> responses = attachmentService.saveFiles(postId, uploadedFiles);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/posts/{postId}/files")
    @Operation(summary = "파일 목록 조회", description = "업로드된 파일 목록을 조회합니다.")
    public ResponseEntity<List<AttachmentResponse>> findAllFileByPostId(@PathVariable Long postId) {
        final List<AttachmentResponse> allFileByPostId = attachmentService.findAllFileByPostId(postId);

        return ResponseEntity.ok(allFileByPostId);
    }

    @DeleteMapping("/files/{fileId}")
    @Operation(summary = "파일 삭제", description = "파일을 삭제합니다.")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        attachmentService.delete(fileId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
