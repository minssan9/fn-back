//package com.service.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.regex.Pattern;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests()
//                    .antMatchers("/").permitAll()
//                    .antMatchers("/swagger-ui.html").permitAll()
//                    .anyRequest().authenticated();
//            http.csrf()
//                    .requireCsrfProtectionMatcher(new CsrfRequireMatcher())
//                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//    }
//
//    static class CsrfRequireMatcher implements RequestMatcher {
//        private static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
//
//        @Override
//        public boolean matches(HttpServletRequest request) {
//            if (ALLOWED_METHODS.matcher(request.getMethod()).matches())
//                return false;
//
//            final String referer = request.getHeader("Referer");
//            if (referer != null && referer.contains("/swagger-ui")) {
//                return false;
//            }
//            return true;
//        }
//    }
//
//}
