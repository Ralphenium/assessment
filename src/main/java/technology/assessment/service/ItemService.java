package technology.assessment.service;

import technology.assessment.model.dto.ItemDTO;
import technology.assessment.model.dto.response.ApiResponse;
import technology.assessment.model.dto.response.ItemResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ItemService {
    ApiResponse<String> addItem(ItemDTO payload);
    ApiResponse<String> addItems(List<ItemDTO> payloadList);
    ApiResponse<String> updateItem(ItemDTO payload);
    ApiResponse<List<ItemResponse>> listItem(int page, int size);
    ApiResponse<ItemResponse> getItemById(Long id);
    ApiResponse<String> deleteItem(Long id);
    ApiResponse<ItemResponse> getItemByNameAndPrice(String name, BigDecimal price);
}
