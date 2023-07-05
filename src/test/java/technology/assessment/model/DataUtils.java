package technology.assessment.model;

import technology.assessment.model.dto.ItemDTO;
import technology.assessment.model.dto.response.ApiResponse;
import technology.assessment.model.dto.response.ItemResponse;
import technology.assessment.model.entity.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static technology.assessment.util.AppCode.OKAY;
import static technology.assessment.util.AppCode.CREATED;
import static technology.assessment.util.MessageUtil.*;

public class DataUtils {

    public static ApiResponse<String> updateResponse(){
        return new ApiResponse<>(SUCCESS,OKAY, UPDATED);
    }
    public static ItemDTO testStoreItemUpdateRequest(){
        return ItemDTO.builder()
                .name("item name")
                .description("item description")
                .price(BigDecimal.TEN)
                .build();
    }
    public static ApiResponse<String> createUserResponse(){
        return new ApiResponse<>(SUCCESS,CREATED, DONE);
    }

    public static String testStoreItemUpdateRequestError(){
        return String.join(",",Arrays.asList(ITEM_REQUIRED));

    }

    public static ItemDTO testStoreItemRequest(){

        return ItemDTO.builder()
                .name("Test 1")
                .description("Test 1")
                .price(BigDecimal.TEN)
                .build();
    }
    public static ItemDTO testStoreItemBadRequest(){

        return ItemDTO.builder()
                .name("")
                .description("Test 1")
                .price(BigDecimal.TEN)
                .build();
    }
    public static Item testStoreItemData(){
        return Item.builder()
                .name("Rice")
                .description("Rice flower")
                .price(BigDecimal.TEN)
                .build();
    }

    public static String testStoreItemRequestError(){
        return String.join(",",Arrays.asList(ITEM_REQUIRED));
    }
    public static ApiResponse<List<ItemResponse>> testStoreItemResponseList(){
       return new ApiResponse<>(SUCCESS,OKAY,Collections.singletonList(
               ItemResponse.builder()
                       .name("Test")
                       .description("test desc")
                       .price(BigDecimal.TEN)
                       .createdAt(LocalDateTime.now())
                       .updatedAt(LocalDateTime.now())
                       .build()
       ));
    }
    }


