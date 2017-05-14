package cn.edu.fudan.bclab.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.fudan.bclab.hackathon.service.ImageService;

@EnableAsync
@EnableCaching
@RestController
@RequestMapping("/qr")
public class QRController {

	@Autowired
	ImageService imageService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public ListenableFuture<byte[]> getQRCode(@RequestParam(value = "text", required = true) String text) {
		try {
			return imageService.generateQRCodeAsync(text, 256, 256);
		} catch (Exception ex) {
			throw new InternalServerError("Error while generating QR code image.", ex);
		}
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public class InternalServerError extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public InternalServerError(final String message, final Throwable cause) {
			super(message);
		}

	}

}
