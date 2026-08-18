package service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;

public class LSBSteganographyService implements SteganographyService {

    @Override
    public void encode(String inputImage,
                       String outputImage,
                       String message) throws Exception {

        BufferedImage image = loadImage(inputImage);

        embedMessage(image, message);

        saveImage(image, outputImage);
    }

    @Override
    public String decode(String imagePath) throws Exception {

        BufferedImage image = loadImage(imagePath);

        return extractMessage(image);
    }

    private BufferedImage loadImage(String path) throws Exception {
        return ImageIO.read(new File(path));
    }

    private void saveImage(BufferedImage image, String outputPath) throws Exception {
        ImageIO.write(image, "png", new File(outputPath));
    }

    private void embedMessage(BufferedImage image, String message) {

        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        int messageLength = messageBytes.length;

        int width = image.getWidth();
        int height = image.getHeight();

        int totalBits = 32 + (messageLength * 8);
        int capacity = width * height * 3;

        if (totalBits > capacity) {
            throw new IllegalArgumentException("Message is too large for this image.");
        }

        int bitIndex = 0;

        outer:
        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                int rgb = image.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int[] colors = { red, green, blue };

                for (int i = 0; i < 3; i++) {

                    int bit;

                    if (bitIndex < 32) {

                        bit = (messageLength >> (31 - bitIndex)) & 1;

                    } else {

                        int dataBit = bitIndex - 32;

                        if (dataBit >= messageLength * 8) {
                            break outer;
                        }

                        int byteIndex = dataBit / 8;
                        int bitPosition = 7 - (dataBit % 8);

                        bit = (messageBytes[byteIndex] >> bitPosition) & 1;
                    }

                    colors[i] = (colors[i] & 0xFE) | bit;

                    bitIndex++;
                }

                int newRGB =
                        (0xFF << 24)
                                | (colors[0] << 16)
                                | (colors[1] << 8)
                                | colors[2];

                image.setRGB(x, y, newRGB);
            }
        }
    }

    private String extractMessage(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        int bitIndex = 0;
        int messageLength = 0;

        byte[] messageBytes = null;

        int currentByte = 0;
        int currentBit = 0;
        int byteIndex = 0;

        outer:
        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                int rgb = image.getRGB(x, y);

                int[] colors = {
                        (rgb >> 16) & 0xFF,
                        (rgb >> 8) & 0xFF,
                        rgb & 0xFF
                };

                for (int color : colors) {

                    int bit = color & 1;

                    if (bitIndex < 32) {

                        messageLength = (messageLength << 1) | bit;

                        if (bitIndex == 31) {
                            messageBytes = new byte[messageLength];
                        }

                    } else {

                        currentByte = (currentByte << 1) | bit;
                        currentBit++;

                        if (currentBit == 8) {

                            messageBytes[byteIndex] = (byte) currentByte;

                            byteIndex++;

                            currentByte = 0;
                            currentBit = 0;

                            if (byteIndex == messageLength) {
                                break outer;
                            }
                        }
                    }

                    bitIndex++;
                }
            }
        }

        return new String(messageBytes, StandardCharsets.UTF_8);
    }
}