package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.post.enumType.Forum;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
public class PostRequestWithFile {

    private Long postId;
    private String title;
    private String contents;
    private Forum forum;
    private List<MultipartFile> files;

}
