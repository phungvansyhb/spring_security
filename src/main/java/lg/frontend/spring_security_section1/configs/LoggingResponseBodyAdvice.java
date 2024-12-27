package lg.frontend.spring_security_section1.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lg.frontend.spring_security_section1.DTOs.request.AuditLogDTO;
import lg.frontend.spring_security_section1.entities.AuditLog;
import lg.frontend.spring_security_section1.repositories.AuditLogRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class LoggingResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private final HttpServletRequest request;
    private final AuditLogRepository auditLogRepository;
    public LoggingResponseBodyAdvice(HttpServletRequest request, AuditLogRepository auditLogRepository) {
        this.request = request;
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        AuditLogDTO auditLogDTO = (AuditLogDTO) this.request.getAttribute("auditLog");
        if(auditLogDTO != null && body != null){

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            try {
                String jsonString = objectMapper.writeValueAsString(body);
                auditLogDTO.setResponse(jsonString);
                AuditLog auditLog = new AuditLog();
                BeanUtils.copyProperties(auditLogDTO , auditLog);
                auditLogRepository.save(auditLog);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
        return body;
    }
}
