package com.sportsmatch.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

  /**
   * Compresses the given image data using the DEFLATE algorithm.
   *
   * @param data The image data to compress.
   * @return The compressed image data.
   */
  public static byte[] compressImage(byte[] data) {

    // Creating deflater object
    Deflater deflater = new Deflater();
    deflater.setLevel(Deflater.BEST_COMPRESSION); // setting compression level to the best compression
    deflater.setInput(data); // setting input data for compression
    deflater.finish(); // finishing compression

    // Creating output stream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[4 * 1024]; // creating temporary buffer
    while (!deflater.finished()) {
      int size = deflater.deflate(tmp); // Compressing data and writing it to the output stream
      outputStream.write(tmp, 0, size);
    }
    try {
      outputStream.close(); // Closing the output stream
    } catch (Exception expection) { // Handling in case of error
    }
    return outputStream.toByteArray(); // Returning compressed data as byte array
  }


  /**
   * Decompresses the given compressed image data using the DEFLATE algorithm.
   *
   * @param data The compressed image data to decompress.
   * @return The decompressed image data.
   */
  public static byte[] decompressImage(byte[] data) {

    // Creating inflater object
    Inflater inflater = new Inflater();
    inflater.setInput(data); // Setting input data for decompression

    // Creating output stream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[4 * 1024]; // Creating temporary buffer
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(tmp); // Decompressing compressed data and writing it to the output stream
        outputStream.write(tmp, 0, count);
      }
      outputStream.close(); // Closing the output stream
    } catch (Exception expection) { // Handling in case of error
    }
    // Returning decompressed data as byte array
    return outputStream.toByteArray();
  }
}

