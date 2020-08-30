package chap6;

import java.util.ArrayList;
import java.util.List;

/**
 * 单线程串行渲染页面
 *
 * @author jianghao.zhang
 */
public class SingleThreadRenderer {

    void renderPage(CharSequence source) {
        // 渲染文本
        renderText(source);

        // 下载图片(IO操作，耗时严重！)
        List<ImageData> imageDataList = new ArrayList<>();
        for (ImageInfo imageInfo : scanForImageInfo(source)) {
            imageDataList.add(imageInfo.downloadImage());
        }

        // 渲染图片
        for (ImageData data : imageDataList) {
            renderImage(data);
        }
    }

    private void renderImage(ImageData data) {
    }

    private ImageInfo[] scanForImageInfo(CharSequence source) {
        return new ImageInfo[0];
    }

    private void renderText(CharSequence source) {
    }
}
