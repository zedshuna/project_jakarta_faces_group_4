package org.eclipse.jakarta.hello;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DashboardRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<String> values;

    public DashboardRecord(String... values) {
        this.values = new ArrayList<>(List.of(values));
    }

    public DashboardRecord(List<String> values) {
        this.values = new ArrayList<>(values);
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = new ArrayList<>(values);
    }
}
