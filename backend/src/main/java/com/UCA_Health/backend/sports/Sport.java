package com.UCA_Health.backend.sports;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "sports",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_sports_name", columnNames = {"name"})
    }
)


@AllArgsConstructor
@NoArgsConstructor
@Builder //Construcción sin id
public class Sport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;
	
    @Column(columnDefinition = "TEXT")  //permite almacenar texto largo para descripción
    private String description;
    
    @Size(max = 50)
    @Column(length = 50)
	private String category;
    
    
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sport)) return false;
        Sport other = (Sport) o;

        return id != null && Objects.equals(id, other.id);
    }

    
    @Override
    public int hashCode() {
        return 31;
    }
    
    @Override
    public String toString() {
        return "Sport{id=" + id +
               ", name='" + name + '\'' +
               ", category='" + category + '\'' +
               '}';
    }


	public Long getId() {
		return id;
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	
}

