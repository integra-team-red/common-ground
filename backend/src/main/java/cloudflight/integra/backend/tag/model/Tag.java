package cloudflight.integra.backend.tag.model;

public class Tag {
    private Long id;
    private String label;
    private String normalizedLabel;

    public Tag(Long id, String label, String normalizedLabel) {
        this.id = id;
        this.label = label;
        this.normalizedLabel = normalizedLabel;
    }

    public Tag() { }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public String getNormalizedLabel() {
        return normalizedLabel;
    }
    public void setNormalizedLabel(String normalizedLabel) {
        this.normalizedLabel = normalizedLabel;
    }
}
