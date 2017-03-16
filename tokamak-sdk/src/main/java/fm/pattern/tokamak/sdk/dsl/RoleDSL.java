package fm.pattern.tokamak.sdk.dsl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import fm.pattern.tokamak.sdk.JwtClientProperties;
import fm.pattern.tokamak.sdk.RolesClient;
import fm.pattern.tokamak.sdk.commons.Result;
import fm.pattern.tokamak.sdk.model.RoleRepresentation;

public class RoleDSL extends AbstractDSL<RoleDSL, RoleRepresentation> {

    private RolesClient client = new RolesClient(JwtClientProperties.getEndpoint());

    private String name = randomAlphanumeric(10);
    private String description = "description";

    public static RoleDSL role() {
        return new RoleDSL();
    }

    public RoleDSL withName(String name) {
        this.name = name;
        return this;
    }

    public RoleDSL withDescription(String description) {
        this.description = description;
        return this;
    }

    public RoleRepresentation build() {
        RoleRepresentation representation = create();
        if (!shouldPersist()) {
            return representation;
        }

        Result<RoleRepresentation> response = client.create(representation, super.getToken().getAccessToken());
        if (response.rejected()) {
            throw new IllegalStateException("Unable to create role, response from server: " + response.getErrors().toString());
        }

        return response.getInstance();
    }

    private RoleRepresentation create() {
        RoleRepresentation representation = new RoleRepresentation();
        representation.setName(name);
        representation.setDescription(description);
        return representation;
    }

}