package technology.assessment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import technology.assessment.exception.RecordNotFoundException;
import technology.assessment.mapper.Mapper;
import technology.assessment.model.dto.ItemDTO;
import technology.assessment.model.dto.response.ApiResponse;
import technology.assessment.model.dto.response.ItemResponse;
import technology.assessment.model.entity.Item;
import technology.assessment.repository.ItemRepository;

import static technology.assessment.util.AppCode.*;
import static technology.assessment.util.MessageUtil.*;
import static technology.assessment.util.ParamName.SORTING_COL;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    @Override
    public ApiResponse<String> addItem(ItemDTO payload) {
        Item newItem = Mapper.convertObject(payload,Item.class);
        newItem.setCreatedAt(LocalDateTime.now());
        itemRepository.save(newItem);
        return new ApiResponse<>(SUCCESS,CREATED,DONE);
    }

    @Override
    public ApiResponse<String> addItems(List<ItemDTO> payloadList) {
        List<Item> itemList = new ArrayList<>();

        for (ItemDTO payload : payloadList) {
            Item newItem = Mapper.convertObject(payload, Item.class);
            newItem.setCreatedAt(LocalDateTime.now());
            itemList.add(newItem);
        }

        itemRepository.saveAll(itemList);

        return new ApiResponse<>(SUCCESS, CREATED, DONE);
    }

    @Override
    public ApiResponse<String> updateItem(ItemDTO payload) {
        Item item = itemRepository.findById(payload.getId()).orElseThrow(() -> new RecordNotFoundException(ITEM_REQUIRED));
        item.setPrice(payload.getPrice()==null || payload.getPrice().intValue()<=0? item.getPrice() : payload.getPrice());
        item.setName(payload.getName()==null ? item.getName() : payload.getName());
        item.setDescription(payload.getDescription()==null ? item.getDescription() : payload.getDescription());
        item.setUpdatedAt(LocalDateTime.now());
        itemRepository.save(item);
        return ApiResponse.<String>builder()
                .code(OKAY)
                .data(UPDATED)
                .message(SUCCESS)
                .build();
    }

    @Override
    public ApiResponse<List<ItemResponse>> listItem(int page, int size) {
        Page<Item> itemPage= itemRepository.findAll(PageRequest.of(page,size, Sort.by(SORTING_COL).descending()));
        if(itemPage.isEmpty())
            throw new RecordNotFoundException(RECORD_NOT_FOUND);
        return new ApiResponse<>(SUCCESS,OKAY,Mapper.convertList(itemPage.getContent(),ItemResponse.class));
    }

    @Override
    public ApiResponse<ItemResponse> getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));

        return new ApiResponse<>(SUCCESS,OKAY,Mapper.convertObject(item, ItemResponse.class));
    }

    @Override
    public ApiResponse<String> deleteItem(Long id) {
        itemRepository.deleteById(id);
        return new ApiResponse<>(SUCCESS,NOT_CONTENT,DELETED);
    }

    @Override
    public ApiResponse<ItemResponse> getItemByNameAndPrice(String name, BigDecimal price) {
        Item item = itemRepository.findByNameAndPrice(name, price)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));

        return new ApiResponse<>(SUCCESS,OKAY,Mapper.convertObject(item, ItemResponse.class));
    }

}
