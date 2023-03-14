//package com.test.t1;
//
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.handler.codec.http.*;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
//import static io.netty.handler.codec.http.HttpResponseStatus.OK;
//import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
//import static org.springframework.http.HttpHeaders.*;
//
//@Slf4j
//public class HttpServerHandler extends ChannelInboundHandlerAdapter {
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        String s = null;
//        if (msg instanceof HttpRequest) {
//            DefaultHttpRequest request = (DefaultHttpRequest) msg;
//            String uri = request.uri();
//            if ("/favicon.ico".equals(uri)) {
//                return;
//            }
//            log.info(new Date().toString());
//            //这里我们的处理是回源到tomcat服务器进行抓取，然后
//            //将抓取的内容放回到redis里面
//            try {
//                URL url = new URL("http://119.29.188.224:8080" + uri);
//                log.info(url.toString());
//                URLConnection urlConnection = url.openConnection();
//                HttpURLConnection connection = (HttpURLConnection) urlConnection;
//                connection.setRequestMethod("GET");
//                //连接
//                connection.connect();
//                //得到响应码
//                int responseCode = connection.getResponseCode();
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
//                            (connection.getInputStream(), StandardCharsets.UTF_8));
//                    StringBuilder bs = new StringBuilder();
//                    String l;
//                    while ((l = bufferedReader.readLine()) != null) {
//                        bs.append(l).append("\n");
//                    }
//                    s = bs.toString();
//                }
//                connection.disconnect();
//            } catch (Exception e) {
//                log.error("", e);
//                return;
//            }
//        }
//        FullHttpResponse response = new DefaultFullHttpResponse(
//                HTTP_1_1, OK, Unpooled.wrappedBuffer(s != null ? s
//                .getBytes() : new byte[0]));
//        response.headers().set(CONTENT_TYPE, "text/html");
//        response.headers().set(CONTENT_LENGTH,
//                response.content().readableBytes());
//        response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        ctx.write(response);
//        ctx.flush();
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
//            throws Exception {
//        ctx.close();
//    }
//}
//
//
