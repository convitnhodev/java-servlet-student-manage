package model.search.base;

import java.util.List;


public class BasePageAndSort extends BasePage {
    protected List<BaseSort> sorts;

    public List<BaseSort> getSorts() {
        return sorts;
    }

    public void setSorts(List<BaseSort> sorts) {
        this.sorts = sorts;
    }
}
