package kpi.fire.domain;

public enum FireKind {
    LOAD_REGULATED,
    VENTILATION_REGULATED;

    public String toUkrString() {
        return this == LOAD_REGULATED ? "Пожежа, що регулюється навантаженням" : "Пожежа, що регулюється вентиляцією";
    }

}
