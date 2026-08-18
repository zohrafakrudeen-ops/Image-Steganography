package operation;

import service.LSBSteganographyService;
import service.SteganographyService;

public class ImageDecoder {

    private final SteganographyService service;

    public ImageDecoder() {
        service = new LSBSteganographyService();
    }

    public String decode(String imagePath) throws Exception {

        return service.decode(imagePath);

    }

}