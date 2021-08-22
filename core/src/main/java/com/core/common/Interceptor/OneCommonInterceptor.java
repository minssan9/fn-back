package com.core.common.Interceptor;//package com.core.common.Interceptor;
//
//import com.core.common.dto.CustomHttpStatus;
//import com.core.common.util.StringUtil;
//import com.core.homs.common.dao.CommonDao;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//@SuppressWarnings({"rawtypes","unchecked"})
//public class OneCommonInterceptor extends HandlerInterceptorAdapter{
//
//    protected Log log = LogFactory.getLog(OneCommonInterceptor.class);
//
//    static int STS_NO_AUTH = CustomHttpStatus.getNoAuth();
//    static int STS_NO_NEEDREFRESH = CustomHttpStatus.getNeedRefresh();
//    static int STS_FAIL_AUTH = CustomHttpStatus.getFail();
//
//    static int STS_UPDATED = CustomHttpStatus.getUpdated();
//
//	@Resource(name="loginDao")
//	private LoginDao loginDao;
//
//	@Resource(name="commonDao")
//	private CommonDao commonDao;
//
//	private JwtUtil jwtUtil = new JwtUtil();
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        if (log.isDebugEnabled()) {
//            log.debug("===================       START       ===================");
//            log.debug(" Request URI \t:  " + request.getRequestURI());
//        }
//
//        if( !"OPTIONS".equals(request.getMethod())) {
//        	try {
//                String token = StringUtil.isNullToString(request.getHeader("$accessToken"));
//                String pUserId = StringUtil.isNullToString(request.getHeader("$userId"));
//                String pUserType = StringUtil.isNullToString(request.getHeader("$userType"));
//                String pCompany = StringUtil.isNullToString(request.getHeader("$company"));
//                String pAuthGroupCd = StringUtil.isNullToString(request.getHeader("$authGroupCd"));
//
//                if(token.equals("")) {
//            		response.sendError(STS_NO_AUTH,"Invalid Authentication information");
//            		return false;
//            	}
//
//            	// 1. rToken 검증
//            	Boolean bVaild = jwtUtil.isValid(token);
//            	if(!bVaild) {
//            		response.sendError(STS_NO_NEEDREFRESH, "need refresh.");
//            		return false;
//            	}
//
//                String userId = StringUtil.isNullToString(jwtUtil.getUserId(token));
//                String tokenType = StringUtil.isNullToString(jwtUtil.getClaims(token).getBody().get("tokenType"));
//                String userType = StringUtil.isNullToString(jwtUtil.getClaims(token).getBody().get("userType"));
//                String company = StringUtil.isNullToString(jwtUtil.getClaims(token).getBody().get("company"));
//                String authGroupCd = StringUtil.isNullToString(jwtUtil.getClaims(token).getBody().get("authGroupCd"));
//
//                if ("APIKEY".equals(tokenType)) {
//                	// 발급된 토큰인지 체크
//                	Map sendParamMap = new HashMap();
//                	sendParamMap.put("token", token);
//                	int cnt = commonDao.getInt("common.chkApiKey", sendParamMap);
//                	if (cnt < 1) {
//                		response.sendError(STS_FAIL_AUTH, "Fail Authorize");
//                		return false;
//                	}
//
//                } else {
//                    Map pMap = new HashMap();
//                    pMap.put("userId", userId);
//
//                    if (!userId.equals(pUserId) || !userType.equals(pUserType) || !company.equals(pCompany) || !authGroupCd.equals(pAuthGroupCd)) {
//                		response.sendError(STS_FAIL_AUTH, "Fail Authorize");
//                		return false;
//                    }
//
//                    Map tokenMap = loginDao.getUserToken(pMap);
//                    String userAccessToken = StringUtil.isNullToString(tokenMap.get("accessToken"));
//                    // String userRefreshToken = StringUtil.isNullToString(tokenMap.get("refreshToken"));
//                    String multiLoginYn = StringUtil.isNullToString(tokenMap.get("multiLoginYn"));
//        			// int accessTokenTimeout = Integer.parseInt(StringUtil.nullConvert(tokenMap.get("accessTokenTimeout"), "0"));
//        			// int rfreshTokenTimeout = Integer.parseInt(StringUtil.nullConvert(tokenMap.get("rfreshTokenTimeout"), "0"));
//        			// 멀티 로그인 가능한 유저인지 확인
//        			if(!multiLoginYn.equals("Y")) {
//                        // 1. aToken <> db 비교  rToken <> db 비교
//                        if(!userAccessToken.equals(token)) {
//                        	response.sendError(STS_FAIL_AUTH, "Fail Authorize.");
//                        	return false;
//                        }
//        			}
//
//                    String oldVer = StringUtil.isNullToString(request.getHeader("$systemVer"));
//                    String curVer = commonDao.getString("common.getSystemVersion", null);
//                    if(!oldVer.equals(curVer)) {
//                		response.sendError(STS_UPDATED, "updated.");
//                		return false;
//                    }
//                }
//
//        	} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
//        		response.sendError(STS_FAIL_AUTH, "Fail Authorize.");
//        		return false;
//    		} catch (ExpiredJwtException ex) {
//    			response.sendError(STS_FAIL_AUTH, "Fail Authorize.");
//    			return false;
//    		} catch (Exception ex) {
//    			log.error(ex.getCause());
//    			log.error(ex.getMessage());
//    			// response.sendError(STS_FAIL_AUTH, "Fail Authorize.");
//    			return false;
//    		}
//        }
//
//        return super.preHandle(request, response, handler);
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//            ModelAndView modelAndView) throws Exception {
//        if (log.isDebugEnabled()) {
//            log.debug("===================        END        ===================\n");
//        }
//    }
//
//}
