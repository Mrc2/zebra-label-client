package com.oldworldind.app.gui.zebralabel;

import javax.ws.rs.core.MediaType;

/**
 * @since  Apr 16, 2018
 * @author mcolegrove
 */
public enum RenderingType {
    
    /**
     *
     */
    PngImage(new MediaType("image", "png")), PdfImage(new MediaType("application","pdf"));
    
    RenderingType(MediaType type) {
        this.type = type;
    }

    private final MediaType type;

    public MediaType getType() {
        return type;
    }

	@Override
	public String toString() {
		return "RenderingType{type=" + type + '}';
	}
    
}
