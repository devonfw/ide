package com.devonfw.tools.ide.url.folderhandling;

public class UrlTool extends UrlHasChildParentArtifact<UrlRepository, UrlEdition> {

	public UrlTool(UrlRepository parent, String name) {

		super(parent, name);
	}

	@Override
	protected UrlEdition newChild(String name) {

		return new UrlEdition(this, name);
	}

}
