package amadda_back.amadda_back.View.domain.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OCRResponseDTO {

    private String version;
    private String requestId;
    private Long timestamp;
    private List<OCRImage> images;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OCRImage {

        private String uid;
        private String name;
        private String inferResult;
        private String message;
        private ValidationResult validationResult;
        private ConvertedImageInfo convertedImageInfo;
        private List<Field> fields;

        // Getters and setters
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ValidationResult {

        private String result;

        // Getters and setters
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConvertedImageInfo {

        private int width;
        private int height;
        private boolean longImage;
        private int pageIndex;

        // Getters and setters
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Field {

        private String valueType;
        private BoundingPoly boundingPoly;
        private String inferText;
        private float inferConfidence;

        // Getters and setters
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BoundingPoly {

        private List<Vertex> vertices;

        // Getters and setters
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Vertex {

        private float x;
        private float y;

        // Getters and setters
    }
}
