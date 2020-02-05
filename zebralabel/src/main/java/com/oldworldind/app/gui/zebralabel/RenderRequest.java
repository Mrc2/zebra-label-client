package com.oldworldind.app.gui.zebralabel;

/**
 * @author mcolegrove
 * @since Apr 16, 2018
 */
public final class RenderRequest {

    private int height;
    private int width;
    private int rotation;
    private RenderingType renderingType;
    private String fileNamePrefix;

    public static RenderRequest getPdfRequest(int height, int width, String mylabel) {
        return new RenderRequest(height, width, 0, mylabel, RenderingType.PdfImage);
    }

    public static RenderRequest getPngRequest(int height, int width, String mylabel) {
        return new RenderRequest(height, width, 0, mylabel, RenderingType.PngImage);
    }

    public static RenderRequest getPdfRequest(int height, int width, int rotation, String mylabel) {
        return new RenderRequest(height, width, rotation, mylabel, RenderingType.PdfImage);
    }

    public static RenderRequest getPngRequest(int height, int width, int rotation, String mylabel) {
        return new RenderRequest(height, width, rotation, mylabel, RenderingType.PngImage);
    }

    private RenderRequest(int height, int width, int rotation, String mylabel, RenderingType renderingType) {
        this.height = height;
        this.width = width;
        this.rotation = rotation;
        this.fileNamePrefix = mylabel;
        this.renderingType = renderingType;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getRotation() {
        return rotation;
    }

    public RenderingType getRenderingType() {
        return renderingType;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

}
