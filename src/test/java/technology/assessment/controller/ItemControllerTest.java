package technology.assessment.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import technology.assessment.exception.BadRequestException;
import technology.assessment.exception.RecordNotFoundException;
import technology.assessment.model.DataUtils;
import technology.assessment.model.dto.response.ApiResponse;
import technology.assessment.model.dto.response.ItemResponse;
import technology.assessment.service.ItemService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static technology.assessment.model.DataUtils.*;
import static technology.assessment.util.AppCode.*;
import static technology.assessment.util.ItemEndpoints.*;
import static technology.assessment.util.MessageUtil.*;
import static technology.assessment.util.RestMapper.mapFromJson;
import static technology.assessment.util.RestMapper.mapToJson;

@WebMvcTest(ItemController.class)
@Slf4j
class ItemControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void test_add_item_success() throws Exception {
        when(itemService.addItem(any())).thenReturn(DataUtils.createUserResponse());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(),DONE);
    }

    @Test
    void test_add_item_bad_request() throws Exception {
        Mockito.doThrow(new BadRequestException(testStoreItemRequestError()))
                .when(itemService).addItem(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),testStoreItemRequestError());
    }
    @Test
    void test_update_item_success() throws Exception {
        when(itemService.updateItem(any())).thenReturn(DataUtils.updateResponse());
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemUpdateRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(),UPDATED);
    }
    @Test
    void test_update_item_bad_request() throws Exception {
        Mockito.doThrow(new BadRequestException(testStoreItemUpdateRequestError()))
                .when(itemService).updateItem(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemUpdateRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),testStoreItemUpdateRequestError());
    }

    @Test
    void test_list_item_return_success() throws Exception {
        when(itemService.listItem(anyInt(),anyInt())).thenReturn(DataUtils.testStoreItemResponseList());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
                ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<ItemResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(),1);

    }
    @Test
    void test_list_item_return_failed() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(RECORD_NOT_FOUND))
                .when(itemService).listItem(anyInt(),anyInt());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),RECORD_NOT_FOUND);
    }

}
