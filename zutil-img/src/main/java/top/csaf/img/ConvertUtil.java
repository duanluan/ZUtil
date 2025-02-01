package top.csaf.img;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class ConvertUtil {

  /**
   * SVG 转 PNG/JPG/TIFF，转换后文件已存在时会覆盖
   *
   * @param svgPath       SVG 文件路径
   * @param targetImgPath PNG 文件路径
   * @param pngWidth      PNG 宽度
   * @param pngHeight     PNG 高度
   * @param dpi           DPI（每英寸像素数）
   * @return PNG 文件大小
   */
  public static long svg2img(@NonNull final String svgPath, @NonNull final String targetImgPath, final float pngWidth, final float pngHeight, final float dpi) {
    // 创建 PNG 转换器
    ImageTranscoder transcoder;
    // 获取文件后缀
    String suffix = targetImgPath.substring(targetImgPath.lastIndexOf(".") + 1).toLowerCase();
    switch (suffix) {
      case "png":
        transcoder = new PNGTranscoder();
        break;
      case "jpg":
      case "jpeg":
        transcoder = new JPEGTranscoder();
        // 设置 JPEG 质量
        transcoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 1f);
        break;
      case "tiff":
      case "tif":
        transcoder = new TIFFTranscoder();
        break;
      default:
        throw new IllegalArgumentException("Unsupported image format: " + suffix);
    }
    // 宽度
    transcoder.addTranscodingHint(ImageTranscoder.KEY_WIDTH, pngWidth);
    // 高度
    transcoder.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, pngHeight);
    // 设置 DPI（一英寸多少像素），25.4 表示 1 英寸等于 25.4 毫米
    transcoder.addTranscodingHint(ImageTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, 25.4f / dpi);

    try (
      InputStream inputStream = Files.newInputStream(Paths.get(svgPath));
      FileOutputStream outputStream = new FileOutputStream(targetImgPath)
    ) {
      // 创建输入源（支持 File、InputStream 或 URI）
      TranscoderInput input = new TranscoderInput(inputStream);
      // 创建输出流
      TranscoderOutput output = new TranscoderOutput(outputStream);
      // 执行转换
      transcoder.transcode(input, output);
      return Paths.get(targetImgPath).toFile().length();
    } catch (IOException | TranscoderException e) {
      log.error("Convert SVG to PNG failed", e);
    }
    return -1;
  }

  /**
   * SVG 转 JPG，JPG 文件已存在时会覆盖
   *
   * @param svgPath        SVG 文件路径
   * @param jpgPath        JPG 文件路径，后缀名不是 jpg 或 jpeg 时会在后面加上 .jpg
   * @param jpgWidth       JPG 宽度
   * @param jpgHeight      JPG 高度
   * @param dpi            DPI（每英寸像素数）
   * @param maxJpgFileSize 最大 JPG 文件大小，超过此大小会压缩，单位 kb
   * @return JPG 文件大小
   */
  public static long svg2jpg(String svgPath, String jpgPath, float jpgWidth, float jpgHeight, float dpi, long maxJpgFileSize) {
    // 后缀名不是 jpg 或 jpeg 时会在后面加上 .jpg
    if (!jpgPath.toLowerCase().endsWith(".jpg") && !jpgPath.toLowerCase().endsWith(".jpeg")) {
      jpgPath += ".jpg";
    }
    long jpgFileSize = svg2img(svgPath, jpgPath, jpgWidth, jpgHeight, dpi);
    if (jpgFileSize <= maxJpgFileSize) {
      return jpgFileSize;
    }
    return ThumbnailUtil.compress(jpgPath, jpgPath, maxJpgFileSize, 0.9);
  }
}
