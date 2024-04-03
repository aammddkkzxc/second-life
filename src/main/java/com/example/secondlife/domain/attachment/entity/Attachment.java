package com.example.secondlife.domain.attachment.entity;

import com.example.secondlife.common.base.BaseTimeEntity;
import com.example.secondlife.domain.post.entity.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Attachment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalFileName;

    private String storedFileName;

    private String filePath;

    private Long fileSize;

    private boolean isDeleted;

    @Builder
    public Attachment(Long id, Post post, String originalFileName, String storedFileName, Long fileSize) {
        this.id = id;
        this.post = post;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileSize = fileSize;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
