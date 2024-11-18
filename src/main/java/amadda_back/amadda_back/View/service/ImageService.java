package amadda_back.amadda_back.View.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${spring.s3.accessKey}")
    private String accessKey;

    @Value("${spring.s3.secretKey}")
    private String secretKey;

    @Value("${spring.naver.cloud.bucket}")
    private String bucketName;

    private final String endPoint = "https://kr.object.ncloudstorage.com";
    private final String regionName = "kr-standard";

    // S3 client 초기화
    private AmazonS3 createS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    // 파일 업로드
    public List<String> uploadFile(List<MultipartFile> files) {
        List<String> fileUrls = new ArrayList<>();

        // 각 파일에 대해 처리
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            String fileUrl = null;

            try {
                // 파일을 S3에 업로드
                File localFile = convertMultipartFileToFile(file);
                uploadToS3(localFile, fileName);

                // 업로드된 파일의 URL 생성
                fileUrl = "https://" + "kr.object.ncloudstorage.com/" + bucketName + "/" + fileName;
                fileUrls.add(fileUrl); // URL을 리스트에 추가
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileUrls; // 여러 개의 URL 반환
    }

    // 파일을 S3에 업로드하는 메서드
    private void uploadToS3(File file, String fileName) {
        AmazonS3 s3 = createS3Client();

        // 업로드할 파일 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead); // 공개 권한 설정
        putObjectRequest.setMetadata(metadata);

        // 파일을 S3에 업로드
        s3.putObject(putObjectRequest);
    }

    // MultipartFile을 File로 변환하는 메서드
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);
        return tempFile;
    }
}
