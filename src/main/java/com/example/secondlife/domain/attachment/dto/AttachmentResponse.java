package com.example.secondlife.domain.attachment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentResponse {

    private Long attachmentId;
    private Long postId;
    private String originalFileName;
    private String storedFileName;
    private Long fileSize;
    private boolean isDeleted;
    private LocalDateTime createdDate;

}
