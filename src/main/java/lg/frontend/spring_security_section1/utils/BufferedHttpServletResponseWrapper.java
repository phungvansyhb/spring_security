package lg.frontend.spring_security_section1.utils;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class BufferedHttpServletResponseWrapper extends HttpServletResponseWrapper{
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintWriter writer = new PrintWriter(outputStream);
    private boolean isCommitted = false;

    public BufferedHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }


            @Override
            public void setWriteListener(WriteListener listener) {
                // Not implemented
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
        isCommitted = true;
    }

    public String getCapturedResponseBody() {
        return outputStream.toString();
    }

    @Override
    public boolean isCommitted() {
        return isCommitted || super.isCommitted();
    }
}
