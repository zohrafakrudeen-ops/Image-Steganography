package service;

public interface SteganographyService {

    void encode(String inputImage,
                String outputImage,
                String message) throws Exception;

    String decode(String imagePath) throws Exception;
}
