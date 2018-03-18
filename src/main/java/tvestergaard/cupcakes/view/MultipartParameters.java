package tvestergaard.cupcakes.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Helper class for performing validation operations on incoming requests sent using enctype multipart/form-data.
 */
public class MultipartParameters extends Parameters
{

    public MultipartParameters(HttpServletRequest request)
    {
        super(request);
    }

    public String asString(String parameter)
    {
        try {

            Part part = request.getPart(parameter);

            if (part == null)
                return "";

            final int           bufferSize = 1024;
            final char[]        buffer     = new char[bufferSize];
            final StringBuilder out        = new StringBuilder();
            Reader              in         = new InputStreamReader(part.getInputStream(), "UTF-8");
            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
            return out.toString();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
