package kostadin.ecommers.fishing.domain.models.service;

import kostadin.ecommers.fishing.domain.models.service.BaseServiceModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoryServiceModel extends BaseServiceModel {

    private String name;

    @NotNull
    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
