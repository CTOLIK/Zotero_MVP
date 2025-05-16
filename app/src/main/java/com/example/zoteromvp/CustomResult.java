package com.example.zoteromvp;

import java.util.Optional;

import okhttp3.Headers;

class Nothing {
    private Nothing() {
    }
}
public sealed class CustomResult<T> permits CustomResult.GeneralError, CustomResult.GeneralSuccess {
         static non-sealed class GeneralError extends CustomResult<Nothing> {
            static class NetworkError extends GeneralError {
                public static final int NO_INTERNET_CONNECTION_HTTP_CODE = -1;
                public static final int NO_HTTPS_CERTIFICATE_FOUND = -2;
                public static final int UNKNOWN_NETWORK_EXCEPTION_HTTP_CODE = -999;

                private final int httpCode;
                private final String stringResponse;

                public NetworkError(int httpCode, String stringResponse) {
                    this.httpCode = httpCode;
                    this.stringResponse = stringResponse;
                }

                public boolean isUnchanged() {
                    return httpCode == 304;
                }

                public boolean isNoNetworkError() {
                    return httpCode == NO_INTERNET_CONNECTION_HTTP_CODE;
                }

                public boolean isNoCertificateFound() {
                    return httpCode == NO_HTTPS_CERTIFICATE_FOUND;
                }

                public boolean isNotFound() {
                    return httpCode == 404;
                }

                public int getHttpCode() {
                    return httpCode;
                }

                public String getStringResponse() {
                    return stringResponse;
                }
            }

            static class UnacceptableStatusCode extends NetworkError {
                public UnacceptableStatusCode(int httpCode, String stringResponse) {
                    super(httpCode, stringResponse);
                }
            }

            static class CodeError extends GeneralError {
                private final Throwable throwable;

                public CodeError(Throwable throwable) {
                    this.throwable = throwable;
                }

                public Throwable getThrowable() {
                    return throwable;
                }
            }
        }

        static non-sealed class GeneralSuccess<T> extends CustomResult<T> {
            private final T value;

            public GeneralSuccess(T value) {
                this.value = value;
            }

            public T getValue() {
                return value;
            }

            static class NetworkSuccess<T> extends GeneralSuccess<T> {
                private final Headers headers;
                private final int httpCode;

                public NetworkSuccess(T value, Headers headers, int httpCode) {
                    super(value);
                    this.headers = headers;
                    this.httpCode = httpCode;
                }

                public int getLastModifiedVersion() {
                    return Optional.ofNullable(headers.get("last-modified-version"))
                            .map(Integer::parseInt)
                            .orElse(0);
                }

                public int getHttpCode() {
                    return httpCode;
                }

                public Headers getHeaders() {
                    return headers;
                }
            }
        }

        public Integer getResultHttpCode() {
            if (this instanceof GeneralSuccess.NetworkSuccess) {
                return ((GeneralSuccess.NetworkSuccess<?>) this).getHttpCode();
            } else if (this instanceof GeneralError.NetworkError) {
                return ((GeneralError.NetworkError) this).getHttpCode();
            } else {
                return null;
            }
        }
    }

