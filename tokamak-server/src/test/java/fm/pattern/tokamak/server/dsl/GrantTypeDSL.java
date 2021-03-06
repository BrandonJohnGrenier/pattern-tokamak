package fm.pattern.tokamak.server.dsl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import fm.pattern.tokamak.server.model.GrantType;
import fm.pattern.tokamak.server.service.GrantTypeService;
import fm.pattern.valex.Result;

public class GrantTypeDSL extends AbstractDSL<GrantTypeDSL, GrantType> {

	private String name = randomAlphabetic(10);
	private String description = null;

	public static GrantTypeDSL grantType() {
		return new GrantTypeDSL();
	}

	public GrantTypeDSL withName(String name) {
		this.name = name;
		return this;
	}

	public GrantTypeDSL withDescription(String description) {
		this.description = description;
		return this;
	}

	public GrantType build() {
		return create();
	}

	public GrantType save() {
		Result<GrantType> result = load(GrantTypeService.class).create(create());
		if (result.accepted()) {
			return result.getInstance();
		}
		throw new IllegalStateException("Unable to create grant type, errors: " + result.getErrors().toString());
	}

	private GrantType create() {
		GrantType grantType = new GrantType(name);
		grantType.setDescription(description);
		return grantType;
	}

}
