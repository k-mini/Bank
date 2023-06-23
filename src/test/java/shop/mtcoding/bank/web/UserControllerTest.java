package shop.mtcoding.bank.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto.JoinReqDto;

// @Transactional // @Sql 없고 @Transactional로 햇을때 오류났었으나
// ActiveProfiles("test")추가하니
// 오류해결됨?? 왜지?? -> DevInit에서 Dev모드에서는 실행전 User객체를 하나 넣어주는데
// setUp 또 같은 User정보를 넣어주기 때문에 오류가 발생했음
@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        System.out.println("UserControllerTest setUp 시작 ================================");
        userRepository.save(newUser("ssar", "쌀"));
        em.clear();
    }

    @Test
    public void join_success_test() throws Exception {
        // given
        System.out.println("테스트 : ==============================================");
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("love");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("love@nate.com");
        joinReqDto.setFullname("러브");

        String requestBody = om.writeValueAsString(joinReqDto);
        System.out.println("테스트 requestBody : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        // String responseBody =
        // resultActions.andReturn().getResponse().getContentAsString();
        // System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());

    }

    @Test
    public void join_fail_test() throws Exception {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        String requestBody = om.writeValueAsString(joinReqDto);
        // System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isBadRequest());

    }
}
