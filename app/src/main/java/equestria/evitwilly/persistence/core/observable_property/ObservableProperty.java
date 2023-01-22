package equestria.evitwilly.persistence.core.observable_property;

public class ObservableProperty<T> {

    private T value;
    private ObservablePropertyInterface<T> propertyInterface;

    public ObservableProperty(T defaultValue) {
        this.value = defaultValue;
    }

    protected void setValue(T value) {
        this.value = value;
        if (propertyInterface != null) {
            propertyInterface.onChanged(value);
        }
    }

    public T getValue() {
        return value;
    }

    public void addChangedListener(ObservablePropertyInterface<T> propertyInterface) {
        this.propertyInterface = propertyInterface;
        propertyInterface.onChanged(value);
    }

    public void clearChangedListeners() {
        propertyInterface = null;
    }

}


