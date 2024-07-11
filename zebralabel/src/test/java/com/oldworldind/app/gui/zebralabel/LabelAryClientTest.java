package com.oldworldind.app.gui.zebralabel;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.junit.Assert;
import org.junit.Test;

/**
 * @author mcolegrove
 * @since Apr 16, 2018
 */
public class LabelAryClientTest {

    private static final Logger LOG = LogManager.getLogger(LabelAryClientTest.class);
    private final String zpl = "^xa^cfa,50^fo100,100^fdHello World^fs^xz";

    private static final String HOME = "." + '/'; // "." + File.separator; // using / for Microsoft Path
    private final String ziplh = HOME + "somefileh.zpl";
    private final String zipls = HOME + "somefiles.zpl";

    private final String zplGrainger = HOME + "Grainger1pk_demo.txt";

    @Test
    public void testService4() throws IOException {

        RenderRequest rr = RenderRequest.getPdfRequest(6, 4, "mypdfFile_");
        String partialFileRes = zipls;

        RenderZebraSvc svc = new RenderZebraSvc();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(partialFileRes);
        StringBuilder sb = IoUtils.pipeToBuffer(is);
        String myString = sb.toString().trim();
        LOG.info(partialFileRes + "_toclean:" + myString);
        Assert.assertFalse("must have content from resources here:" + partialFileRes, myString.isEmpty());
        String cleaner = svc.replaceCharFakeh(myString);

        String myzip = svc.replaceCharQuote(cleaner);
        File file = svc.renderRequest(rr, myzip);
        Assert.assertNotNull("sb ok", file);

        LOG.info("ckf:" + file.getAbsolutePath());

        doCleanup(15, file, true);
    }

    @Test
    public void testService3() throws IOException {

        RenderRequest rr = RenderRequest.getPngRequest(2, 4, "mypnghFile_");

        String partialFileRes = ziplh;
        RenderZebraSvc svc = new RenderZebraSvc();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(partialFileRes);
        StringBuilder sb = IoUtils.pipeToBuffer(is);
        String myString = sb.toString().trim();
        LOG.info(partialFileRes + "_toclean:" + myString);
        Assert.assertFalse("must have content from resources here:" + partialFileRes, myString.isEmpty());
        String cleaner = svc.replaceCharFakeh(myString);

        String myzip = svc.replaceCharQuote(cleaner);
        File file = svc.renderRequest(rr, myzip);
        Assert.assertNotNull("sb ok", file);

        LOG.info("ckf:" + file.getAbsolutePath());
        doCleanup(3, file, true);
    }

    @Test
    public void testServiceX() throws IOException {

        RenderRequest rr = RenderRequest.getPngRequest(2, 4, "mypnghFile_");

        String partialFileRes = zplGrainger;
        RenderZebraSvc svc = new RenderZebraSvc();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(partialFileRes);
        StringBuilder sb = IoUtils.pipeToBuffer(is);
        String myString = sb.toString().trim();
        LOG.info(partialFileRes + "_toclean:" + myString);
        Assert.assertFalse("must have content from resources here:" + partialFileRes, myString.isEmpty());
        String cleaner = svc.replaceCharFakeh(myString);

        String myzip = svc.replaceCharQuote(cleaner);
        File file = svc.renderRequest(rr, myzip);
        Assert.assertNotNull("sb ok", file);

        LOG.info("ckf:" + file.getAbsolutePath());
        doCleanup(3, file, true);
    }

    @Test
    public void testService2() throws IOException {
        RenderRequest rr = RenderRequest.getPngRequest(2, 4, "mypndFile_");
        RenderZebraSvc svc = new RenderZebraSvc();
        File file = svc.renderRequest(rr, zpl);
        Assert.assertNotNull("sb ok", file);

        LOG.info("ckf:" + file.getAbsolutePath());
        doCleanup(3, file, true);
    }

    @Test
    public void testService() throws IOException {
        String targFile = "label.pdf";
        Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
        // adjust print density (8dpmm), label width (4 inches), label height (6 inches), and label index (0) as necessary
        WebTarget target = client.target("http://api.labelary.com/v1/printers/8dpmm/labels/4x6/0/");
        Invocation.Builder request = target.request();

//         MediaType.A
        request.accept("application/pdf"); // omit this line to get PNG images back
        Response response = request.post(Entity.entity(zpl, APPLICATION_FORM_URLENCODED));

        if (200 == response.getStatus()) {
            byte[] body = response.readEntity(byte[].class);
            File file = new File(targFile); // change file name for PNG images
            Files.write(file.toPath(), body);
            doCleanup(5, file, true);
        } else {
            String body = response.readEntity(String.class);
            LOG.error(body);
            Assert.fail("evil");

        }
    }

    private void doCleanup(int kminsize, File file, boolean cleanUp) {
        Assert.assertTrue("file must exist:" + file, file != null && file.exists());
        if (kminsize > 0) {
            long msize = kminsize * 1024;
            long fsize = file.length();
            LOG.info("File size:" + fsize + " min k to expect:" + kminsize + ", or=" + msize);
            Assert.assertTrue("file must be k:" + kminsize + ", or=" + msize + ", vs:" + file, file.length() >= msize);
        }

        if (cleanUp) {
            boolean deleted = false;
            try {

                deleted = file.delete();
            } catch (Exception e) {
                LOG.error("no delete file:" + file, e);
            }
            Assert.assertTrue("file must clean :" + file, deleted);
            LOG.info("did clean up on:" + file);
            return;
        }
        LOG.info("file retained:" + file);
    }

}
