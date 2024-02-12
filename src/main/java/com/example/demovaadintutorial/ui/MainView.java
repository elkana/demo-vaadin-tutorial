package com.example.demovaadintutorial.ui;

import com.example.demovaadintutorial.model.Contact;
import com.example.demovaadintutorial.repo.IContactRepo;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@Route
public class MainView extends VerticalLayout {

        public MainView(IContactRepo repoContact) {
                var grid = new Grid<Contact>();
                setSizeFull();

                grid.addColumn(Contact::getId).setHeader("Id").setComparator(Contact::getId);
                grid.addColumn(Contact::getFirstName).setHeader("First Name")
                                .setComparator(Contact::getFirstName);
                grid.addColumn(Contact::getLastName).setHeader("Last Name")
                                .setComparator(Contact::getLastName);
                grid.addColumn(Contact::getEmail).setHeader("Email")
                                .setComparator(Contact::getEmail);
                grid.addColumn(Contact::getStatus).setHeader("Status")
                                .setTextAlign(ColumnTextAlign.END)
                                .setComparator(Contact::getStatus);
                grid.addColumn(e -> e.getCompany() == null ? "" : e.getCompany().getName())
                                .setHeader("Company");
                // grid.addColumn(Contact::getCompany).setHeader("Company")
                // .setComparator(Contact::getCompany);
                grid.setSizeFull(); // must to be able to use flex

                // var list = repoContact.findAll();
                // gila, cuma gini doank bikin infinite scrollnya
                grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(repoContact));
                grid.getColumns().forEach(col -> col.setAutoWidth(true));

                // grid.asSingleSelect().addValueChangeListener(e -> editForm(e.getValue()));

                add(new H1("Hahaha"), grid);
        }

}
