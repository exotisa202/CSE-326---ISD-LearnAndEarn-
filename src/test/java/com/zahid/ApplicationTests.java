package com.zahid;

import com.nabil.Application;
import com.nabil.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("demo")
@AutoConfigureMockMvc
class ApplicationTests {
    @Autowired AccountService accounts;
    @Autowired MockMvc mvc;
    @Test void demoAccountsAreSeeded() {
        assertNotNull(accounts.getAccountByEmail("student@example.com"));
        assertEquals("ROLE_ADMIN", accounts.getAccountByEmail("teacher@example.com").getRole().getName());
    }
    @Test void loginAndBrowseSyntheticCourses() throws Exception {
        mvc.perform(get("/login")).andExpect(status().isOk());
        MockHttpSession session = (MockHttpSession) mvc.perform(post("/login")
            .param("email", "student@example.com").param("password", "LocalDemo123!"))
            .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"))
            .andReturn().getRequest().getSession(false);
        assertNotNull(session);
        mvc.perform(get("/courses").session(session)).andExpect(status().isOk());
        mvc.perform(get("/logout").session(session).accept("text/html"))
            .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login?logout"));
        assertTrue(session.isInvalid(), "Logout must invalidate the authenticated session");
        mvc.perform(get("/courses").accept("text/html"))
            .andExpect(status().is3xxRedirection());
    }
    @Test void invalidLoginIsRejected() throws Exception {
        mvc.perform(post("/login").param("email", "student@example.com").param("password", "wrong"))
            .andExpect(redirectedUrl("/error"));
    }
}
