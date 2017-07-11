package main.frixs.lyricssearch.model;

/**
 * @author Frixs
 */
public enum MainWindowTabType {
    PREVIEW     ("Preview"),
    ADDNEW      ("AddNew"),
    MANAGEMENT  ("Management");

    private final String value;

    /**
     * Constructor
     * @param value
     */
    private MainWindowTabType(final String value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return value;
    }
}