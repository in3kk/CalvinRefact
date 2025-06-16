package Refact.CalvinRefact.controller.test.script.MemberTest
/**
 * 테스트시 주석 해제 후 ngrinder 테스트 스크립트로 이용
 */
//import static net.grinder.script.Grinder.grinder
//
//import net.grinder.plugin.http.HTTPRequest
//import net.grinder.plugin.http.HTTPPluginControl
//import net.grinder.script.GTest
//import net.grinder.scriptengine.groovy.junit.GrinderRunner
//import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
//import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import HTTPClient.NVPair
//@RunWith(GrinderRunner)
//class MemberJoinTest {
//
//    public static GTest test
//    public static HTTPRequest request
//
//    @BeforeProcess
//    public static void beforeProcess() {
//        HTTPPluginControl.getConnectionDefaults().timeout = 3000
//        request = new HTTPRequest()
//        test = new GTest(1, "POST /test/member/join")
//        grinder.logger.info("BeforeProcess executed.")
//    }
//	@BeforeThread
//    public void beforeThread() {
//        test.record(this, "testRecipeDetail");
//        grinder.statistics.delayReports = true;
//        grinder.logger.info("before thread.");
//    }
//    @Before
//    public void before() {
//        grinder.statistics.delayReports = true
//    }
//
//    @Test
//    public void testJoinMember() {
//        def timestamp = String.valueOf(System.currentTimeMillis())
//        def id = "testuser" + grinder.threadNumber + "T" + timestamp
//
//        def params = [
//            id: id,
//            id2: "naver.com",
//            pwd: "rlawlstprlawlstp123",
//            name: "홍길동",
//            phone_number: "01012345678",
//            address: "서울특별시",
//            address2: "강남구"
//        ]
//
//		def paramPairs = params.collect { k, v -> new NVPair(k, v) } as NVPair[]
//        //def encodedParams = params.collect { k, v -> "${k}=${URLEncoder.encode(v, 'UTF-8')}" }.join("&")
//        def headers = [ "Content-Type": "application/x-www-form-urlencoded" ]
//
//        //def response = request.POST("http://host.docker.internal:8080/test/member/join", encodedParams.getBytes("UTF-8"), headers)
//
//		def response = request.POST("http://host.docker.internal:8080/test/member/join", paramPairs)
//
//        assertEquals(200, response.statusCode)
//        grinder.logger.info("응답: ${response.getText()}")
//    }
//}