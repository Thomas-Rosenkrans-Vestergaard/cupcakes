package tvestergaard.cupcakes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;

public class ImageUpload
{

	private HttpServletRequest request;
	private File               destinationDirectory;

	public void setDestinationDirectory(File destinationDirectory)
	{
		this.destinationDirectory = destinationDirectory;
	}

	public void saveAs(String parameterName, String name) throws IOException, ServletException
	{
		Part part = request.getPart(parameterName);

		OutputStream out      = null;
		InputStream  contents = null;

		try {
			destinationDirectory.mkdirs();
			out = new FileOutputStream(destinationDirectory.getAbsolutePath() + "/" + name);
			contents = part.getInputStream();

			int          read  = 0;
			final byte[] bytes = new byte[1024];

			while ((read = contents.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		} finally {
			if (out != null) {
				out.close();
			}
			if (contents != null) {
				contents.close();
			}
		}
	}

	public ImageUpload(HttpServletRequest request)
	{
		this.request = request;
	}
}
