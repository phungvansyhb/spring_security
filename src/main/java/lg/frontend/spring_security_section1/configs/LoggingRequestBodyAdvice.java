package lg.frontend.spring_security_section1.configs;

import jakarta.servlet.http.HttpServletRequest;
import lg.frontend.spring_security_section1.DTOs.request.AuditLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@ControllerAdvice
public class LoggingRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        System.out.println("Request Body: " + body);
        AuditLogDTO auditLogDTO = (AuditLogDTO) httpServletRequest.getAttribute("auditLog");
        if(auditLogDTO != null && body != null){
            auditLogDTO.setResponse(body.toString());
        }
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

}
