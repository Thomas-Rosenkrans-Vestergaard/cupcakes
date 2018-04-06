package tvestergaard.cupcakes.logic;

import java.io.*;

/**
 * Helper class for saving {@link InputStream}s as files.
 */
public class FileSaver
{

    /**
     * The destination directory.
     */
    private File destination;

    /**
     * Creates a new {@link FileSaver} helper.
     *
     * @param destination The destination directory.
     */
    public FileSaver(File destination)
    {
        this.destination = destination;
    }

    /**
     * Saves the provided {@link InputStream} as a file with the provided {@code name}.
     *
     * @param contents The contents to to save.
     * @param name     The name of the file to save the contents as.
     * @throws IOException
     */
    public void saveAs(InputStream contents, String name) throws IOException
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
