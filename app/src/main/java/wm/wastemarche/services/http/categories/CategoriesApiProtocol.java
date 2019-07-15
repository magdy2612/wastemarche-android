package wm.wastemarche.services.http.categories;

import java.util.List;

import wm.wastemarche.model.Category;
import wm.wastemarche.services.http.ApiProtocol;

public interface CategoriesApiProtocol extends ApiProtocol {
    void categoriesLoaded(List<Category> categories);

    void categoriesFialed(String error);
}
