package lg.frontend.spring_security_section1.configs;

import lg.frontend.spring_security_section1.exceptions.ItemQuantityException;
import lg.frontend.spring_security_section1.models.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CustomResponse<Map<String , String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        HashMap<String , String> errors = new HashMap<>();
        fieldErrors.forEach(error -> {
            errors.put(error.getField() , error.getDefaultMessage());
        });
        return new CustomResponse<>(false, HttpStatus.BAD_REQUEST.name(), errors);
    }

    @ExceptionHandler(value = ItemQuantityException.class)
    public CustomResponse<Map<String , String>> handleItemQuantityException(ItemQuantityException e) {
        return new CustomResponse<>(false, HttpStatus.BAD_REQUEST.name(), Map.of("cause", e.getMessage()));
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public CustomResponse<Map<String , String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new CustomResponse<>(false, HttpStatus.BAD_REQUEST.name(), Map.of("cause", e.getMessage()));
    }


}
