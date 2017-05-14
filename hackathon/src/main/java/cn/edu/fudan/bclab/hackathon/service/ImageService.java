package cn.edu.fudan.bclab.hackathon.service;

import java.io.*;

import org.slf4j.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;

import com.google.zxing.*;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.BitMatrix;

@Service
public class ImageService {

	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

	@Cacheable("qr-code-cache")
	public byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {

		Assert.hasText(text);
		Assert.isTrue(width > 0);
		Assert.isTrue(height > 0);
		
		logger.info("Will generate image  text=[{}], width=[{}], height=[{}]", text, width, height);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		MatrixToImageWriter.writeToStream(matrix, MediaType.IMAGE_PNG.getSubtype(), baos, new MatrixToImageConfig());
		return baos.toByteArray();
	}

	@Cacheable("qr-code-cache")
	public ListenableFuture<byte[]> generateQRCodeAsync(String text, int width, int height) throws Exception {
		return new AsyncResult<byte[]>(generateQRCode(text, width, height));
	}

}