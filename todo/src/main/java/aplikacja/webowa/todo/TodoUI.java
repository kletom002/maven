package aplikacja.webowa.todo;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;

import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Syla
 */
@SpringUI
@Theme("valo")
public class TodoUI extends UI{

    private VerticalLayout root;
    
    @Autowired
    TodoLayout todoLayout;
    
    @Override
    protected void init(VaadinRequest request) {
        setupLayout();
        addHeader();
        addForm();
        addTodoList();
        addDeleteButton();
        
    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER); //polozenie szablonu
        setContent(root);
    }

    private void addHeader() {
        Label header = new Label("TODOs");
        header.addStyleName(ValoTheme.LABEL_H1); //wieksza czcionka 
        root.addComponent(header);
    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("80%"); //szerokosc formularza
        
        TextField task = new TextField();
        Button add = new Button("Add");
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);
        add.setIcon(VaadinIcons.PLUS);
        
        formLayout.addComponentsAndExpand(task);
        formLayout.addComponents(add);
        
        add.addClickListener(clik-> {
            todoLayout.add(new Todo(task.getValue()));
            task.setValue("");
            task.focus();   
        });
        task.focus();
        add.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        
        root.addComponent(formLayout);
    }

    private void addTodoList() {
        todoLayout.setWidth("80%"); //szerokosc listy todo
        root.addComponent(todoLayout);
    }

    private void addDeleteButton() {
        Button delete = new Button("Delete completed items");
        delete.addClickListener(click-> {
            todoLayout.deleteCompleted();
        });
        root.addComponent(delete);
    }
    
}
