package br.com.ealbornoz.constructor.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import br.com.ealbornoz.constructor.api.ItemWithName;

@MappedSuperclass
public abstract class AbstractItemWithName implements ItemWithName , Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	protected String name;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj != null) {
			if (obj instanceof String) {
				return name.equals(obj);
			} else if (obj instanceof ItemWithName) {
				if (name == null && ((ItemWithName) obj).getName() == null) {
					return true;
				} else if (name != null) {
					return name.equals(((ItemWithName) obj).getName());
				}
			}
		}

		return false;
	}
}
