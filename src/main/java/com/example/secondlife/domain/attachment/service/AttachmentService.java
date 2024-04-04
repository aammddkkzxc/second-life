package com.example.secondlife.domain.attachment.service;

import com.example.secondlife.common.exception.NotFoundException;
import com.example.secondlife.domain.attachment.dto.AttachmentDtoUtil;
import com.example.secondlife.domain.attachment.dto.AttachmentRequest;
import com.example.secondlife.domain.attachment.dto.AttachmentResponse;
import com.example.secondlife.domain.attachment.entity.Attachment;
import com.example.secondlife.domain.attachment.repository.AttachmentRepository;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.service.PostSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachmentService {

    private final PostSearchService postSearchService;
    private final AttachmentRepository attachmentRepository;

    @Transactional
    public List<AttachmentResponse> saveFiles(Long postId, List<AttachmentRequest> requests) {
        if (CollectionUtils.isEmpty(requests)) {
            throw new NotFoundException("첨부파일이 존재하지 않습니다.");
        }

        for (AttachmentRequest request : requests) {
            request.setPostId(postId);
        }

        List<Attachment> attachments = requests
                .stream()
                .map(this::attachmentRequestToAttachment)
                .toList();

        List<Attachment> savedAttachments = attachmentRepository.saveAll(attachments);

        return savedAttachments.stream()
                .map(AttachmentDtoUtil::attachmentToAttachmentResponse)
                .toList();
    }

    public Attachment findById(Long fileId) {
        return attachmentRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("첨부파일이 존재하지 않습니다."));
    }

    public List<AttachmentResponse> findAllFileByPostId(final Long postId) {
        final List<Attachment> allByPostId = attachmentRepository.findAllByPostId(postId);

        return allByPostId.stream()
                .map(AttachmentDtoUtil::attachmentToAttachmentResponse)
                .toList();
    }

    public void delete(Long fileId) {
        findById(fileId).delete();
    }

    private Attachment attachmentRequestToAttachment(AttachmentRequest attachmentRequest) {
        Post findPost = postSearchService.findById(attachmentRequest.getPostId());

        return Attachment.builder()
                .id(attachmentRequest.getAttachmentId())
                .post(findPost)
                .originalFileName(attachmentRequest.getOriginalFileName())
                .storedFileName(attachmentRequest.getStoredFileName())
                .fileSize(attachmentRequest.getFileSize())
                .build();
    }
}
