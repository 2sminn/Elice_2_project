package com.elice.kittyandpuppy.module.category.controller;

import com.elice.kittyandpuppy.module.category.dto.CategoryDto;
import com.elice.kittyandpuppy.module.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Category", description = "카테고리 관련 API")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/categories")
    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "생성된 카테고리의 ID를 반환합니다.", content = @Content(schema = @Schema(implementation = Long.class)))
    public Long saveCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.saveCategory(categoryDto);
    }

    @GetMapping("/categories/{branch}")
    @Operation(summary = "지점별 카테고리 조회", description = "특정 지점에 해당하는 카테고리 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "지점별 카테고리 정보를 반환합니다.", content = @Content(schema = @Schema(implementation = Map.class)))
    public Map<String, CategoryDto> getCategoryByBranch(@PathVariable String branch){
        return categoryService.getCategoryByBranch(branch);
    }

    @PutMapping("/categories/{categoryId}")
    @Operation(summary = "카테고리 업데이트", description = "지정된 ID의 카테고리 정보를 업데이트합니다.")
    @ApiResponse(responseCode = "200", description = "업데이트된 카테고리의 ID를 반환합니다.", content = @Content(schema = @Schema(implementation = Long.class)))
    public Long updateCategory (@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryId, categoryDto);
    }

    @DeleteMapping ("/categories/{categoryId}")
    @Operation(summary = "카테고리 삭제", description = "지정된 ID의 카테고리를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "카테고리가 성공적으로 삭제되었습니다.")
    public void deleteCategory (@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
