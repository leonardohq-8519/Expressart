package org.project.expressart.ArchivoPost.domain;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ArchivoPost.infrastructure.ArchivoPostRepository;
import org.project.expressart.exceptions.InvalidFileException;
import org.project.expressart.exceptions.S3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.region}")
    private String region;

    private static final Set<String> ALLOWED_IMG_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif"
    );

    private static final Set<String> ALLOWED_TYPES_IN_CHAT = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif", "application/pdf"
    );

    private static final long MAX_SIZE = 50*1024*1024;

    public String uploadImagePost(MultipartFile file, Long postId){
        validateImage(file);
        return upload(file, "posts/" + postId);
    }

    public String uploadImageCommission(MultipartFile file, Long commissionId){
        validateImage(file);
        return upload(file, "commissions/" + commissionId);
    }

    public String uploadFileChat(MultipartFile file, Long chatId){
        validateFileChat(file);
        return upload(file, "chats/" + chatId);
    }

    public String uploadAvatar(MultipartFile file, Long userId){
        validateImage(file);
        return upload(file, "avatar/" + userId);
    }

    public void delete(String url){
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(extractKey(url)).build());
    }

    private String upload(MultipartFile file, String folder){
        String key = folder + "/" + UUID.randomUUID() + "_" + cleanName(file.getOriginalFilename());
        s3Client.putObject(PutObjectRequest.builder().bucket(bucket).key(key).contentType(file.getContentType()).build(),
                RequestBody.fromBytes(getBytesSafe(file)
                ));

        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
    }

    private byte[] getBytesSafe(MultipartFile file){
        try{
            return file.getBytes();
        } catch (IOException e) {
            throw new S3Exception("Error reading file bytes before S3 upload", e);
        }
    }

    private void validateImage(MultipartFile file){
        if (file.isEmpty())
            throw new InvalidFileException("Blank file");
        if (file.getSize()>MAX_SIZE)
            throw new InvalidFileException("File exceeds 50MB limit");
        if (!ALLOWED_IMG_TYPES.contains(file.getContentType()))
            throw new InvalidFileException("File type not allowed. File must be: JPEG, PNG, WEBP, GIF");
    }

    private void validateFileChat(MultipartFile file){
        if (file.isEmpty())
            throw new InvalidFileException("Blank file");
        if (file.getSize()>MAX_SIZE)
            throw new InvalidFileException("File exceeds 50MB limit");
        if (!ALLOWED_TYPES_IN_CHAT.contains(file.getContentType()))
            throw new InvalidFileException("File type not allowed. File must be: JPEG, PNG, WEBP, GIF, PDF");
    }

    private String cleanName(String name){
        if (name == null) return "file";
        return name.toLowerCase().replaceAll("[^a-z0-9._-]", "_");
    }

    private String extractKey(String url){
        return url.substring(url.indexOf(".amazonaws.com/") + 15);
    }
}
