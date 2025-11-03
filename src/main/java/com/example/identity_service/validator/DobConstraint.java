package com.example.identity_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) //chỉ định annotation này được phép đặt ở đâu (ElementType.FIELD → nghĩa là chỉ được dùng trên thuộc tính (field) của class)
@Retention(RetentionPolicy.RUNTIME) //Cho biết annotation này tồn tại đến giai đoạn nào của chương trình.
@Constraint(validatedBy = {DobValidator.class}) // Chỉ định class khi gặp @DobConstraint để kiểm tra tính hợp lệ
public @interface DobConstraint {
    String message() default "Invalid date of birth";
    int min();
    Class<?>[] groups() default {}; //chứa các class thuộc bất kì kiểu nào
    Class<? extends Payload>[] payload() default{}; //? extends Payload nghĩa là “bất kỳ class nào kế thừa hoặc implement Payload”,
}
