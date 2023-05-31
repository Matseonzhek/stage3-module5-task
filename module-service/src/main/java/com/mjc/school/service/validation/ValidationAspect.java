package com.mjc.school.service.validation;

import com.mjc.school.service.annotation.Validate;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.exception.ValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import static com.mjc.school.service.constants.Constants.*;
import static com.mjc.school.service.exception.ServiceErrorCode.NUMBER_VALIDATION;
import static com.mjc.school.service.exception.ServiceErrorCode.STRING_VALIDATION;


@Aspect
@Component
public class ValidationAspect {

    @Before(value = "@annotation(com.mjc.school.service.annotation.Validate)&&args(id)")
    public void checkId(JoinPoint joinPoint, Long id) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        methodSignature.getMethod();
        Validate validate = methodSignature.getMethod().getAnnotation(Validate.class);
        String value = validate.value();
        if (value.equals("checkId")) {
            validateNumber(id, NEWS_ID);
        }
    }

    @Before(value = "@annotation(com.mjc.school.service.annotation.Validate)&&args(newsDtoRequest)")
    public void checkNewsDtoRequest(JoinPoint joinPoint, NewsDtoRequest newsDtoRequest) throws ValidationException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        methodSignature.getMethod();
        Validate validate = methodSignature.getMethod().getAnnotation(Validate.class);
        String value = validate.value();
        if (value.equals("checkNews")) {
            validateString(newsDtoRequest.getTitle(), NEWS_ID, NEWS_TITLE_MIN, NEWS_TITLE_MAX);
            validateString(newsDtoRequest.getContent(), NEWS_ID, NEWS_CONTENT_MIN, NEWS_CONTENT_MAX);
            checkId(joinPoint, newsDtoRequest.getId());
            checkId(joinPoint, newsDtoRequest.getAuthorId());
        }
    }

    @Before(value = "@annotation(com.mjc.school.service.annotation.Validate)&&args(authorDtoRequest)")
    public void checkAuthorDtoRequest(JoinPoint joinPoint, AuthorDtoRequest authorDtoRequest) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        methodSignature.getMethod();
        Validate validate = methodSignature.getMethod().getAnnotation(Validate.class);
        String value = validate.value();
        if (value.equals("checkAuthor")) {
            validateString(authorDtoRequest.getName(), AUTHOR_ID, AUTHOR_NAME_MIN, AUTHOR_NAME_MAX);
        }
    }

    @Before(value = "@annotation(com.mjc.school.service.annotation.Validate)&&args(tagDtoRequest)")
    public void checkTagDtoRequest(JoinPoint joinPoint, TagDtoRequest tagDtoRequest) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        methodSignature.getMethod();
        Validate validate = methodSignature.getMethod().getAnnotation(Validate.class);
        String value = validate.value();
        if (value.equals("checkTag")) {
            validateString(tagDtoRequest.getName(), TAG_ID, TAG_NAME_MIN, TAG_NAME_MAX);
        }
    }

    @Before(value = "@annotation(com.mjc.school.service.annotation.Validate)&&args(commentDtoRequest)")
    public void checkContentDtoRequest(JoinPoint joinPoint, CommentDtoRequest commentDtoRequest) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        methodSignature.getMethod();
        Validate validate = methodSignature.getMethod().getAnnotation(Validate.class);
        String value = validate.value();
        if (value.equals("checkComment")) {
            validateString(commentDtoRequest.getContent(), COMMENT_ID, COMMENT_CONTENT_MIN, COMMENT_CONTENT_MAX);
        }
    }

    void validateString(String value, String parameter, int minNumber, int maxNumber) {
        if (value == null) {
            throw new ValidationException(String.format(STRING_VALIDATION.getMessage(), value, parameter));
        }
        if (value.trim().length() < minNumber || value.trim().length() > maxNumber) {
            throw new ValidationException(String.format(STRING_VALIDATION.getMessage(), value, parameter));
        }
    }

    private void validateNumber(Long id, String parameter) {
        if (id == null || id < 1) {
            throw new ValidationException(String.format(NUMBER_VALIDATION.getMessage(), id, parameter));
        }
    }

}
