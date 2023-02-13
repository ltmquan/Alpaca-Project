package project.alpacabe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "deadline")
	private Date deadline;

	@Column(name = "priority_level")
	private Integer priorityLevel;

	@Column(name = "description")
	private String description;

	@Column(name = "finished")
	private Boolean finished;

	@ManyToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Task parent;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

}
