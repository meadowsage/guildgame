package com.meadowsage.guildgame.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiInterceptor implements HandlerInterceptor {

//    @Value("${API_KEY}")
//    private String API_KEY;
//
//    @Override
//    public boolean preHandle(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Object handler) throws IOException {
//
//        // preflightの場合はスキップ
//        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) return true;
//
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader == null || !authorizationHeader.equals("Bearer " + API_KEY)) {
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
//            return false;
//        }
//
//        return true;
//    }
}
