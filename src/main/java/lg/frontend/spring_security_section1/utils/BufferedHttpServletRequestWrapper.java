package lg.frontend.spring_security_section1.utils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class BufferedHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] cachedBody;

    public BufferedHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // Read the request body and cache it
        InputStream inputStream = request.getInputStream();
        this.cachedBody = inputStream.readAllBytes();
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedBodyServletInputStream(cachedBody);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    private static class CachedBodyServletInputStream extends ServletInputStream {
        private final byte[] cachedBody;
        private int index = 0;

        public CachedBodyServletInputStream(byte[] cachedBody) {
            this.cachedBody = cachedBody;
        }

        @Override
        public boolean isFinished() {
            return index >= cachedBody.length;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() {
            return isFinished() ? -1 : cachedBody[index++];
        }
    }
}



