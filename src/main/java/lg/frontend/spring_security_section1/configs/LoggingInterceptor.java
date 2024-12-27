package lg.frontend.spring_security_section1.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lg.frontend.spring_security_section1.DTOs.request.AuditLogDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;


@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // Log request details
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request Address: " + request.getRemoteAddr());
        System.out.println("Request time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println("Request Path: " + request.getRequestURI());
        Map<String , String[]> params = request.getParameterMap();
        StringBuffer sb = new StringBuffer("{");
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            sb.append(entry.getKey()).append(" = ").append(Arrays.toString(entry.getValue())).append("\n");
        }
        sb.append("}");

        AuditLogDTO auditLogDTO =  new AuditLogDTO();
        auditLogDTO.setParameters(sb.toString());
        auditLogDTO.setMethod(request.getMethod());
        auditLogDTO.setIp(request.getRemoteAddr());
        auditLogDTO.setTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        auditLogDTO.setApiPath(request.getRequestURI());
        request.setAttribute("auditLog", auditLogDTO);
        return true;
    }

}