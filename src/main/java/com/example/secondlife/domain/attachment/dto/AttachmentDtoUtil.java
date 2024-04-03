package com.example.secondlife.domain.attachment.dto;

import com.example.secondlife.domain.attachment.entity.Attachment;

public class AttachmentDtoUtil {

    public static AttachmentResponse attachmentToAttachmentResponse(Attachment attachment) {
        return AttachmentResponse.builder()
                .attachmentId(attachment.getId())
                .postId(attachment.getPost().getId())
                .originalFileName(attachment.getOriginalFileName())
                .storedFileName(attachment.getStoredFileName())
                .fileSize(attachment.getFileSize())
                .isDeleted(attachment.isDeleted())
                .createdDate(attachment.getCreatedDate())
                .build();
    }

}
