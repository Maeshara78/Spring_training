import jakarta.persistence.*;

@Entity
@Table(name = "SP_WINDOW")
public class WindowEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    // (5)
    private SensorEntity windowStatus;

    public WindowEntity() {
    }

    public WindowEntity(String name, SensorEntity sensor) {
        this.windowStatus = sensor;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sensor getWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(Sensor windowStatus) {
        this.windowStatus = windowStatus;
    }
}