package com.example.dayhunter.teamvoytestproject.controller;
import com.example.dayhunter.teamvoytestproject.config.impl.UserDetailsServiceImpl;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodDto;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodRequestDto;
import com.example.dayhunter.teamvoytestproject.service.GoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GoodControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GoodService goodService;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new GoodController(goodService))
                .build();
    }

    @Test
    public void testAddGood() throws Exception {
        // Mock your user details here
        UserDetails userDetails = User.withUsername("testUser")
                .password("testPassword")
                .roles("MANAGER")
                .build();
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        // Mock your GoodRequestDto here
        GoodRequestDto goodDto = new GoodRequestDto();
        goodDto.setName("Billy");
        goodDto.setPrice(23.0);
        goodDto.setQuantity(3);


        mockMvc.perform(post("/goods/add")
                        .content(asJsonString(goodDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(goodService, times(1)).createGood(goodDto);
    }

    @Test
    public void testGoodList() throws Exception {
        // Mock your GoodDto list here
        List<GoodDto> goodDtoList = new ArrayList<>();

        when(goodService.allGoods()).thenReturn(goodDtoList);

        mockMvc.perform(get("/goods/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(goodService, times(1)).allGoods();
    }

    // Helper method to convert an object to JSON string
    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}