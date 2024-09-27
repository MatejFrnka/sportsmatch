package com.sportsmatch.services;

import com.sportsmatch.models.Image;
import com.sportsmatch.repositories.ImageRepository;
import com.sportsmatch.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ImageService {

  private final ImageRepository imageRepository;

  /**
   * Upload an image file to the system.
   *
   * @param file image file to upload.
   * @return ID of the uploaded image.
   * @throws IOException if an I/O error occurs while reading the file.
   */
  public Long uploadImage(MultipartFile file) throws IOException {
    Image newImage = Image.builder()
        .name(file.getOriginalFilename())
        .type(file.getContentType())
        .imageData(ImageUtils.compressImage(file.getBytes())).build();

    imageRepository.save(newImage);
    return newImage.getId();
  }

  /**
   * Download an image.
   *
   * @param id ID of the image to download.
   * @return the downloaded Image object.
   * @throws NoSuchElementException if no image with the given ID exists.
   */
  public Image downloadImage(Long id) {
    return imageRepository.findById(id).orElseThrow();
  }

  /**
   * Deletes an image.
   *
   * @param id ID of the image to delete.
   */
  public void deleteImage(Long id) {
    imageRepository.deleteById(id);
  }
}
