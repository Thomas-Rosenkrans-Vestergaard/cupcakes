package tvestergaard.cupcakes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class MultipartParameters
{
	private HttpServletRequest request;

	public String asString(String parameter) throws ServletException, IOException
	{
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
	}

	public MultipartParameters(HttpServletRequest request)
	{
		this.request = request;
	}

	public boolean isPresent(String parameter) throws ServletException, IOException
	{
		return request.getPart(parameter) != null;
	}

	public boolean notPresent(String parameter) throws ServletException, IOException
	{
		return !isPresent(parameter);
	}

	public boolean isNull(String parameter) throws ServletException, IOException
	{
		return asString(parameter) == null;
	}

	public boolean notNull(String parameter) throws ServletException, IOException
	{
		return asString(parameter) != null;
	}

	public boolean isEmpty(String parameter) throws ServletException, IOException
	{
		String value = asString(parameter);

		return value == null || value.length() < 1;
	}

	public boolean notEmpty(String parameter) throws ServletException, IOException
	{
		return !isEmpty(parameter);
	}

	public boolean isInt(String parameter) throws ServletException, IOException
	{
		try {
			Integer.parseInt(asString(parameter));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean notInt(String parameter) throws ServletException, IOException
	{
		return !isInt(parameter);
	}

	public int getInt(String parameter) throws ServletException, IOException
	{
		return Integer.parseInt(asString(parameter));
	}

	public String getString(String parameter) throws ServletException, IOException
	{
		String value = asString(parameter);

		return value == null ? "" : value;
	}

	public boolean isPositiveInt(String parameter) throws ServletException, IOException
	{
		return isInt(parameter) && getInt(parameter) > 0;
	}

	public boolean notPositiveInt(String parameter) throws ServletException, IOException
	{
		return !isPositiveInt(parameter);
	}

	public boolean isNegativeInt(String parameter) throws ServletException, IOException
	{
		return isInt(parameter) && getInt(parameter) < 0;
	}

	public boolean notNegativeInt(String parameter) throws ServletException, IOException
	{
		return isInt(parameter) && getInt(parameter) > -1;
	}
}
