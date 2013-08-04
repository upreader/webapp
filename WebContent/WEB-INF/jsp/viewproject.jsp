<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/viewproject.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css" media="screen"/>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/js/jquery.leanModal.min.js"></script>
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-view-project">
<div class="page-header-wrapper">
    <div class="page-header">
        <div class="user-profile">
            <div class="user-profile-photo-wrapper">
                <a class="user-profile-photo" href="#">
                    <img class="user-profile-photo-img"
                         src="http://lorempixum.com/q/260/320/people/game%20of%20chairs"/>
                </a>
            </div>
            <div class="user-profile-details-wrapper">
                <div class="user-profile-details">
                    <a class="user-profile-name" href="#">John Doe</a>
                    <a class="user-profile-rating" href="#">Rating: 1</a>
                    <a class="user-profile-profile" href="#">Profile: 60%</a>

                    <div class="user-profile-profile-bar">
                        ------------
                    </div>
                </div>
            </div>
        </div>
        <div class="user-projects">
            <img src="${pageContext.request.contextPath}/images/viewproject-header-flipper.jpg">
        </div>
        <div class="user-actions">
            <img src="${pageContext.request.contextPath}/images/viewproject-header-other.jpg">
        </div>
    </div>
</div>
<div class="project-content-wrapper">
<div class="project-content">
<div class="project-content-sidebar">
    <div class="sidebar-status">
        <ul>
            <li>
                <div class="sidebar-timeleft">
                    <h1>8/25 days left</h1>
                </div>
            </li>
            <li>
                <div class="sidebar-sharesleft">
                    <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-sharesbar.jpg">
                </div>
            </li>
            <li>
                <div class="sidebar-royaltystock image-text-box clearfix">
                    <div class="royaltystock-image image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-royaltystock.jpg">
                    </div>
                    <div class="royaltystock-details image-text-box-text">
                        <h1>Royalty Stock:</h1>

                        <h1>5789$</h1>
                    </div>
                </div>
            </li>
            <li>
                <div class="sidebar-sharestotal image-text-box clearfix">
                    <div class="sharestotal-image image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-sharestotal.jpg">
                    </div>
                    <div class="sharestotal-details image-text-box-text">
                        <h1>shares total:</h1>

                        <h1>5789</h1>
                    </div>
                </div>
            </li>
            <li>
                <div class="sidebar-sharevalue image-text-box clearfix">
                    <div class="sharevalue-image image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-sharevalue.jpg">
                    </div>
                    <div class="sharevalue-details image-text-box-text">
                        <h1>share value: 1$</h1>
                    </div>
                </div>
            </li>
            <li>
                <div class="sidebar-upreaders image-text-box clearfix">
                    <div class="upreaders-image image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-upreader.jpg">
                    </div>
                    <div class="upreaders-details image-text-box-text">
                        <h1>956 Upreaders</h1>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="sidebar-terms">
        <div class="sidebar-derivatives">
            <div>
                <span>Available derivatives</span>
                        <span><img
                                src="${pageContext.request.contextPath}/images/viewproject-sidebar-question.jpg"></span>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-derivatives.jpg">
            </div>
        </div>
        <div class="sidebar-contract">
            <p>Years given to platform: 5</p>

            <p>Royalties given to platform: 5</p>
        </div>
    </div>
    <div class="sidebar-publishers">
        <div>
            <h2>Interested Publishers</h2>
        </div>
        <div>
            <div class="sidebar-publishers-box">
                <div>
                    <span>Barnes & Noble - 2 days ago</span>
                    <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-publishereye.jpg">
                </div>
                <div>
                    <span>Pengiun - 4 days ago</span>
                    <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-publishereye.jpg">
                </div>
            </div>
        </div>
    </div>
    <div class="sidebar-promopacks">
        <h2>PROMO-PACKS</h2>
        <ul>
            <li>
                <div class="promopack image-text-box clearfix">
                    <div class="promopack-image image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-addpromopack.jpg">
                    </div>
                    <div class="promopack-details image-text-box-text">
                        <p>500$ small campaign</p>
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-promopackbar.jpg">
                    </div>
                </div>
            </li>
            <li>
                <div class="promopack image-text-box clearfix">
                    <div class="promopack-image image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-addpromopack.jpg">
                    </div>
                    <div class="promopack-details image-text-box-text">
                        <p>1000$ medium campaign</p>
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-promopackbar.jpg">
                    </div>
                </div>
            </li>
            <li>
                <div class="promopack image-text-box clearfix">
                    <div class="promopack image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-addpromopack.jpg">
                    </div>
                    <div class="promopack image-text-box-text">
                        <p>$5000 large campaign</p>
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-promopackbar.jpg">
                    </div>
                </div>
            </li>
            <li>
                <div class="promopack-shareholder image-text-box clearfix">
                    <div class="promopack-shareholder image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-question.jpg">
                    </div>
                    <div class="promopack-shareholder image-text-box-text">
                        <div>Shareholder? Promote and get rich!</div>
                        <div>Choose a promo-pack and donate</div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="sidebar-user-status">
        <h1>Your status:</h1>

        <p>Shares owned: 6</p>

        <p>Dividend value: 0$</p>

        <p>Promo-pack donation: 0$</p>

        <p>Received promo materials: 5</p>
    </div>
    <div class="sidebar-dividend-stats">
        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-dividentstats.jpg">
    </div>
    <div class="sidebar-opened image-text-box clearfix">
        <div class="opened-image image-text-box-image">
            <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-opened.jpg">
        </div>
        <div class="opened-details image-text-box-text">
            <h1>Opened 4321 times</h1>
        </div>
    </div>
    <div class="sidebar-irs-stats">
        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-irsstats.jpg">
    </div>
    <div class="sidebar-soldbooks image-text-box clearfix">
        <div class="soldbooks-image image-text-box-image">
            <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-soldbooks.jpg">
        </div>
        <div class="soldbooks-details image-text-box-text">
            <h1>sold books 554</h1>
        </div>
    </div>
