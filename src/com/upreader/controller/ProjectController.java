package com.upreader.controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import com.upreader.BusinessException;
import com.upreader.RequestFile;
import com.upreader.UpreaderConstants;
import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.helper.AddProjectWizardHelper;
import com.upreader.model.Project;
import com.upreader.model.User;

public class ProjectController extends BasicController {
    private final AddProjectWizardHelper addProjectHelper;

    public ProjectController(BasicPathHandler handler, Context context) {
        super(handler, context);
        addProjectHelper = new AddProjectWizardHelper(handler, context);
    }

	public boolean doCmd() {
		String cmd = context().query().get("do");
		switch (cmd) {
        case "listPrjs":
            return handler().json(loadProjectsTableJson(Integer.valueOf(context().query().get("startPos")).intValue(),
                                                        Integer.valueOf(context().query().get("endPos")).intValue() ));
        case "addingProject":
            return addProjectHelper.getWizardDataJson();
        case "addingProjectUploadFile":
            return addProjectHelper.uploadProjectFile();
        case "postingProject":
            return addProjectHelper.getPostProjectResult();
        case "viewProject":
            return addProjectHelper.getPostProjectResult();
		default:
			return handler().homepage();
		}
	}

    //TODO
    //TO delete
//	private boolean addProjectStep1(Context context) {
//		// create a new project and store it on session
//		// until wizard is complete
//		String title = context().query().get("title");
//		String genre = context().query().get("genre");
//		String subgenres = context().query().get("subgenres");
//		RequestFile book = context().files().get("book");
//		RequestFile sample = context().files().get("sample");
//		RequestFile cover = context().files().get("cover");
//		String pitch = context().query().get("pitch");
//		String synopsis = context().query().get("synopsis");
//		String references = context().query().get("references");
//		String backstory = context().query().get("backstory");
//
//		try {
//			if (book == null)
//				throw new BusinessException("Book file not specified");
//			if (sample == null)
//				throw new BusinessException("Sample file not specified");
//			if (cover == null)
//				throw new BusinessException("Cover file not specified");
//
//			if (book.getExtension() == null)
//				throw new BusinessException("Book file does not have an extension");
//
//			if (sample.getExtension() == null)
//				throw new BusinessException("Sample file does not have an extension");
//
//			if (cover.getExtension() == null)
//				throw new BusinessException("Cover file does not have an extension");
//
//			String bookUploadFile = UUID.randomUUID().toString();
//			String sampleUploadFile = UUID.randomUUID().toString();
//			String coverUploadFile = UUID.randomUUID().toString();
//
//			try {
//				Path bookPath = FileSystems.getDefault().getPath(handler().uploadFolder(), "books", bookUploadFile + '.' + book.getExtension());
//				book.writeTo(bookPath);
//				Path samplePath = FileSystems.getDefault().getPath(handler().uploadFolder(), "samples", sampleUploadFile + '.' + sample.getExtension());
//				sample.writeTo(samplePath);
//				Path coverPath = FileSystems.getDefault().getPath(handler().uploadFolder(), "covers", coverUploadFile + '.' + cover.getExtension());
//				sample.writeTo(coverPath);
//
////				AmazonService aws = handler.getApplication().getAmazonService();
////				final Upload upload = aws.uploadFile(bookUploadFile+'.'+book.getExtension(), new FileInputStream(bookPath.toFile()), new ProgressListener() {
////					@Override
////					public void progressChanged(ProgressEvent evt) {
////					}
////				});
////				upload.waitForCompletion();
////				System.out.println(upload.getState());
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//
//			Project project = new Project();
//			project.setTitle(title);
//			project.setGenre(genre);
//			project.setSubgenre(subgenres);
//			project.setSynopsis(synopsis);
//			project.setBook(bookUploadFile);
//			project.setCover(coverUploadFile);
//			context().session().putObject(UpreaderConstants.SESSION_NEWPROJECT, project);
//
//			return context().redirect(context().getContextPath() + "/p/newproject2");
//		} catch (BusinessException e) {
//			// set form fields to what user entered
//			context().request().addAttribute("title", title);
//			context().request().addAttribute("genre", genre);
//			context().request().addAttribute("subgenres", subgenres);
//			context().request().addAttribute("pitch", pitch);
//			context().request().addAttribute("synopsis", synopsis);
//			context().request().addAttribute("references", references);
//			context().request().addAttribute("backstory", backstory);
//
//			// return to Step 1 and show error
//			context().request().addAttribute("uerror", e.getMessage());
//			return context().render("newproject1.jsp");
//		}
//	}
//
//	private boolean addProjectStep2(Context context) {
//		Project project = context().session().getObject(UpreaderConstants.SESSION_NEWPROJECT);
//		if (project == null)
//			throw new RuntimeException("No project on session. Aborting...");
//
//		Float bookPrice = context().query().getFloat("bookprice");
//
//		project.setBookPrice(bookPrice);
//		project.setAuthor((User) context().session().getObject("_user_"));
//		project.setApproved(false);
//
//		context().projectDAO().insert(project);
//
//		// project is created, remove it from session
//		context().session().remove(UpreaderConstants.SESSION_NEWPROJECT);
//
//		return true;
//	}

    public List<Project> loadProjectsTableJson(int startPos, int endPos){
        return handler().context().projectDAO().findAllProjectsInRange(startPos, endPos);
    }
}
