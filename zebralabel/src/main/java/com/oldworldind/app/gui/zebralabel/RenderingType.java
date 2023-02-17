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
    PngImage(new MediaType("image", "png")),    
    Json(new MediaType("application", "json")),
    PdfImage(new MediaType("application","pdf"));
    
    RenderingType(MediaType type) {
        this.type = type;
    }

    private final MediaType type;

    public MediaType getType() {
        return type;
    }
    
    public String getAltPath() {
        if (this == PngImage) {
            return "";
        }
        
        return type.getSubtype();
    }

	@Override
	public String toString() {
		return "RenderingType{type=" + type + '}';
	}
    
}
