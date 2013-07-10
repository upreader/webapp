package com.upreader.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.UUID;

import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.upreader.BusinessException;
import com.upreader.RequestFile;
import com.upreader.UpreaderConstants;
import com.upreader.aws.AmazonService;
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
			return addProjectStep1(context);
		case "s2":
			return addProjectStep2(context);
		default:
			return handler.homepage();
		}
	}

	private boolean addProjectStep1(Context context) {
		// create a new project and store it on session
		// until wizard is complete
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

		try {
			if (book == null)
				throw new BusinessException("Book file not specified");
			if (sample == null)
				throw new BusinessException("Sample file not specified");
			if (cover == null)
				throw new BusinessException("Cover file not specified");

			if (book.getExtension() == null)
				throw new BusinessException("Book file does not have an extension");

			if (sample.getExtension() == null)
				throw new BusinessException("Sample file does not have an extension");

			if (cover.getExtension() == null)
				throw new BusinessException("Cover file does not have an extension");

			String bookUploadFile = UUID.randomUUID().toString();
			String sampleUploadFile = UUID.randomUUID().toString();
			String coverUploadFile = UUID.randomUUID().toString();

			try {
				Path bookPath = FileSystems.getDefault().getPath(handler.uploadFolder(), "books", bookUploadFile + '.' + book.getExtension());
				book.writeTo(bookPath);
				Path samplePath = FileSystems.getDefault().getPath(handler.uploadFolder(), "samples", sampleUploadFile + '.' + sample.getExtension());
				sample.writeTo(samplePath);
				Path coverPath = FileSystems.getDefault().getPath(handler.uploadFolder(), "covers", coverUploadFile + '.' + cover.getExtension());
				sample.writeTo(coverPath);
				
//				AmazonService aws = handler.getApplication().getAmazonService();
//				final Upload upload = aws.uploadFile(bookUploadFile+'.'+book.getExtension(), new FileInputStream(bookPath.toFile()), new ProgressListener() {
//					@Override
//					public void progressChanged(ProgressEvent evt) {
//					}
//				});
//				upload.waitForCompletion();
//				System.out.println(upload.getState());				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
			Project project = new Project();
			project.setTitle(title);
			project.setGenre(genre);
			project.setSubgenres(subgenres);
			project.setPitch(pitch);
			project.setSynopsis(synopsis);
			project.setReferences(references);
			project.setBackstory(backstory);
			project.setBook(bookUploadFile);
			project.setSample(sampleUploadFile);
			project.setCover(coverUploadFile);
			context.session().putObject(UpreaderConstants.SESSION_NEWPROJECT, project);

			return context.redirect(context.getContextPath() + "/p/newproject2");
		} catch (BusinessException e) {
			// set form fields to what user entered
			context.request().addAttribute("title", title);
			context.request().addAttribute("genre", genre);
			context.request().addAttribute("subgenres", subgenres);
			context.request().addAttribute("pitch", pitch);
			context.request().addAttribute("synopsis", synopsis);
			context.request().addAttribute("references", references);
			context.request().addAttribute("backstory", backstory);
			
			// return to Step 1 and show error
			context.request().addAttribute("uerror", e.getMessage());
			return context.render("newproject1.jsp");
		}
	}

	private boolean addProjectStep2(Context context) {
		Project project = context.session().getObject(UpreaderConstants.SESSION_NEWPROJECT);
		if (project == null)
			throw new RuntimeException("No project on session. Aborting...");

		Float bookPrice = context.query().getFloat("bookprice");
		Integer publishYears = context.query().getInt("pubyears");
		Integer percentToSale = context.query().getInt("percentsale");

		ProjectOwnership projectOwnership = new ProjectOwnership();
		projectOwnership.setBookPrice(bookPrice);
		projectOwnership.setPublishYears(publishYears);
		projectOwnership.setPercentToSale(percentToSale);
		projectOwnership.setProject(project);
		projectOwnership.setUser((User) context.session().getObject("_user_"));

		project.setBookPrice(bookPrice);
		project.setPublishYears(publishYears);
		project.setPercentToSale(percentToSale);
		project.setAuthor(projectOwnership);
		project.setApproved(false);

		context.projectDAO().insert(project);

		// project is created, remove it from session
		context.session().remove(UpreaderConstants.SESSION_NEWPROJECT);

		return true;
	}
}