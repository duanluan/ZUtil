package top.csaf.img;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import top.csaf.lang.StrUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class ThumbnailUtil {

  /**
   * 指定大小压缩图片为 JPG
   *
   * @param sourcePath       源图片路径
   * @param targetPath       目标图片路径，后缀名不是 jpg 或 jpeg 时会转换成 jpg
   * @param targetSize       指定图片大小，单位 kb
   * @param recursiveQuality 0 < 初始递归压缩的质量 < 1，默认 0.9，此质量递归后仍无法满足指定图片大小时，会递减 0.1 再次尝试
   * @return 压缩后图片大小
   */
  public static long compress(@NonNull final String sourcePath, @NonNull String targetPath, final long targetSize, Double recursiveQuality) {
    if (StrUtil.isBlank(sourcePath) || StrUtil.isBlank(targetPath)) {
      throw new IllegalArgumentException("sourceImgPath or targetImgPath is blank");
    }
    if (!Files.exists(Paths.get(sourcePath))) {
      throw new IllegalArgumentException("sourceImgPath not exists");
    }
    if (targetSize <= 0) {
      throw new IllegalArgumentException("targetSize must be greater than 0");
    }
    if (recursiveQuality == null) {
      recursiveQuality = 0.9;
    } else {
      if (recursiveQuality <= 0 || recursiveQuality >= 1) {
        throw new IllegalArgumentException("recursiveQuality must be greater than 0 and less than 1");
      }
    }
    // targetPath 后缀名不是 jpg 或 jpeg，转换成 jpg
    if (!targetPath.toLowerCase().endsWith(".jpg") && !targetPath.toLowerCase().endsWith(".jpeg")) {
      targetPath = targetPath.substring(0, targetPath.lastIndexOf(".")) + ".jpg";
    }
    try {
      // 先转成目标 jpg
      Thumbnails.of(sourcePath).scale(1).outputQuality(recursiveQuality).toFile(targetPath);
      // 递归压缩
      return recursiveCompressImg(targetPath, targetSize, recursiveQuality, -1);
    } catch (Exception e) {
      log.error("Compress image failed", e);
    }
    return -1;
  }

  /**
   * 指定大小压缩图片为 JPG
   *
   * @param sourcePath 源图片路径
   * @param targetPath 目标图片路径，后缀名不是 jpg 或 jpeg 时会转换成 jpg
   * @param targetSize 指定图片大小，单位 kb
   * @return 压缩后图片大小
   */
  public static long compress(@NonNull final String sourcePath, @NonNull String targetPath, final long targetSize) {
    return compress(sourcePath, targetPath, targetSize, 0.9);
  }

  private static long recursiveCompressImg(String targetPath, long targetSize, double recursiveQuality, long lastSize) throws IOException {
    File targetFile = new File(targetPath);
    long currentTargetSize = targetFile.length();
    // log.info("currentTargetSize:{}", currentTargetSize);
    if (currentTargetSize <= targetSize * 1024) {
      return currentTargetSize;
    }
    // 当前质量已无法继续压缩
    if (currentTargetSize == lastSize) {
      // 质量已经降到 0 仍然无法压缩，返回当前大小
      if (recursiveQuality <= 0) {
        return currentTargetSize;
      }
      // 质量降低 0.1 再次尝试
      recursiveQuality -= 0.1;
      return recursiveCompressImg(targetPath, targetSize, recursiveQuality, -1);
    }
    // 压缩图片
    Thumbnails.of(targetPath).scale(1).outputQuality(recursiveQuality).toFile(targetPath);
    return recursiveCompressImg(targetPath, targetSize, recursiveQuality, currentTargetSize);
  }
}
