package com.adidas.subscriptions.exception.handler;

import com.adidas.subscriptions.exception.AdidasErrorCode;
import com.adidas.subscriptions.exception.ApiError;
import com.adidas.subscriptions.exception.BusinessException;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A convenient base class for @ControllerAdvice classes that wish to provide
 * centralized exception handling across all @RequestMapping methods
 * through @ExceptionHandler methods.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

  /**
   * The Constant apiErrorLog.
   */
  private static final String API_ERROR_LOG = "API Error";

  /**
   * The Constant apiErrorArgument.
   */
  private static final String API_ERROR_STATEMENT = "apiError";


  // 400

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler#handleHttpMessageNotReadable(org.
   * springframework.http.converter.HttpMessageNotReadableException,
   * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_001, ex.getMessage());
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.
   * springframework.web.bind.MethodArgumentNotValidException,
   * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    final List<String> errors = new ArrayList<>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_002, "Validation error", errors);
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler#handleBindException(org.springframework.
   * validation.BindException, org.springframework.http.HttpHeaders,
   * org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                       WebRequest request) {
    final List<String> errors = new ArrayList<>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_002, "Validation error", errors);
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler#handleMissingServletRequestPart(org.
   * springframework.web.multipart.support.MissingServletRequestPartException,
   * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
                                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_003, ex.getMessage());
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler#handleMissingServletRequestParameter(org.
   * springframework.web.bind.MissingServletRequestParameterException,
   * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
    final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
    final WebRequest request) {
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_004, ex.getMessage());
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler
   * #handleServletRequestBindingException(org.springframework.web.bind.
   * ServletRequestBindingException, org.springframework.http.HttpHeaders,
   * org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                        HttpHeaders headers, HttpStatus status, WebRequest request) {
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_004, ex.getMessage());
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler#handleTypeMismatch(org.springframework.beans.
   * TypeMismatchException, org.springframework.http.HttpHeaders,
   * org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
                                                      final HttpStatus status, final WebRequest request) {
    final String message = ex.getValue() + " should be of type " + ex.getRequiredType().getName();
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_005, message);
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler#handleNoHandlerFoundException(org.
   * springframework.web.servlet.NoHandlerFoundException,
   * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                 final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_006, ex.getMessage());
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

  // 404

  /**
   * Handle no such element exception.
   *
   * @param ex      the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Object> handleNoSuchElementException(final NoSuchElementException ex,
                                                             final WebRequest request) {
    final String message = "Requested " + ex.getMessage() + " does not exist";
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_007, message);
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

  // 405

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler#handleHttpRequestMethodNotSupported(org.
   * springframework.web.HttpRequestMethodNotSupportedException,
   * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
    final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
    final WebRequest request) {
    final StringBuilder message = new StringBuilder(65);
    message.append(ex.getMethod());
    message.append(" method is not supported for this request. Supported methods are ");
    Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> message.append(t).append(" "));
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_008, message.substring(0, message.length() - 1));
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
  }

  // 415

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.web.servlet.mvc.method.annotation.
   * ResponseEntityExceptionHandler#handleHttpMediaTypeNotSupported(org.
   * springframework.web.HttpMediaTypeNotSupportedException,
   * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
   * org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    final StringBuilder message = new StringBuilder(56);
    message.append(ex.getContentType());
    message.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> message.append(t + " "));
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_009, message.substring(0, message.length() - 1));
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  // 500

  /**
   * Handle generic exception.
   *
   * @param ex      the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(final Exception ex, final WebRequest request) {
    final ApiError apiError = new ApiError(AdidasErrorCode.TECH_010, "Internal error ocurred");
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle business exception.
   *
   * @param ex      the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Object> handleBusinessException(final BusinessException ex, final WebRequest request) {
    final ApiError apiError = new ApiError(ex.getErrorCode(), ex.getMessage(), ex.getErrors());
    LOG.error(API_ERROR_LOG, StructuredArguments.value(API_ERROR_STATEMENT, apiError));
    logStackTrace(ex);
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Log stack trace.
   *
   * @param ex the ex
   */
  protected void logStackTrace(Exception ex) {

    // Iterate exception until get root exception
    Throwable rootThrowable = ex;
    Throwable tempThrowable;
    while (null != (tempThrowable = rootThrowable.getCause())) {
      rootThrowable = tempThrowable;
    }

    // Log stack trace element
    LOG.error(rootThrowable.getMessage(),rootThrowable);
  }

}
