package com.upreader.controller;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.upreader.UpreaderConstants;
import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.dto.MonitorBoardDTO;
import com.upreader.helper.CollectionHelper;
import com.upreader.model.Project;
import com.upreader.model.User;
import com.upreader.helper.NumberHelper;

import java.util.*;

public class MonitoringBoardController extends BasicController {
    public MonitoringBoardController(BasicPathHandler handler, Context context) {
        super(handler, context);
    }

    private Comparator<MonitorBoardDTO> composeComparator(DatatablesCriterias criterias) {
        List<Comparator<MonitorBoardDTO>> composed = new ArrayList<>();

        for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
            MonitoringBoardComparator comparator = null;

            switch (columnDef.getName()) {
                case "id":
                    comparator = MonitoringBoardComparator.ID_SORT;
                    break;
                case "title":
                    comparator = MonitoringBoardComparator.TITLE_SORT;
                    break;
                case "authorName":
                    comparator = MonitoringBoardComparator.AUTHORNAME_SORT;
                    break;
                case "bookPrice":
                    comparator = MonitoringBoardComparator.BOOKPRICE_SORT;
                    break;
                case "upreaders":
                    comparator = MonitoringBoardComparator.UPREADERS_SORT;
                    break;
                case "shareValue":
                    comparator = MonitoringBoardComparator.SHAREVALUE_SORT;
                    break;
                case "irsProgress":
                    comparator = MonitoringBoardComparator.IRSPROGRESS_SORT;
                    break;
                case "daysToDeadline":
                    comparator = MonitoringBoardComparator.DAYSTODEADLINE_SORT;
                    break;
                case "noViews":
                    comparator = MonitoringBoardComparator.NOVIEWS_SORT;
                    break;
                case "booksSold":
                    comparator = MonitoringBoardComparator.BOOKSSOLD_SORT;
                    break;
                case "sharesToSale":
                    comparator = MonitoringBoardComparator.SHARESTOSALE_SORT;
                    break;
                case "subscribers":
                    comparator = MonitoringBoardComparator.SUBSCRIBERS_SORT;
                    break;
                case "serialStorySubscriptionPrice":
                    comparator = MonitoringBoardComparator.SUBSCRIPTIONPRICE_SORT;
                    break;
                case "income":
                    comparator = MonitoringBoardComparator.INCOME_SORT;
                    break;
                case "derivatives":
                    comparator = MonitoringBoardComparator.DERIVATIVES_SORT;
                    break;
            }

            if (columnDef.getSortDirection() == ColumnDef.SortDirection.ASC)
                composed.add(getComparator(comparator));
            else
                composed.add(descending(getComparator(comparator)));
        }

