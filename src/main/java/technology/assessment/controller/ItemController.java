package technology.assessment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import technology.assessment.model.dto.ItemDTO;
import technology.assessment.model.dto.response.ApiResponse;
import technology.assessment.model.dto.response.ItemResponse;
import technology.assessment.service.ItemService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static technology.assessment.util.ItemEndpoints.*;
import static technology.assessment.util.ParamName.*;
import static technology.assessment.util.ParamName.SIZE_DEFAULT;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping(ADD)
    ApiResponse<String> addItem(@Valid @RequestBody ItemDTO payload){
        return itemService.addItem(payload);
    }
    @PostMapping(BULK_ADD)
    ApiResponse<String> addItem(@Valid @RequestBody List<ItemDTO> payloadList){
        return itemService.addItems(payloadList);
    }
    @PostMapping(UPDATE)
    ApiResponse<String> updateItem(@Valid @RequestBody ItemDTO payload){
        return itemService.updateItem(payload);
    }
    @GetMapping(LIST)
    ApiResponse<List<ItemResponse>> listItem(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
                                             @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size){
        return itemService.listItem(page,size);

    }

    @GetMapping(GET_BY_ID)
    ApiResponse<ItemResponse> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @DeleteMapping(DELETE)
    ApiResponse<String> addItem(@PathVariable Long id){
        return itemService.deleteItem(id);
    }

    @GetMapping
    ApiResponse<ItemResponse> getItemByNameAndPrice(@RequestParam String name, @RequestParam BigDecimal price) {
        return itemService.getItemByNameAndPrice(name, price);
    }
}
