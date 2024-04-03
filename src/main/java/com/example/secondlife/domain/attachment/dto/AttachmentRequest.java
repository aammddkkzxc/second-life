package com.example.secondlife.domain.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AttachmentRequest {

    private Long attachmentId;
    private Long postId;
    private String originalFileName;
    private String storedFileName;
    private Long fileSize;

}