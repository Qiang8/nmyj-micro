package com.taiji.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p>Title:ZuulDebugFilter.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/30 16:52</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class ZuulDebugFilter extends ZuulFilter {

    private ObjectMapper objectMapper;

    public ZuulDebugFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ZuulDebugFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 999;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();

        try {
            Boolean zuulEngineRan              = (Boolean) ctx.get("zuulEngineRan ");
            Boolean retryable                  = (Boolean) ctx.get("retryable ");
            String  proxy                      = (String) ctx.get("proxy ");
            Boolean isDispatcherServletRequest = (Boolean) ctx.get("isDispatcherServletRequest ");


            LOGGER.info("zuulEngineRan:\t{}", zuulEngineRan);
            LOGGER.info("retryable:\t{}", retryable);
            LOGGER.info("proxy:\t{}", proxy);
            LOGGER.info("isDispatcherServletRequest:\t{}", isDispatcherServletRequest);

            String serviceId = (String) ctx.get("serviceId");
            LOGGER.info("serviceId:\t{}", serviceId);

            Boolean debugRouting = (Boolean) ctx.get("debugRouting  ");
            Boolean debugRequest = (Boolean) ctx.get("debugRequest ");

            LOGGER.info("debugRouting :\t{}", debugRouting);
            LOGGER.info("debugRequest:\t{}", debugRequest);

            HashSet ignoredHeaders = (HashSet) ctx.get("ignoredHeaders");
            LOGGER.info("ignoredHeaders:\t{}", objectMapper.writeValueAsString(ignoredHeaders));

            Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
            Map<String, String>       zuulRequestHeader  = ctx.getZuulRequestHeaders();
            String                    requestURI         = (String) ctx.get("requestURI");
            InputStream               in                 = request.getInputStream();
            String                    reqBody            = StreamUtils.copyToString(in, Charset.forName("UTF-8"));

            LOGGER.info("requestURI :\t{}", requestURI);
            LOGGER.info("request header:\t{}", objectMapper.writeValueAsString(zuulRequestHeader));
            LOGGER.info("request url:\t{},\t{}", request.getMethod(), request.getRequestURL().toString());

            LOGGER.info("request param:\t{}", objectMapper.writeValueAsString(requestQueryParams));

            LOGGER.info("request body:\t{}", reqBody);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }

        try {
            Boolean responseGZipped    = ctx.getResponseGZipped();
            Integer responseStatusCode = ctx.getResponseStatusCode();
            LOGGER.info("responseGZipped:\t{}", responseGZipped);
            LOGGER.info("responseStatusCode:\t{}", responseStatusCode);

            List<Pair<String, String>> originResponseHeaders = ctx.getOriginResponseHeaders();
            List<Pair<String, String>> zuulResponseHeaders   = ctx.getZuulResponseHeaders();

            boolean jsonFalg = false;
            for (Pair<String, String> pair : originResponseHeaders) {
                if ("Content-Type".equals(pair.first()) && "application/json;charset=UTF-8".equals(pair.second())) {
                    jsonFalg = true;
                    break;
                }
            }

            LOGGER.debug("originResponseHeaders:\t{}", printList(originResponseHeaders));
            LOGGER.debug("zuulResponseHeaders:\t{}", printList(zuulResponseHeaders));

            String      responseBody       = ctx.getResponseBody();
            InputStream responseDataStream = ctx.getResponseDataStream();

            LOGGER.debug("responseBody:\t{}", responseBody);

            if (responseDataStream != null && jsonFalg == true) {
                byte[]      temp;
                InputStream tempIn;
                if (responseGZipped) {
                    InputStream gzipInputStream = new GZIPInputStream(responseDataStream);
                    temp = StreamUtils.copyToByteArray(gzipInputStream);
                    byte[] compressTemp = compress(temp);
                    tempIn = new ByteArrayInputStream(compressTemp);
                    ctx.setResponseDataStream(tempIn);
                } else {
                    temp = StreamUtils.copyToByteArray(responseDataStream);
                    tempIn = new ByteArrayInputStream(temp);

                    ctx.setResponseDataStream(tempIn);
                }

                String reqBody = new String(temp, Charset.forName("UTF-8"));
                LOGGER.debug("responseDataStream:\t{} ", reqBody);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }

        String executedFilters = ctx.getFilterExecutionSummary().toString();
        LOGGER.debug("executedFilters:\t{}", executedFilters);

        List<String> routingDebug = (List<String>) ctx.get("routingDebug");
        LOGGER.debug("routingDebug :\t{}", StringUtils.join(routingDebug, "\n"));

        return null;
    }

    String printList(List<Pair<String, String>> list) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (Pair<String, String> pair : list) {
            String first  = pair.first();
            String second = pair.second();
            sb.append("{").append(first).append(":").append(second).append("}");
        }

        sb.append("]");

        return sb.toString();
    }

    byte[] compress(final byte[] input) throws IOException {
        ByteArrayOutputStream bout    = new ByteArrayOutputStream();
        GZIPOutputStream      gzipper = new GZIPOutputStream(bout);
        gzipper.write(input, 0, input.length);
        gzipper.close();
        return bout.toByteArray();
    }

    byte[] decompress(final byte[] input) throws IOException {
        final ByteArrayInputStream  byteInputStream  = new ByteArrayInputStream(input);
        final ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(input.length);
        final GZIPInputStream       gzipInputStream  = new GZIPInputStream(byteInputStream);

        byte[] buffer = new byte[1024];

        while (gzipInputStream.available() > 0) {
            final int count = gzipInputStream.read(buffer, 0, 1024);

            if (count > 0) {
                byteOutputStream.write(buffer, 0, count);
            }
        }

        gzipInputStream.close();
        byteOutputStream.close();
        byteInputStream.close();

        return byteOutputStream.toByteArray();
    }
}
