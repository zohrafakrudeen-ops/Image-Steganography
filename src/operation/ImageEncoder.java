package operation;

import service.LSBSteganographyService;
import service.SteganographyService;

public class ImageEncoder {

    private final SteganographyService service;

    public ImageEncoder() {
        service = new LSBSteganographyService();
    }

    public void encode(String input,
                       String output,
                       String message) throws Exception {

        service.encode(input, output, message);

    }
}