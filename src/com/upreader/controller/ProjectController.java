package com.upreader.controller;

import com.upreader.RequestFile;
import com.upreader.UpreaderConstants;
import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.model.Project;
import com.upreader.model.ProjectOwnership;
import com.upreader.model.User;

public class ProjectController {
	private final UpreaderHandler handler;

	public ProjectController(BasicPathHandler handler) {
		this.handler = (UpreaderHandler) handler;
	}

	public boolean doCmd(Context context) {
		String cmd = context.query().get("do");
		switch (cmd) {
		case "s1":
			addProjectStep1(context);
			context.redirect(context.getContextPath()+"/p/newproject2");
			break;
		case "s2":
			addProjectStep2(context);
			break;
		}

		return handler.homepage();
	}

	private void addProjectStep1(Context context) {
		// create a new project and store it on session
		// until wizard is complete
		Project project = new Project();
		String title = context.query().get("title");
		String genre = context.query().get("genre");
		String subgenres = context.query().get("subgenres");
		RequestFile book = context.files().get("book");
		RequestFile sample = context.files().get("sample");
		RequestFile cover = context.files().get("cover");
		String pitch = context.query().get("pitch");
		String synopsis = context.query().get("synopsis");
		String references = context.query().get("references");
		String backstory = context.query().get("backstory");

		project.setTitle(title);
		project.setGenre(genre);
		project.setSubgenres(subgenres);
		project.setPitch(pitch);
		project.setSynopsis(synopsis);
		project.setReferences(references);
		project.setBackstory(backstory);

		context.session().putObject(UpreaderConstants.SESSION_NEWPROJECT, project);
	}

	private void addProjectStep2(Context context) {
		Project project = context.session().getObject(UpreaderConstants.SESSION_NEWPROJECT);
		if(project == null)
			throw new RuntimeException("No project on session. Aborting...");
		
		Float bookPrice = context.query().getFloat("bookprice");
		Integer publishYears = context.query().getInt("pubyears");
		Integer percentToSale = context.query().getInt("percentsale");
		
		ProjectOwnership projectOwnership = new ProjectOwnership();
		projectOwnership.setBookPrice(bookPrice);
		projectOwnership.setPublishYears(publishYears);
		projectOwnership.setPercentToSale(percentToSale);
		projectOwnership.setProject(project);
		projectOwnership.setUser((User)context.session().getObject("_user_"));
		
		project.setBookPrice(bookPrice);
		project.setPublishYears(publishYears);
		project.setPercentToSale(percentToSale);
		project.setAuthor(projectOwnership);
		project.setApproved(false);
		
		context.projectDAO().insert(project);
		
		// project is created, remove it from session
		context.session().remove(UpreaderConstants.SESSION_NEWPROJECT);
	}
}
