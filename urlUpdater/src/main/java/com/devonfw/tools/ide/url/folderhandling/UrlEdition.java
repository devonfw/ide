package com.devonfw.tools.ide.url.folderhandling;

public class UrlEdition extends UrlHasChildParentArtifact<UrlTool, UrlVersion> {

	public UrlEdition(UrlTool parent, String name) {

		super(parent, name);
	}

	@Override
	protected UrlVersion newChild(String name) {

		return new UrlVersion(this, name);
	}
}
