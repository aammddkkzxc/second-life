package com.example.secondlife.domain.attachment.util;

import com.example.secondlife.domain.attachment.dto.AttachmentRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AttachmentUtil {

    private final String uploadPath = Paths.get("C:", "develop", "upload-files").toString();

    public List<AttachmentRequest> uploadFiles(final List<MultipartFile> multipartFiles) {
        List<AttachmentRequest> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(uploadFile(multipartFile));
        }
        return files;
    }

    public AttachmentRequest uploadFile(final MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadPath = getUploadPath(today) + File.separator + saveName;
        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return AttachmentRequest.builder()
                .originalFileName(multipartFile.getOriginalFilename())
                .storedFileName(today + File.separator + saveName)
                .fileSize(multipartFile.getSize())
                .build();
    }

    private String generateSaveFilename(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

    private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    }

    private String makeDirectories(final String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IllegalStateException("디렉토리 생성에 실패했습니다. path = " + path);
            }
        }
        return dir.getPath();
    }
}
