package com.company.inventory.response;

public class CategoryResponseRest extends ResponseRest{
    private CategoryResponseList categoryResponseList;

    public CategoryResponseList getCategoryResponseList() {
        return categoryResponseList;
    }

    public void setCategoryResponseList(CategoryResponseList categoryResponseList) {
        this.categoryResponseList = categoryResponseList;
    }
}
