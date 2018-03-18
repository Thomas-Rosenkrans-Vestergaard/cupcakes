package tvestergaard.cupcakes;

import javax.servlet.ServletException;
import java.io.*;

public class ImageUpload
{

    private File destination;

    public ImageUpload(File destination)
    {
        this.destination = destination;
    }

    public void saveAs(InputStream contents, String name) throws IOException, ServletException
    {
        destination.mkdirs();
        try (OutputStream output = new FileOutputStream(destination.getAbsolutePath() + "/" + name)) {
            int          read;
            final byte[] bytes = new byte[1024];
            while ((read = contents.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
        }
    }
}
