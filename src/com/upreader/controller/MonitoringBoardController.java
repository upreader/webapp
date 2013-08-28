package com.upreader.controller;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.upreader.UpreaderConstants;
import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.dto.MonitorBoardDTO;
import com.upreader.model.Project;
import com.upreader.model.User;
import org.joda.time.DateMidnight;
import org.joda.time.Days;

import java.util.*;

public class MonitoringBoardController extends BasicController {
    public MonitoringBoardController(BasicPathHandler handler, Context context) {
        super(handler, context);
    }

    public boolean data() {
        // first get all user's projects and create the DTOs
        // save the DTOs on the user's session
        // manipulate only the DTOs with DatatablesCriterias requests

        List<MonitorBoardDTO> dtos = context().session().getObject(UpreaderConstants.SESSION_MONITOR_BOARD_DATA);
        if(dtos == null) {
            User user =  context().session().getObject(UpreaderConstants.SESSION_USER);
            List<Project> projects = context().projectDAO().findAllProjectsForUser(user.getId());
            dtos = toMonitorBoardDTOs(projects);
        }

        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(context().request().getRawRequest());
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Project o");

        if(criterias.hasOneSortedColumn()) {
            // we have 2 types of technical sorts:
                // simple - sort by columns from Project
                // complex - sort by other entities related to Project

            boolean simpleSort = false;

            for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
                switch (columnDef.getName()) {
                    case "title":
                    case "bookPrice":
                    case "shareValue":
                    case "noViews":
                    case "booksSold":
                    case "sharesToSale":
                    case "serialStorySubscriptionPrice":
                        simpleSort = true;
                        break;
                    default:
                        simpleSort = false;
                }
            }

            if(simpleSort) {
                List<String> orderParams = new ArrayList<>();
                queryBuilder.append(" ORDER BY ");

                for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
                    orderParams.add("o." + columnDef.getName() + " " + columnDef.getSortDirection());
                }

                Iterator<String> itr2 = orderParams.iterator();
                while (itr2.hasNext()) {
                    queryBuilder.append(itr2.next());
                    if (itr2.hasNext()) {
                        queryBuilder.append(" , ");
                    }
                }

                List<Project> projects = context().projectDAO().findWithQuery(queryBuilder.toString());
                dtos = toMonitorBoardDTOs(projects);
            } else {
                // treat case by case: upreaders, irsProgress, daysToDeadline, subscribers, income, derivatives, authorName
                for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
                    switch (columnDef.getName()) {
                        case "upreaders":
                            break;
                        case "irsProgress":
                            break;
                        case "daysToDeadline":
                            break;
                        case "subscribers":
                            break;
                        case "income":
                            break;
                        case "derivatives":
                            break;
                        case "authorName":
                            break;
                    }
                }
            }
        }

        DataSet<MonitorBoardDTO> dataSet = new DataSet(dtos, (long) dtos.size(), (long) dtos.size());
        DatatablesResponse<MonitorBoardDTO> response = DatatablesResponse.build(dataSet, criterias);
        return handler().json(response);
    }

    private List<MonitorBoardDTO> toMonitorBoardDTOs(List<Project> projects) {
        if(projects == null)
            return new ArrayList<>();

        List<MonitorBoardDTO> dtos = new ArrayList<>(projects.size());
        for(Project p : projects) {
            dtos.add(toMonitorBoardDTO(p));
        }

        return dtos;
    }

    private MonitorBoardDTO toMonitorBoardDTO(Project project) {
        MonitorBoardDTO dto = new MonitorBoardDTO();

        dto.setId(project.getId());

        dto.setTitle(project.getTitle());
        dto.setBookPrice(project.getBookPrice());
        dto.setUpreaders(project.getShareholders() == null ? 0 : project.getShareholders().size());
        dto.setShareValue(project.getShareValue());
        dto.setIrsProgress(project.getSharesToSale() / project.getTotalShares() * 100);

        if(project.getDeadline() != null) {
            DateMidnight now = new DateMidnight(new Date().getTime());
            DateMidnight deadline = new DateMidnight(project.getDeadline().getTime());

            int days = Days.daysBetween(now, deadline).getDays();
            if(days > 0) {
                // project is still in deadline
                dto.setDaysToDeadline(days);
            } else {
                dto.setDaysToDeadline(0);
            }
        } else {
            // not set (possible for first release)
            dto.setDaysToDeadline(-1);
        }

        return dto;
    }
}
