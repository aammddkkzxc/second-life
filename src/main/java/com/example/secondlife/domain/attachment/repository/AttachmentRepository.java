package com.example.secondlife.domain.attachment.repository;

import com.example.secondlife.domain.attachment.entity.Attachment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findAllByPostId(Long postId);

}
