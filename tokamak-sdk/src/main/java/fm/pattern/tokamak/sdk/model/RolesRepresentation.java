package fm.pattern.tokamak.sdk.model;

import java.util.ArrayList;
import java.util.List;

import fm.pattern.tokamak.sdk.commons.Representation;

public class RolesRepresentation extends Representation {

	private List<RoleRepresentation> roles = new ArrayList<RoleRepresentation>();

	public RolesRepresentation() {

	}

	public RolesRepresentation(List<RoleRepresentation> roles) {
		this.roles = roles;
	}

	public List<RoleRepresentation> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleRepresentation> roles) {
		this.roles = roles;
	}

}
