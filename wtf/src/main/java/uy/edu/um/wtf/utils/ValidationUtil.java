package uy.edu.um.wtf.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import uy.edu.um.wtf.exceptions.InvalidDataException;

import java.util.Set;

public class ValidationUtil {

    // Método genérico para validar objetos
    public static <T> void validate(T object, Validator validator) throws InvalidDataException {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new InvalidDataException("Errores de validación: " + sb.toString());
        }
    }



}