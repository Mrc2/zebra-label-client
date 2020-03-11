package com.oldworldind.app.gui.zebralabel;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 * @author mcolegrove
 * @since Apr 16, 2018
 */
public class RenderZebraSvc {
    private static final Logger LOG = LogManager.getLogger(RenderZebraSvc.class);

    private static final String URL = "http://api.labelary.com/v1/printers/8dpmm/labels/";

    String replaceCharFakeh(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        String result = source.replaceAll("®", "^");
        return result;
    }

    String replaceCharQuote(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        String result = source.replaceAll("¨", "^");
        return result;
    }

    public File renderCleansedRequest(RenderRequest renderReq, String labelContent) {
        String cleanContent = replaceCharQuote(replaceCharFakeh(labelContent));
        if (isBlank(cleanContent)) {
            LOG.error("cannot render content was null:" + cleanContent);
            return null;
        }
        return renderRequest(renderReq, cleanContent);
    }

    public File renderRequest(RenderRequest renderReq, String labelContent) {

        if (isBlank(labelContent)) {
            LOG.error("cannot render content was null:" + labelContent);
            return null;
        }
//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.register(MultiPartFeature.class);
//        Client client = ClientBuilder.newClient(clientConfig);
        Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

        String path = URL + renderReq.getWidth() + "x" + renderReq.getHeight() + "/0/";
		LOG.info("run label vs:" + path + " as type:" + renderReq.getRenderingType());
        WebTarget target = client.target(path);
        Invocation.Builder request = target.request();
        request.accept(renderReq.getRenderingType().getType());
        Response response = request.post(Entity.entity(labelContent, APPLICATION_FORM_URLENCODED));
        if (200 == response.getStatus()) {
            byte[] body = response.readEntity(byte[].class);
            String fileName = toFileName(renderReq);
            File file = new File(fileName);
            try {
                Files.write(file.toPath(), body);
            } catch (IOException ex) {
                LOG.fatal("cannot write file for rsvp:200", ex);
            }
            return file;
        }
        LOG.error("cannot render content:");
        String body = response.readEntity(String.class);
        LOG.error("***error starting response***");
        LOG.error(body);
        LOG.error("***error ending response***");
        return null;

    }

    private static String toFileName(RenderRequest renderReq) {
        String fileName = "label4x6";
        if (renderReq == null) {
            return getFileName(fileName, ".pdf");
        }
        fileName = "label" + renderReq.getHeight() + "x" + renderReq.getWidth() + "_model_";
        if (renderReq.getFileNamePrefix() != null && !renderReq.getFileNamePrefix().isEmpty()) {
            fileName = renderReq.getFileNamePrefix().trim() + renderReq.getHeight() + "x" + renderReq.getWidth() + "_model_";
        }

        if (RenderingType.PdfImage == renderReq.getRenderingType()) {
            return getFileName(fileName, ".pdf");
        }

        if (RenderingType.PngImage == renderReq.getRenderingType()) {
            return getFileName(fileName, ".png");
        }

        return null;
    }

    private static String getFileName(String fileName, String ext) {
        try {
            return File.createTempFile(fileName, ext).getName();
        } catch (IOException ex) {
            LOG.error("file cannot be created vs:" + fileName + ", ext:" + ext);
        }
        return null;
    }

}