</div>
<div class="project-content-main">
    <article id="story">
        <div class="story-header-wrapper">
            <div class="story-header image-text-box">
                <div class="story-header-image image-text-box-image">
                    <img src="http://lorempixum.com/q/290/360/people/game of chairs">
                </div>
                <div class="story-header-details">
                    <div class="story-synopsis">
                        <p style="font-weight: bold">
                            lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt
                            ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
                            laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                            voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
                            cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                        </p>

                        <p>
                            lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt
                            ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
                            laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                            voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
                            cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                        </p>
                    </div>
                    <div class="story-buy-ebook story-buy-box">
                        <div class="story-buy-box-type">
                            <p>buy</p>
                            <p>e-book</p>
                        </div>
                        <div class="story-buy-box-price">
                            3.99
                        </div>
                        <div class="story-buy-box-future">
                            future-price
                        </div>
                    </div>
                    <div class="story-buy-shares story-buy-box">
                        <div class="story-buy-box-type">
                            <p>buy</p>
                            <p>shares</p>
                        </div>
                        <div class="story-buy-box-price">
                            3.99
                        </div>
                        <div class="story-buy-box-future">
                            shares available
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="story-types-wrapper">
            <table class="story-types-table">
                <tr>
                    <th>type</th>
                    <th>format</th>
                    <th>category</th>
                    <th>genre</th>
                    <th>subgenre</th>
                    <th>tags</th>
                </tr>
                <tr>
                    <td>story</td>
                    <td>fiction</td>
                    <td>novel</td>
                    <td>sci-fi</td>
                    <td>fantasy</td>
                    <td>my 1st e-book</td>
                </tr>
            </table>
        </div>
        <div class="story-read-wrapper">
            <div class="story-read-button">
                <input type="button" class="button-white" value="read the pilot"/>
            </div>
        </div>
        <div class="story-promo-item-wrapper">
            <div class="story-promo-item">
                <div class="story-promo-item-content">
                    <h3 style="text-align: center">It started with me, watching stolen TV series</h3>
                    <br/>
                    <div style="text-align: center">
                        <iframe width="677" height="328" frameborder="0" allowfullscreen="" src="http://www.youtube.com/embed/6ooInluKNbs"></iframe>
                    </div>
                </div>
                <div class="story-promo-item-social">
                    <p>
                        <span>
                            <iframe scrolling="no" frameborder="0" style="width:100px; height:20px;" src="http://platform.twitter.com/widgets/tweet_button.html?url=http://sethgodin.typepad.com/seths_blog/2013/08/words-are-hooks-words-are-levers.html&text=Words are hooks, words are levers&count=1" allowtransparency="true"></iframe>
                        </span>
                        <span>
                            <iframe width="100%" scrolling="no" frameborder="0" hspace="0" marginheight="0" marginwidth="0" style="position: static; top: 0px; width: 70px; margin: 0px; border-style: none; left: 0px; visibility: visible; height: 20px;" tabindex="0" vspace="0" id="I0_1375617179663" name="I0_1375617179663" src="https://apis.google.com/u/0/_/+1/fastbutton?bsv&amp;count=true&amp;size=medium&amp;hl=en-US&amp;origin=http%3A%2F%2Fsethgodin.typepad.com&amp;url=http%3A%2F%2Fsethgodin.typepad.com%2Fseths_blog%2F2013%2F08%2Fwords-are-hooks-words-are-levers.html&amp;gsrc=3p&amp;jsh=m%3B%2F_%2Fscs%2Fapps-static%2F_%2Fjs%2Fk%3Doz.gapi.en.AxzHZUKBw5A.O%2Fm%3D__features__%2Fam%3DEQ%2Frt%3Dj%2Fd%3D1%2Frs%3DAItRSTNLE4ENTVQDnCw8sLct9DvXqChJew#_methods=onPlusOne%2C_ready%2C_close%2C_open%2C_resizeMe%2C_renderstart%2Concircled&amp;id=I0_1375617179663&amp;parent=http%3A%2F%2Fsethgodin.typepad.com&amp;pfname=&amp;rpctoken=24452946" allowtransparency="true" data-gapiattached="true" title="+1"></iframe>
                        </span>
                        <span>
                            <iframe scrolling="no" frameborder="0" allowtransparency="true" style="border:none; overflow:visible; width: 90px; height:21px;" src="http://www.facebook.com/plugins/like.php?href=http://sethgodin.typepad.com/seths_blog/2013/08/words-are-hooks-words-are-levers.html&send=false&layout=button_count&show_faces=false&action=like&colorscheme=light&font=arial&height=21"></iframe>
                        </span>
                    </p>
                </div>
                <div class="story-promo-item-end">
                    <img src="${pageContext.request.contextPath}/images/viewproject-dotted-line.jpg"/>
                </div>
            </div>
            <div class="story-promo-item">
                <div class="story-promo-item-content">
                    <h3>This is me, writing this post</h3>
                    <br/>
                    <p style="font-size: 14px">
                        <img src="http://lorempixum.com/q/330/300/people/game%20of%20chairs" align="right"/>
                        lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
                        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                        voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat
                        non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. lorem ipsum dolor
                        sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore
                        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
                        ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum
                        dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa
                        qui officia deserunt mollit anim id est laborum.
                    </p>
                    <br/>
                    <p style="font-size: 14px">
                        lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
                        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                        voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat
                        non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </p>
                </div>
                <div class="story-promo-item-social">
                    <p>
                        <span>
                            <iframe scrolling="no" frameborder="0" style="width:100px; height:20px;" src="http://platform.twitter.com/widgets/tweet_button.html?url=http://sethgodin.typepad.com/seths_blog/2013/08/words-are-hooks-words-are-levers.html&text=Words are hooks, words are levers&count=1" allowtransparency="true"></iframe>
                        </span>
                        <span>
                            <iframe width="100%" scrolling="no" frameborder="0" hspace="0" marginheight="0" marginwidth="0" style="position: static; top: 0px; width: 70px; margin: 0px; border-style: none; left: 0px; visibility: visible; height: 20px;" tabindex="0" vspace="0" id="I0_1375617179663" name="I0_1375617179663" src="https://apis.google.com/u/0/_/+1/fastbutton?bsv&amp;count=true&amp;size=medium&amp;hl=en-US&amp;origin=http%3A%2F%2Fsethgodin.typepad.com&amp;url=http%3A%2F%2Fsethgodin.typepad.com%2Fseths_blog%2F2013%2F08%2Fwords-are-hooks-words-are-levers.html&amp;gsrc=3p&amp;jsh=m%3B%2F_%2Fscs%2Fapps-static%2F_%2Fjs%2Fk%3Doz.gapi.en.AxzHZUKBw5A.O%2Fm%3D__features__%2Fam%3DEQ%2Frt%3Dj%2Fd%3D1%2Frs%3DAItRSTNLE4ENTVQDnCw8sLct9DvXqChJew#_methods=onPlusOne%2C_ready%2C_close%2C_open%2C_resizeMe%2C_renderstart%2Concircled&amp;id=I0_1375617179663&amp;parent=http%3A%2F%2Fsethgodin.typepad.com&amp;pfname=&amp;rpctoken=24452946" allowtransparency="true" data-gapiattached="true" title="+1"></iframe>
                        </span>
                        <span>
                            <iframe scrolling="no" frameborder="0" allowtransparency="true" style="border:none; overflow:visible; width: 90px; height:21px;" src="http://www.facebook.com/plugins/like.php?href=http://sethgodin.typepad.com/seths_blog/2013/08/words-are-hooks-words-are-levers.html&send=false&layout=button_count&show_faces=false&action=like&colorscheme=light&font=arial&height=21"></iframe>
                        </span>
                    </p>
                </div>
                <div class="story-promo-item-end">
                    <img src="${pageContext.request.contextPath}/images/viewproject-dotted-line.jpg"/>
                </div>
            </div>
        </div>
    </article>
</div>
</div>
</div>
</div>

<jsp:include page="inc/footer.jspf"/>

</body>
</html>