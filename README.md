# webflux-micrometer-tracing Sample

Exemplifies the tracing behavior with Micrometer Tracing with a Spring WebFlux application.

* ✅ http://localhost:8080/
    ```
    {"traceId":"6440daba1cc9dc397f83202bfbeca6ed","spanId":"7f83202bfbeca6ed","thread":"reactor-http-nio-3","log_level":"INFO","class":"c.e.w.controller.SampleController","log_message":"Called /"}
    {"traceId":"6440daba1cc9dc397f83202bfbeca6ed","spanId":"7f83202bfbeca6ed","thread":"reactor-http-nio-3","log_level":"INFO","class":"c.e.w.controller.SampleController","log_message":"Will return OK"}
    ```
* ✅ http://localhost:8080/error
    ```
    {"traceId":"6440dac0c4b383016757ed24600f383d","spanId":"6757ed24600f383d","thread":"reactor-http-nio-3","log_level":"INFO","class":"c.e.w.controller.SampleController","log_message":"Called /error"}
    {"traceId":"6440dac0c4b383016757ed24600f383d","spanId":"6757ed24600f383d","thread":"reactor-http-nio-3","log_level":"ERROR","class":"c.e.w.controller.SampleController","log_message":"Error: ERROR"}
    ```
* ✅ http://localhost:8080/webclient
    ```
    {"traceId":"6440dac4ef5885c8c018085cfafb6245","spanId":"c018085cfafb6245","thread":"reactor-http-nio-3","log_level":"INFO","class":"c.e.w.controller.SampleController","log_message":"Called /webclient"}
    {"traceId":"6440dac4ef5885c8c018085cfafb6245","spanId":"c018085cfafb6245","thread":"reactor-http-nio-3","log_level":"INFO","class":"c.e.w.controller.SampleController","log_message":"Received {headers={Accept=*/*, Accept-Encoding=gzip, Host=httpbin.org, Traceparent=00-6440dac4ef5885c8c018085cfafb6245-1d9eef4e59e10391-00, User-Agent=ReactorNetty/1.1.5, X-Amzn-Trace-Id=Root=1-6440dac4-2a5607e11c5f64da28641a6c}}"}
    ```
* ✅ http://localhost:8080/webclient/error
    ```
    {"traceId":"6440dac7b986c438331595d4f482b665","spanId":"331595d4f482b665","thread":"reactor-http-nio-3","log_level":"INFO","class":"c.e.w.controller.SampleController","log_message":"Called /webclient/error"}
    {"traceId":"6440dac7b986c438331595d4f482b665","spanId":"331595d4f482b665","thread":"reactor-http-nio-3","log_level":"ERROR","class":"c.e.w.controller.SampleController","log_message":"Error: 400 Bad Request from GET https://httpbin.org/status/400"}
    ```
* ✅ http://localhost:8080/webclient/onStatus/200
    ```
    {"traceId":"6440dace0fe617af0a1da8e232f53b7e","spanId":"0a1da8e232f53b7e","thread":"reactor-http-nio-3","log_level":"INFO","class":"c.e.w.controller.SampleController","log_message":"Called /webclient/onStatus/200"}
    {"traceId":"6440dace0fe617af0a1da8e232f53b7e","spanId":"0a1da8e232f53b7e","thread":"reactor-http-nio-3","log_level":"ERROR","class":"c.e.w.controller.SampleController","log_message":"Error: 200 OK from GET https://httpbin.org/status/200"}
    ```
* ⚠️ http://localhost:8080/webclient/onStatus/400
    ```
    {"traceId":"6440dad3ef1177c2310f26740bd5c889","spanId":"310f26740bd5c889","thread":"reactor-http-nio-3","log_level":"INFO","class":"c.e.w.controller.SampleController","log_message":"Called /webclient/onStatus/400"}
    {"traceId":"6440dad3ef1177c2310f26740bd5c889","spanId":"310f26740bd5c889","thread":"reactor-http-nio-3","log_level":"ERROR","class":"c.e.w.controller.SampleController","log_message":"Called onStatus with status code 400"}
    {"thread":"reactor-http-nio-3","log_level":"ERROR","class":"c.e.w.controller.SampleController","log_message":"Error: ERROR"}
    ```
* ✅ http://localhost:8080/webclient/onStatus/400/fix
    ```
    {"traceId":"6440dad9a3884ca6ac075310ae93b9ee","spanId":"ac075310ae93b9ee","thread":"reactor-http-nio-3","log_level":"INFO","class":"c.e.w.controller.SampleController","log_message":"Called /webclient/onStatus/400/fix"}
    {"traceId":"6440dad9a3884ca6ac075310ae93b9ee","spanId":"ac075310ae93b9ee","thread":"reactor-http-nio-3","log_level":"ERROR","class":"c.e.w.controller.SampleController","log_message":"Called onStatus with status code 400"}
    {"traceId":"6440dad9a3884ca6ac075310ae93b9ee","spanId":"ac075310ae93b9ee","thread":"reactor-http-nio-3","log_level":"ERROR","class":"c.e.w.controller.SampleController","log_message":"Error: ERROR"}
    ```
