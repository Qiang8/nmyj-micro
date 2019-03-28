package org.springframework.cloud.netflix.zuul.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:TaijiTraceProxyRequestHelper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/29 20:42</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class TaijiTraceProxyRequestHelper extends TraceProxyRequestHelper{

    private static final Log log = LogFactory.getLog(TaijiTraceProxyRequestHelper.class);

    private TraceRepository traces;

    private ObjectMapper objectMapper;

    @Override
    public void setTraces(TraceRepository traces) {
        this.traces = traces;
    }

    public void setObjectMapper(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> debug(String verb, String uri,
                                     MultiValueMap<String, String> headers, MultiValueMap<String, String> params,
                                     InputStream requestEntity) throws IOException {
        Map<String, Object> info = new LinkedHashMap<>();
        if (this.traces != null) {
            RequestContext context = RequestContext.getCurrentContext();
            info.put("method", verb);
            info.put("path", uri);
            info.put("query", getQueryString(params));
            info.put("remote", true);
            info.put("proxy", context.get("proxy"));
            Map<String, Object> trace = new LinkedHashMap<>();
            Map<String, Object> input = new LinkedHashMap<>();
            trace.put("request", input);
            info.put("headers", trace);
            debugHeaders(headers, input);
            RequestContext ctx = RequestContext.getCurrentContext();
//            if (shouldDebugBody(ctx)) {
                // Prevent input stream from being read if it needs to go downstream
                if (requestEntity != null) {
                    debugRequestEntity(info, ctx.getRequest().getInputStream());
                }
//            }

            log.debug(objectMapper.writeValueAsString(info));

            this.traces.add(info);
            return info;
        }
        return info;
    }

    private void debugRequestEntity(Map<String, Object> info, InputStream inputStream)
            throws IOException {
        if (RequestContext.getCurrentContext().isChunkedRequestBody()) {
            info.put("body", "<chunked>");
            return;
        }
        char[] buffer = new char[4096];
        int count = new InputStreamReader(inputStream, Charset.forName("UTF-8"))
                .read(buffer, 0, buffer.length);
        if (count > 0) {
            String entity = new String(buffer).substring(0, count);
            info.put("body", entity.length() < 4096 ? entity : entity + "<truncated>");
        }
    }
@Override
    void debugHeaders(MultiValueMap<String, String> headers, Map<String, Object> map) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            Collection<String> collection = entry.getValue();
            Object             value      = collection;
            if (collection.size() < 2) {
                value = collection.isEmpty() ? "" : collection.iterator().next();
            }
            map.put(entry.getKey(), value);
        }
    }
}
