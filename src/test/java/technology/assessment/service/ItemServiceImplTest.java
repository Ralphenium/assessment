package technology.assessment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import technology.assessment.model.DataUtils;
import technology.assessment.model.dto.ItemDTO;
import technology.assessment.model.dto.response.ApiResponse;
import technology.assessment.model.dto.response.ItemResponse;
import technology.assessment.model.entity.Item;
import technology.assessment.repository.ItemRepository;
import technology.assessment.util.BaseIT;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static technology.assessment.util.AppCode.*;
import static technology.assessment.util.ItemEndpoints.*;
import static technology.assessment.util.MessageUtil.*;
import static technology.assessment.util.RestMapper.mapFromJson;
import static technology.assessment.util.RestMapper.mapToJson;

class ItemServiceImplTest extends BaseIT {
    @Autowired
    private ItemRepository storeItemRepo;


    @BeforeEach
    void setUp() {
        storeItemRepo.deleteAllInBatch();
    }


    Item initStoreItem() {
        Item item = DataUtils.testStoreItemData();
        return storeItemRepo.save(item);

    }

    @Test
    void test_add_item_success() throws Exception {
        ItemDTO storeItemRequest = DataUtils.testStoreItemRequest();
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(storeItemRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), DONE);
    }

    @Test
    void test_add_item_bad_request() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemBadRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<String>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData().size(), 2);
    }

    @Test
    void test_update_item_success() throws Exception {
        Item item = initStoreItem();
        ItemDTO payload = DataUtils.testStoreItemUpdateRequest();
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(payload)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), UPDATED);
    }

    @Test
    void test_update_item_bad_request() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(new ItemDTO())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<String>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData().size(), 3);
    }

    @Test
    void test_list_item_return_success() throws Exception {
        initStoreItem();
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<ItemResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(), 1);
    }

    @Test
    void test_list_item_return_failed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), RECORD_NOT_FOUND);
    }
}
