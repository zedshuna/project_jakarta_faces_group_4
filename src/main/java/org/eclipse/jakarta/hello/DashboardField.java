package org.eclipse.jakarta.hello;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.faces.model.SelectItem;

public class DashboardField implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String label;
    private final String type;
    private final List<SelectItem> options;

    public DashboardField(String label, String type, String... options) {
        this.label = label;
        this.type = type;
        ArrayList<SelectItem> items = new ArrayList<>();
        for (String option : options) {
            items.add(new SelectItem(option, option));
        }
        this.options = items;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public List<SelectItem> getOptions() {
        return options;
    }
}
