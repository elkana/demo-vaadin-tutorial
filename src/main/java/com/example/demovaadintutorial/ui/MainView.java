package com.example.demovaadintutorial.ui;

import com.example.demovaadintutorial.model.Contact;
import com.example.demovaadintutorial.repo.ICompanyRepo;
import com.example.demovaadintutorial.repo.IContactRepo;
import com.example.demovaadintutorial.ui.form.ContactForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@Route
public class MainView extends VerticalLayout {
        Grid<Contact> grid = new Grid<Contact>(); // ntah knp ga boleh Grid<>(Contact.class);
        IContactRepo repoContact;
        ContactForm form;
        TextField filterText;

        public MainView(IContactRepo repoContact, ICompanyRepo repoCompany) {
                this.repoContact = repoContact;
                addClassName("main-view");
                setSizeFull();

                // configure search
                filterText = new TextField();
                filterText.setPlaceholder("Filter by name...");
                filterText.setClearButtonVisible(true);
                filterText.setValueChangeMode(ValueChangeMode.LAZY);
                filterText.addValueChangeListener(e -> updateList());
                var addContactBtn = new Button("Add Contact", click -> {
                        grid.asSingleSelect().clear();
                        editForm(new Contact());
                });

                // configure grid
                grid.addClassName("contact-grid"); // css will be .main-view .contact-grid {...}
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
                grid.setSizeFull(); // must to be able to use flex

                grid.getColumns().forEach(col -> col.setAutoWidth(true));

                grid.asSingleSelect().addValueChangeListener(e -> editForm(e.getValue()));

                // configure form
                form = new ContactForm(repoCompany.findAll());
                form.addListener(ContactForm.SaveEvent.class, l -> {
                        repoContact.save(l.getContact());
                        updateList();
                        closeEditor();
                });
                form.addListener(ContactForm.DeleteEvent.class, l -> {
                        repoContact.delete(l.getContact());
                        updateList();
                        closeEditor();
                });
                form.addListener(ContactForm.CloseEvent.class, l -> closeEditor());

                var div = new Div(grid, form);
                div.addClassName("content");
                div.setSizeFull();

                add(new H1("Data Grid"), new HorizontalLayout(filterText, addContactBtn), div);
                updateList();
                closeEditor();
        }

        private void editForm(Contact value) {
                if (value == null)
                        closeEditor();
                else {
                        form.setContact(value);
                        form.setVisible(true);
                        addClassName("editing");
                }
        }

        private void closeEditor() {
                form.setContact(null);
                form.setVisible(false);
                removeClassName("editing");
        }

        private void updateList() {
                String filter = filterText.getValue();
                if (filter == null || filter.isEmpty())
                        grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(repoContact));
                else
                        grid.setItems(repoContact.search(filter));
        }

}
