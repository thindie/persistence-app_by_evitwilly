package equestria.evitwilly.persistence.core.observable_property;

public class MutableObservableProperty<T> extends ObservableProperty<T> {

    public MutableObservableProperty(T defaultValue) {
        super(defaultValue);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

}