        return getComparator(composed);
    }

    public boolean data() {
        // first get all user's projects and create the DTOs
        // save the DTOs on the user's session
        // manipulate only the DTOs with DatatablesCriterias requests
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(context().request().getRawRequest());
        List<MonitorBoardDTO> dtos = new ArrayList<>();
        User user = context().session().getObject(UpreaderConstants.SESSION_USER);

        if (user != null) {
            //List<Project> projects = context().projectDAO().findAllProjectsForUser(user.getId());
            //dtos = toMonitorBoardDTOs(projects);
            dtos = generateTestDTOs();

            if (criterias.hasOneSortedColumn()) {
                // sorting was asked
                Collections.sort(dtos, composeComparator(criterias));
            }
        }

        List<MonitorBoardDTO> display_dtos = null;

        if (dtos.size() > criterias.getDisplayStart() + criterias.getDisplaySize())
            display_dtos = dtos.subList(criterias.getDisplayStart(), criterias.getDisplayStart() + criterias.getDisplaySize());
        else
            display_dtos = dtos;

        DataSet<MonitorBoardDTO> dataSet = new DataSet(display_dtos, (long) dtos.size(), (long) dtos.size());
        DatatablesResponse<MonitorBoardDTO> response = DatatablesResponse.build(dataSet, criterias);
        return handler().json(response);
    }

    private List<MonitorBoardDTO> generateTestDTOs() {
        List<MonitorBoardDTO> dtos = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            MonitorBoardDTO dto = new MonitorBoardDTO();
            dto.setId(i);
            dto.setTitle("Project " + i);
            dto.setBookPrice(null);
            dto.setUpreaders(1000 * i);
            dto.setShareValue((float) i / 4);
            dto.setIrsProgress(100 / i);
            dto.setIrsMax(100);
            dto.setDaysToDeadline(30 - i);
            dto.setNoViews(5555 * i);
            dto.setBooksSold(120 * i);
            dto.setSharesToSale(1652 * i);
            if (i % 2 == 0)
                dto.setSubscribers(400);
            else
                dto.setSubscribers(600);
            dto.setSerialStorySubscriptionPrice(0.99f * i);
            dto.setIncome(111 * i);
            dto.setDerivatives("movie");
            dto.setAuthorName("Author Name " + i);
            dto.setAuthorRating(i);
            dtos.add(dto);
        }

        return dtos;
    }

    private List<MonitorBoardDTO> toMonitorBoardDTOs(List<Project> projects) {
        if (projects == null)
            return new ArrayList<>();

        List<MonitorBoardDTO> dtos = new ArrayList<>(projects.size());
        for (Project p : projects) {
            dtos.add(context().projectDAO().toMonitorBoardDTO(p));
        }

        return dtos;
    }

    public static Comparator<MonitorBoardDTO> getComparator(final List<Comparator<MonitorBoardDTO>> multipleOptions) {
        return new Comparator<MonitorBoardDTO>() {
            @Override
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                for (Comparator<MonitorBoardDTO> option : multipleOptions) {
                    int result = option.compare(o1, o2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        };
    }

    public static Comparator<MonitorBoardDTO> getComparator(final Comparator<MonitorBoardDTO>... multipleOptions) {
        return getComparator(Arrays.asList(multipleOptions));
    }

    public static Comparator<MonitorBoardDTO> descending(final Comparator<MonitorBoardDTO> other) {
        return new Comparator<MonitorBoardDTO>() {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return -1 * other.compare(o1, o2);
            }
        };
    }

    enum MonitoringBoardComparator implements Comparator<MonitorBoardDTO> {
        ID_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getId() != null && o2.getId() != null) ? o1.getId() - o2.getId() : 0;
            }
        },
        TITLE_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getTitle() != null && o2.getTitle() != null) ? o1.getTitle().compareTo(o2.getTitle()) : 0;
            }
        },
        BOOKPRICE_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getBookPrice() != null && o2.getBookPrice() != null) ? NumberHelper.compareFloats(o1.getBookPrice(), o2.getBookPrice()) : 0;
            }
        },
        UPREADERS_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getUpreaders() != null && o2.getUpreaders() != null) ? o1.getUpreaders() - o2.getUpreaders() : 0;
            }
        },
        SHAREVALUE_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getShareValue() != null && o2.getShareValue() != null) ? NumberHelper.compareFloats(o1.getShareValue(), o2.getShareValue()) : 0;
            }
        },
        IRSPROGRESS_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getIrsProgress() != null && o2.getIrsProgress() != null) ? o1.getIrsProgress() - o2.getIrsProgress() : 0;
            }
        },
        DAYSTODEADLINE_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getDaysToDeadline() != null && o2.getDaysToDeadline() != null) ? o1.getDaysToDeadline() - o2.getDaysToDeadline() : 0;
            }
        },
        NOVIEWS_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getNoViews() != null && o2.getNoViews() != null) ? o1.getNoViews() - o2.getNoViews() : 0;
            }
        },
        BOOKSSOLD_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getBooksSold() != null && o2.getBooksSold() != null) ? o1.getBooksSold() - o2.getBooksSold() : 0;
            }
        },
        SHARESTOSALE_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getSharesToSale() != null && o2.getSharesToSale() != null) ? o1.getSharesToSale() - o2.getSharesToSale() : 0;
            }
        },
        SUBSCRIBERS_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getSubscribers() != null && o2.getSubscribers() != null) ? o1.getSubscribers() - o2.getSubscribers() : 0;
            }
        },
        SUBSCRIPTIONPRICE_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getSerialStorySubscriptionPrice() != null && o2.getSerialStorySubscriptionPrice() != null) ? NumberHelper.compareFloats(o1.getSerialStorySubscriptionPrice(), o2.getSerialStorySubscriptionPrice()) : 0;
            }
        },
        INCOME_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getIncome() != null && o2.getIncome() != null) ? o1.getIncome() - o2.getIncome() : 0;
            }
        },
        DERIVATIVES_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getDerivatives() != null && o2.getDerivatives() != null) ? o1.getDerivatives().compareTo(o2.getDerivatives()) : 0;
            }
        },
        AUTHORNAME_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getAuthorName() != null && o2.getAuthorName() != null) ? o1.getAuthorName().compareTo(o2.getAuthorName()) : 0;
            }
        },
        AUTHORRATING_SORT {
            public int compare(MonitorBoardDTO o1, MonitorBoardDTO o2) {
                return (o1 != null && o2 != null && o1.getAuthorRating() != null && o2.getAuthorRating() != null) ? o1.getAuthorRating() - o2.getAuthorRating() : 0;
            }
        };
    }
}