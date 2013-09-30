<?xml version="1.0" ?>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<jsp:useBean id="upreaderConst" class="com.upreader.UpreaderConstants" scope="session"/>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="com.upreader.UpreaderApplication" %>
<%@ page import="com.upreader.UpreaderConstants" %>
<%@ page import="com.upreader.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    //Retreive the resourceBundle for the current language according to the user's locale.
    ResourceBundle upreaderResources = UpreaderApplication.getInstance().getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", request.getLocale());

    /*
     * Read request params
     */
    String _type     = request.getParameter("type")  == null ? "0" : request.getParameter("type");
    String _view     = request.getParameter("view_type")  == null ? upreaderConst.DEFAULT_VIEW : request.getParameter("view_type");
    String _editor   = request.getParameter("editor") == null ? "" : request.getParameter("editor");
    String _lang     = request.getLocale().toString();

    /*
     * Set the directory inside the bucket
     */
    String _cur_dir = "";
    if(_type.equals("1")){
        User loggedUser = (User) session.getAttribute(UpreaderConstants.SESSION_USER);
        _cur_dir += upreaderConst.IMAGES_PATH_INSIDE_UPREADER_BUCHET;
        _cur_dir +=  loggedUser.getEmail() + "/";
    }
%>

<!doctype html>
<html ng-app="tinyfileManagerApp">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="robots" content="noindex,nofollow">
        <title><%=upreaderResources.getString("tinyMCE.fileManager.title")%></title>
        <link href="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/css/bootstrap-lightbox.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/css/style.css" rel="stylesheet" type="text/css" />
	    <link href="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/css/dropzone.css" type="text/css" rel="stylesheet" />
        <!--[if lt IE 8]><style>
        .img-container span {
            display: inline-block;
            height: 100%;
        }
        </style><![endif]-->
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/js/filemanagerController.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/js/bootstrap-lightbox.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/js/dropzone.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/js/jquery.touchSwipe.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/js/modernizr.custom.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/js/bootbox.min.js"></script>

        <script>
            var ext_img=new Array(<%=upreaderConst.EXT_IMG%>);
            var allowed_ext=new Array(<%
                                        if(_type.equals("1")){ %>
                                            <%=upreaderConst.EXT_IMG%>
                                        <%}else{ %>
                                            <%=upreaderConst.EXT%>
                                         <% } %>);

            //dropzone config
            Dropzone.options.dropzoneUpload = {
                dictInvalidFileType: "<%=upreaderResources.getString("tinyMCE.fileManager.langErrorExtension")%>",
                dictFileTooBig: "<%=upreaderResources.getString("tinyMCE.fileManager.fileTooBig")%>",
                dictResponseError: "<%=upreaderResources.getString("tinyMCE.fileManager.serverError")%>",
                paramName: "file", // The name that will be used to transfer the file
                maxFilesize: <%=upreaderConst.MAX_UPLOAD_SIZE%>, // MB
                accept: function(file, done) {
                        var extension=file.name.split('.').pop().toLowerCase();
                        if ($.inArray(extension, allowed_ext) > -1) {
                            done();
                        }
                        else { done("<%=upreaderResources.getString("tinyMCE.fileManager.langErrorExtension")%>"); }
                }
            };
        </script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/js/include.js"></script>
    </head>
    <body ng-controller="tinyfileManagerController">
        <input type="hidden" id="type" value="<%=_type%>" />
        <input type="hidden" id="view" value="<%=_view%>" />
        <input type="hidden" id="track" value="<%=_editor%>" />
        <input type="hidden" id="cur_dir" value="<%=_cur_dir%>" />
        <input type="hidden" id="ok" value="<%=upreaderResources.getString("tinyMCE.fileManager.OK")%>" />
        <input type="hidden" id="cancel" value="<%=upreaderResources.getString("tinyMCE.fileManager.Cancel")%>" />
        <input type="hidden" id="rename" value="<%=upreaderResources.getString("tinyMCE.fileManager.Rename")%>" />
        <%  //if having permission to upload files
            if(upreaderConst.UPLOAD_FILES.equals("true")){ %>
        <!----- uploader div start ------->
        <div class="uploader">
            <center><button class="btn btn-inverse close-uploader"><i class="icon-backward icon-white"></i>  <%=upreaderResources.getString("tinyMCE.fileManager.returnToFileList")%></button>  </center>
            <div class="space10"></div><div class="space10"></div>
            <% if(upreaderConst.JAVA_UPLOAD.equals("true")){ %>
            <div class="tabbable upload-tabbable"> <!-- Only required for left/right tabs -->
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#tab1" data-toggle="tab"> <%=upreaderResources.getString("tinyMCE.fileManager.uploadBase")%>  </a></li>
                    <li><a href="#tab2" id="uploader-btn" data-toggle="tab"> <%=upreaderResources.getString("tinyMCE.fileManager.uploadJava")%> </a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="tab1">
                        <% } %>
                        <form action="/upreader/i/s/p" method="post" enctype="multipart/form-data" id="dropzoneUpload" class="dropzone">
                              <input type="hidden" name="do"       value="addingProjectUploadFile"/>
                              <input type="hidden" name="fileType" value="<%=upreaderConst.PUBLIC_IMAGE%>"/>
                              <div class="fallback">
                                    <input name="file" type="file" multiple />
                              </div>
                        </form>
                        <% if(upreaderConst.JAVA_UPLOAD.equals("true")){ %>
                        </div>
                        <div class="tab-pane" id="tab2">
                            <div id="iframe-container">

                            </div>
                            <div class="java-help"><%=upreaderResources.getString("tinyMCE.fileManager.UploadFileHelpLabel")%></div>
                        </div>
                      <% } %>
                </div>
            </div>

        </div>
        <!----- uploader div end ------->
        <%}%>


        <div class="container-fluid">
            <!----- header div start ------->
            <div class="navbar navbar-fixed-top">
                <div class="navbar-inner">
                    <div class="container-fluid">
                        <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <div class="brand"><%=upreaderResources.getString("tinyMCE.fileManager.toolbar")%> -></div>
                        <div class="nav-collapse collapse">
                            <div class="filters">
                                <div class="row-fluid">
                                    <div class="span3 half">
                                        <span><%=upreaderResources.getString("tinyMCE.fileManager.actions")%>:</span>
                                        <% if(upreaderConst.UPLOAD_FILES.equals("true")){ %>
                                        <button class="tip btn upload-btn" title="<%=upreaderResources.getString("tinyMCE.fileManager.UploadFileLabel")%>"><i class="icon-plus"></i><i class="icon-file"></i></button>
                                        <% } %>
                                        <% if(upreaderConst.CREATE_FOLDER.equals("true")){ %>
                                        <button class="tip btn new-folder" title="<%=upreaderResources.getString("tinyMCE.fileManager.NewFolder")%>"><i class="icon-plus"></i><i class="icon-folder-open"></i></button>
                                        <% } %>
                                    </div>
                                    <div class="span3 half view-controller">
                                        <span><%=upreaderResources.getString("tinyMCE.fileManager.view")%>:</span>
                                        <button class="btn tip<% if(_view.equals("0")){%> btn-inverse <%}%>" id="view0" data-value="0"
                                                title="<%=upreaderResources.getString("tinyMCE.fileManager.viewBoxes")%>"><i class="icon-th <% if(_view.equals("0")){%> icon-white <%}%>"></i></button>
                                        <button class="btn tip<% if(_view.equals("1")){%> btn-inverse <%}%>" id="view1" data-value="1"
                                                title="<%=upreaderResources.getString("tinyMCE.fileManager.viewList")%>"><i class="icon-align-justify <% if(_view.equals("1")){%> icon-white <%}%>"></i></button>
                                        <button class="btn tip<% if(_view.equals("2")){%> btn-inverse <%}%>" id="view2" data-value="2"
                                                title="<%=upreaderResources.getString("tinyMCE.fileManager.viewColumnsList")%>"><i class="icon-fire <% if(_view.equals("2")){%> icon-white <%}%>"></i></button>
                                    </div>


                                        <div class="span6 types">
                                            <span><%=upreaderResources.getString("tinyMCE.fileManager.FiltersLabel")%>:</span>
                                            <% if(!_type.equals("1")){%>
                                                <input id="select-type-1" name="radio-sort" type="radio" data-item="ff-item-type-1" checked="checked"  class="hide"  />
                                                <label id="ff-item-type-1" title="<%=upreaderResources.getString("tinyMCE.fileManager.FilesLabel")%>" for="select-type-1" class="tip btn ff-label-type-1"><i class="icon-file"></i></label>
                                                <input id="select-type-2" name="radio-sort" type="radio" data-item="ff-item-type-2" class="hide"  />
                                                <label id="ff-item-type-2" title="<%=upreaderResources.getString("tinyMCE.fileManager.ImagesLabel")%>" for="select-type-2" class="tip btn ff-label-type-2"><i class="icon-picture"></i></label>
                                                <input id="select-type-3" name="radio-sort" type="radio" data-item="ff-item-type-3" class="hide"  />
                                                <label id="ff-item-type-3" title="<%=upreaderResources.getString("tinyMCE.fileManager.ArchivesLabel")%>" for="select-type-3" class="tip btn ff-label-type-3"><i class="icon-inbox"></i></label>
                                                <input id="select-type-4" name="radio-sort" type="radio" data-item="ff-item-type-4" class="hide"  />
                                                <label id="ff-item-type-4" title="<%=upreaderResources.getString("tinyMCE.fileManager.VideosLabel")%>" for="select-type-4" class="tip btn ff-label-type-4"><i class="icon-film"></i></label>
                                                <input id="select-type-5" name="radio-sort" type="radio" data-item="ff-item-type-5" class="hide"  />
                                                <label id="ff-item-type-5" title="<%=upreaderResources.getString("tinyMCE.fileManager.MusicLabel")%>" for="select-type-5" class="tip btn ff-label-type-5"><i class="icon-music"></i></label>
                                            <%}%>
                                            <input accesskey="f" type="text" class="filter-input" id="filter-input" name="filter" placeholder="<%=upreaderResources.getString("tinyMCE.fileManager.textFiltersLabel")%>..." value=""/>

                                            <input id="select-type-all" name="radio-sort" type="radio" data-item="ff-item-type-all" class="hide" />
                                            <label id="ff-item-type-all" title="<%=upreaderResources.getString("tinyMCE.fileManager.FiltersAllLabel")%>" for="select-type-all" class="tip btn btn-inverse ff-label-type-all"><i class="icon-align-justify icon-white"></i></label>
                                        </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!----- header div end ------->

            <!----- breadcrumb div start ------->
            <div class="row-fluid">
                <ul class="breadcrumb">
                    <li class="pull-left">
                        <a href="dialog?type=<%=_type%>"><i class="icon-home"></i></a>
                    </li>
                    <li>
                        <span class="divider">/</span>
                    </li>
                    <li class="pull-right">
                        <a id="refresh" href="dialog?type=<%=_type%>"><i class="icon-refresh"></i></a>
                    </li>
                </ul>
            </div>
            <!----- breadcrumb div end ------->
            <div class="row-fluid ff-container">
               <input type="hidden"  id="s3FolderContents" value=<%=UpreaderApplication.getInstance().getAmazonService().listFolderContents(_cur_dir, true)%> />

                <% if(upreaderConst.SHOW_SORTING_BAR.equals("true")){ %>
                <!-- sorter -->
                <div class="sorter-container list-view<%=_view%>">
                    <div class="file-name"><a class="sorter" href="javascript:void('')" data-sort="name"><%=upreaderResources.getString("tinyMCE.fileManager.sortByFilename")%></a></div>
                    <div class="file-date"><a class="sorter" href="javascript:void('')" data-sort="date"><%=upreaderResources.getString("tinyMCE.fileManager.sortByDate")%></a></div>
                    <div class="file-size"><a class="sorter" href="javascript:void('')" data-sort="size"><%=upreaderResources.getString("tinyMCE.fileManager.sortBySize")%></a></div>
                    <div class='file-extension'><a class="sorter" href="javascript:void('')" data-sort="extension"><%=upreaderResources.getString("tinyMCE.fileManager.sortByExt")%></a></div>
                    <div class='file-operations'><%=upreaderResources.getString("tinyMCE.fileManager.operations")%></div>
                </div>
                <% } %>

                <!--ul class="thumbnails ff-items"-->
                <ul class="grid cs-style-2 list-view<%=_view%>">
                    <li ng-repeat="currentFile in currentListing" class="ff-item-type-{{currentFile.ext}}"  data-name={{currentFile.fileName}}>
                        <figure>
                            <a href="javascript:void('')" title="<%=upreaderResources.getString("tinyMCE.fileManager.select")%>"
                               class="link"
                               data-file={{currentFile.fileName}}
                               data-type="<%=_type%>"
                               data-field_id={{currentFile.link}}
                                   data-function="<% if(_type.equals("1")) {%>apply_img<%}%>">
                                <div class="img-precontainer">
                                    <div class="img-container">
                                        <span></span>
                                        <img alt="image" class="original" width="64" height="64" ng-src={{currentFile.link}} />
                                    </div>
                                </div>
                                <div class="img-container-mini original-thumb">
                                    <img alt="image" width="41" height="64" ng-src={{currentFile.link}}>
                                </div>
                            </a>
                            <div class="box">
                                <h4>
                                    <a href="javascript:void('')" title="<%=upreaderResources.getString("tinyMCE.fileManager.select")%>"
                                       class="link" data-file={{currentFile.fileName}} data-type="<%=_type%>"
                                       data-function=""
                                       ng-bind="currentFile.fileName"></a>
                                </h4>
                            </div>

                            <div class="file-date" ng-bind="currentFile.lastModified | date:<%=upreaderResources.getString("projectsTable.dateFormatShort")%>"></div>
                            <div class="file-size" ng-bind="currentFile.size"></div>
                            <div class='file-extension' ng-bind="currentFile.ext"></div>
                            <figcaption>
                                <form method="post" class="download-form">
                                    <a title="Download" class="tip-right" ng-href={{currentFile.link}} download><i class="icon-download"></i></a>

                                    <a  class="tip-right preview"
                                        title="<%=upreaderResources.getString("tinyMCE.fileManager.preview")%>"
                                        data-url="{{currentFile.link}}"
                                        data-toggle="lightbox"
                                        href="#previewLightbox"
                                        ng-show="currentFile.itIsImage">
                                        <i class=" icon-eye-open"></i>
                                    </a>
                                    <a ng-show="!currentFile.itIsImage" class="preview disabled"><i class="icon-eye-open icon-white"></i></a>

                                    <% if(upreaderConst.EDIT_FILE.equals("true")){%>
                                        <a href="javascript:void('')" class="tip-left edit-button">
                                        <i class="icon-pencil icon-white"></i></a>
                                    <%}%>

                                    <a href="javascript:void('')"
                                       class="tip-left erase-button <% if(upreaderConst.DELETE_FILE.equals("true")){%> delete-file"<%}%>
                                       title="<%=upreaderResources.getString("tinyMCE.fileManager.delete")%>"
                                       data-confirm="<%=upreaderResources.getString("tinyMCE.fileManager.confirmDelete")%>"
                                       data-path="" data-thumb="">
                                    <i class="icon-trash <% if(!upreaderConst.DELETE_FILE.equals("true")){%> icon-white"<%}%>"></i>
                                    </a>
                                </form>
                            </figcaption>
                        </figure>
                    </li>
                    </ul>
            </div>
        </div>
    <!----- lightbox div start ------->
    <div id="previewLightbox" class="lightbox hide fade"  tabindex="-1" role="dialog" aria-hidden="true">
	    <div class='lightbox-content'>
		    <img id="full-img" src="">
	    </div>
    </div>
    <!----- lightbox div end ------->

    <!----- loading div start ------->
    <div id="loading_container" style="display:none;">
	    <div id="loading" style="background-color:#000; position:fixed; width:100%; height:100%; top:0px; left:0px;z-index:100000"></div>
	    <img id="loading_animation" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/img/storing_animation.gif" alt="loading" style="z-index:10001; margin-left:-32px; margin-top:-32px; position:fixed; left:50%; top:50%"/>
    </div>
    <!----- loading div end ------->

</body>
</html>
