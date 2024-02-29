package com.sparrowwallet.bokmakierie;

import boofcv.abst.fiducial.QrCodeDetector;
import boofcv.alg.fiducial.qrcode.QrCode;
import boofcv.factory.fiducial.ConfigQrCode;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.factory.filter.binary.ConfigThreshold;
import boofcv.factory.filter.binary.ThresholdType;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayU8;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Bokmakierie {
    private final QrCodeDetector<GrayU8> detector;

    public Bokmakierie() {
        ConfigQrCode config = new ConfigQrCode();
        ConfigThreshold configThreshold = ConfigThreshold.local(ThresholdType.LOCAL_MEAN,15);
        configThreshold.scale = 1.00;
        config.threshold = configThreshold;
        this.detector = FactoryFiducial.qrcode(config, GrayU8.class);
    }

    public Bokmakierie(ConfigThreshold configThreshold) {
        ConfigQrCode config = new ConfigQrCode();
        config.threshold = configThreshold;
        this.detector = FactoryFiducial.qrcode(config, GrayU8.class);
    }

    public Bokmakierie(ConfigQrCode config) {
        this.detector = FactoryFiducial.qrcode(config, GrayU8.class);
    }

    public Result scan(BufferedImage bufferedImage) {
        GrayU8 gray = ConvertBufferedImage.convertFrom(bufferedImage, (GrayU8)null);
        detector.process(gray);
        List<QrCode> results = detector.getDetections();
        if(!results.isEmpty()) {
            QrCode qrCode = results.get(0);
            return new Result(qrCode.message, qrCode.rawbits, qrCode.version);
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        if(args.length < 1) {
            System.err.println("An image file must be specified");
        }

        BufferedImage image = ImageIO.read(new File(args[0]));

        Bokmakierie bokmakierie = new Bokmakierie();
        Result result = bokmakierie.scan(image);

        if(result != null) {
            System.out.println(result.getMessage());
        }
    }
}
